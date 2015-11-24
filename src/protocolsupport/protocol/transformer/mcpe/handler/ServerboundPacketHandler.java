package protocolsupport.protocol.transformer.mcpe.handler;

import gnu.trove.map.hash.TIntObjectHashMap;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

import net.minecraft.server.v1_8_R3.Packet;

import protocolsupport.protocol.transformer.mcpe.UDPNetworkManager;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketRegistry;
import protocolsupport.protocol.transformer.mcpe.packet.raknet.EncapsulatedPacket;
import protocolsupport.protocol.transformer.mcpe.packet.raknet.RakNetPacket;

public class ServerboundPacketHandler {

	private UDPNetworkManager networkManager;
	public ServerboundPacketHandler(UDPNetworkManager networkManager) {
		this.networkManager = networkManager;
	}

	private final Orderer orderer = new Orderer();

	private int lastReceivedSeqNumber = -1;
	@SuppressWarnings("rawtypes")
	public List<Packet> transform(RakNetPacket rpacket) throws Exception {
		//send ack or nack based on seq number
		int packetSeqNumber = rpacket.getSeqNumber();
		int prevSeqNumber = lastReceivedSeqNumber;
		lastReceivedSeqNumber = packetSeqNumber;
		if (packetSeqNumber - prevSeqNumber == 1) {
			networkManager.sendACK(packetSeqNumber);
		} else {
			networkManager.sendNACK(lastReceivedSeqNumber + 1, packetSeqNumber - 1);
		}

		//order packets or just add based on reliability
		ArrayList<EncapsulatedPacket> orderedPackets = new ArrayList<>();
		for (EncapsulatedPacket packet : rpacket.getPackets()) {
			if (packet.reliability == 2 || packet.reliability == 3) {
				orderedPackets.addAll(orderer.getOrdered(packet));
			} else {
				orderedPackets.add(packet);
			}
		}

		//unsplit packets and work only with full ones
		List<EncapsulatedPacket> fullEPackets = getFullPackets(orderedPackets);

		//transform packets
		ArrayList<Packet> packets = new ArrayList<Packet>();
		for (EncapsulatedPacket epacket : fullEPackets) {
			packets.addAll(transformPacketData(epacket.data));
		}

		return packets;
	}

	private final TIntObjectHashMap<SplittedPacket> notFullPackets = new TIntObjectHashMap<SplittedPacket>();
	private List<EncapsulatedPacket> getFullPackets(List<EncapsulatedPacket> epackets) {
		ArrayList<EncapsulatedPacket> result = new ArrayList<EncapsulatedPacket>();
		for (EncapsulatedPacket epacket : epackets) {
			if (!epacket.hasSplit) {
				result.add(epacket);
			} else {
				int splitID = epacket.splitID;
				SplittedPacket partial = notFullPackets.get(splitID);
				if (partial == null) {
					notFullPackets.put(splitID, new SplittedPacket(epacket));
				} else {
					partial.appendData(epacket);
					if (partial.isComplete()) {
						notFullPackets.remove(splitID);
						result.add(partial.getFullPacket());
					}
				}
			}
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	private List<? extends Packet> transformPacketData(ByteBuf fullpacket) throws Exception {
		return PEPacketRegistry.getPacket(fullpacket.readUnsignedByte()).decode(fullpacket).transfrom();
	}

	private static final class SplittedPacket {

		private static final long maxSplits = 512;

		private int receivedSplits = 0;
		private EncapsulatedPacket[] packets;

		public SplittedPacket(EncapsulatedPacket startpacket) {
			if (startpacket.splitCount > maxSplits) {
				throw new IllegalStateException("Too many splits for single packet, max: "+maxSplits+", packet: "+startpacket.splitCount);
			}
			packets = new EncapsulatedPacket[startpacket.splitCount];
			packets[0] = startpacket;
		}

		public void appendData(EncapsulatedPacket packet) {
			if (packets[packet.splitIndex] != null) {
				return;
			}
			receivedSplits++;
			packets[packet.splitIndex] = packet;
		}

		public boolean isComplete() {
			return packets.length - receivedSplits == 1;
		}

		public EncapsulatedPacket getFullPacket() {
			EncapsulatedPacket epacket = new EncapsulatedPacket();
			epacket.orderChannel = packets[0].orderChannel;
			epacket.orderIndex = packets[0].orderIndex;
			for (EncapsulatedPacket bufferpacket : packets) {
				epacket.data.writeBytes(bufferpacket.data);
			}
			return epacket;
		}

	}

	//ordering is done based on messageIndex and not on orderIndex because for whatever reason login packets are sent unordered
	private static final class Orderer {
		private final HashMap<Integer, EncapsulatedPacket> packets = new HashMap<>();
		private final HashSet<Integer> missing = new HashSet<>();
		private int minReceivedIndex = Integer.MAX_VALUE;
		private int lastReceivedIndex = -1;

		public Collection<EncapsulatedPacket> getOrdered(EncapsulatedPacket epacket) {
			int messageIndex = epacket.messageIndex;
			if ((missing.size() > 256) || (messageIndex - lastReceivedIndex > 128)) {
				throw new DecoderException("Too big packet loss");
			}
			//duplicate packet, ignore it, return empty packet list
			if (messageIndex <= lastReceivedIndex && missing.isEmpty()) {
				return Collections.emptyList();
			}
			//in case if received index more than last we put packet to queue and add missing packet ids
			//return empty packet list
			if (messageIndex - lastReceivedIndex > 1) {
				packets.put(messageIndex, epacket);
				for (int i = (lastReceivedIndex + 1); i < messageIndex; i++) {
					missing.add(i);
				}
				lastReceivedIndex = messageIndex;
				return Collections.emptyList();
			}
			//in case if received index is after the last one we have to cases:
			//1st - no missing packets - just return list containing this packet
			//2nd - have - missing packets - put packet in queue, return empty packet list
			if (messageIndex - lastReceivedIndex == 1) {
				lastReceivedIndex = messageIndex;
				if (missing.isEmpty()) {
					return Collections.singletonList(epacket);
				} else {
					packets.put(messageIndex, epacket);
					return Collections.emptyList();
				}
			}
			//duplicate packet or not missing, ignore it, return empty packet list
			if (packets.containsKey(messageIndex) || !missing.contains(messageIndex)) {
				return Collections.emptyList();
			}
			//add packet to queue and remove its id from missing list, also update minReceivedIndex
			minReceivedIndex = Math.min(messageIndex, minReceivedIndex);
			packets.put(messageIndex, epacket);
			missing.remove(messageIndex);
			//if missing list is empty than we return all queued packets in order
			//if not - just return empty packet list
			if (missing.isEmpty()) {
				EncapsulatedPacket[] packetBufs = new EncapsulatedPacket[packets.size()];
				for (Entry<Integer, EncapsulatedPacket> entry : packets.entrySet()) {
					packetBufs[entry.getKey() - minReceivedIndex] = entry.getValue();
				}
				packets.clear();
				minReceivedIndex = Integer.MAX_VALUE;
				return Arrays.asList(packetBufs);
			} else {
				return Collections.emptyList();
			}
		}

	}


}
