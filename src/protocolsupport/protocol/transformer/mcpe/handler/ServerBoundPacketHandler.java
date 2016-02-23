package protocolsupport.protocol.transformer.mcpe.handler;

import gnu.trove.map.hash.TIntObjectHashMap;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.DecoderException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import net.minecraft.server.v1_8_R3.Packet;

import protocolsupport.protocol.transformer.mcpe.UDPNetworkManager;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketRegistry;
import protocolsupport.protocol.transformer.mcpe.packet.raknet.EncapsulatedPacket;
import protocolsupport.protocol.transformer.mcpe.packet.raknet.RakNetPacket;

public class ServerBoundPacketHandler {

	private final UDPNetworkManager networkManager;
	public ServerBoundPacketHandler(UDPNetworkManager networkManager) {
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
		} else if (packetSeqNumber - prevSeqNumber > 1) {
			networkManager.sendNACK(prevSeqNumber + 1, packetSeqNumber - 1);
		} else {
			return Collections.emptyList();
		}

		//order packets or just add based on reliability
		ArrayList<EncapsulatedPacket> orderedPackets = new ArrayList<>();
		for (EncapsulatedPacket packet : rpacket.getPackets()) {
			if (packet.getReliability() == 2 || packet.getReliability() == 3) {
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
			ByteBuf data = epacket.getData();
			//after logging in client prefixes data with 142 except for ping packets
			//there is no packet id 142 right now, so we can just ignore first byte if it's value is 142
			data.markReaderIndex();
			int b = data.readUnsignedByte();
			if (b != 142) {
				data.resetReaderIndex();
			}
			packets.addAll(transformPacketData(data));
		}

		return packets;
	}

	private final TIntObjectHashMap<SplittedPacket> notFullPackets = new TIntObjectHashMap<SplittedPacket>();
	private List<EncapsulatedPacket> getFullPackets(List<EncapsulatedPacket> epackets) {
		ArrayList<EncapsulatedPacket> result = new ArrayList<EncapsulatedPacket>();
		for (EncapsulatedPacket epacket : epackets) {
			if (!epacket.hasSplit()) {
				result.add(epacket);
			} else {
				int splitID = epacket.getSplitId();
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
			if (startpacket.getSplitCount() > maxSplits) {
				throw new IllegalStateException("Too many splits for single packet, max: "+maxSplits+", packet: "+startpacket.getSplitCount());
			}
			packets = new EncapsulatedPacket[startpacket.getSplitCount()];
			packets[0] = startpacket;
		}

		public void appendData(EncapsulatedPacket packet) {
			if (packets[packet.getSplitIndex()] != null) {
				return;
			}
			receivedSplits++;
			packets[packet.getSplitIndex()] = packet;
		}

		public boolean isComplete() {
			return packets.length - receivedSplits == 1;
		}

		public EncapsulatedPacket getFullPacket() {
			ByteBuf fulldata = Unpooled.buffer();
			for (EncapsulatedPacket bufferpacket : packets) {
				fulldata.writeBytes(bufferpacket.getData());
			}
			return new EncapsulatedPacket(fulldata);
		}

	}

	//ordering is done based on messageIndex
	private static final class Orderer {
		private final TIntObjectHashMap<EncapsulatedPacket> queue = new TIntObjectHashMap<>(300);
		private int lastReceivedIndex = -1;
		private int lastOrderedIndex = -1;

		public Collection<EncapsulatedPacket> getOrdered(EncapsulatedPacket epacket) {
			int messageIndex = epacket.getMessageIndex();
			if (queue.size() > 256) {
				throw new DecoderException("Too big packet loss");
			}
			//duplicate packet, ignore it, return empty packet list
			if (messageIndex <= lastOrderedIndex) {
				return Collections.emptyList();
			}
			//in case if received index more than last we put packet to queue and add missing packet ids
			//return empty packet list
			if (messageIndex - lastReceivedIndex > 1) {
				queue.put(messageIndex, epacket);
				lastReceivedIndex = messageIndex;
				return Collections.emptyList();
			}
			//in case if received index is after the last one we have to cases:
			//1st - no missing packets - just return list containing this packet
			//2nd - have - missing packets - put packet in queue, return empty packet list
			if (messageIndex - lastReceivedIndex == 1) {
				lastReceivedIndex = messageIndex;
				if (queue.isEmpty()) {
					lastOrderedIndex = lastReceivedIndex;
					return Collections.singletonList(epacket);
				} else {
					queue.put(messageIndex, epacket);
					return Collections.emptyList();
				}
			}
			//duplicate packet, ignore it, return empty packet list
			if (queue.containsKey(messageIndex)) {
				return Collections.emptyList();
			}
			//add packet to queue
			queue.put(messageIndex, epacket);
			//return as much ordered packets as we can
			ArrayList<EncapsulatedPacket> opackets = new ArrayList<EncapsulatedPacket>();
			EncapsulatedPacket foundPacket = null;
			while ((foundPacket = queue.remove(lastOrderedIndex + 1)) != null) {
				opackets.add(foundPacket);
				lastOrderedIndex++;
			}
			return opackets;
		}

	}


}
