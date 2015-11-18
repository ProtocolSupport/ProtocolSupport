package protocolsupport.protocol.transformer.mcpe.handler;

import gnu.trove.map.hash.TIntObjectHashMap;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.ArrayList;
import java.util.List;

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

	private int lastReceivedSeqNumber = -1;
	@SuppressWarnings("rawtypes")
	public List<Packet> transform(RakNetPacket packet) throws Exception {
		int packetSeqNumber = packet.getSeqNumber();
		int prevSeqNumber = lastReceivedSeqNumber;
		lastReceivedSeqNumber = packetSeqNumber;
		if (packetSeqNumber - prevSeqNumber == 1) {
			networkManager.sendACK(packetSeqNumber);
		} else {
			networkManager.sendNACK(lastReceivedSeqNumber + 1, packetSeqNumber - 1);
		}
		ArrayList<Packet> packets = new ArrayList<Packet>();
		for (ByteBuf buffer : getFullPackets(packet.getPackets())) {
			packets.addAll(transformFullPacket(buffer));
		}
		return packets;
	}

	private final TIntObjectHashMap<NotFullPacket> notFullPackets = new TIntObjectHashMap<NotFullPacket>();
	private List<ByteBuf> getFullPackets(List<EncapsulatedPacket> epackets) {
		ArrayList<ByteBuf> result = new ArrayList<ByteBuf>();
		for (EncapsulatedPacket epacket : epackets) {
			if (!epacket.hasSplit) {
				result.add(epacket.data);
			} else {
				int splitID = epacket.splitID;
				NotFullPacket partial = notFullPackets.get(splitID);
				if (partial == null) {
					notFullPackets.put(splitID, new NotFullPacket(epacket));
				} else {
					partial.appendData(epacket);
					if (partial.isComplete()) {
						notFullPackets.remove(splitID);
						result.add(partial.getData());
					}
				}
			}
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	private List<? extends Packet> transformFullPacket(ByteBuf fullpacket) throws Exception {
		return PEPacketRegistry.getPacket(fullpacket.readUnsignedByte()).decode(fullpacket).transfrom();
	}


	private static final class NotFullPacket {

		private static final long maxSplits = 512;

		private int receivedSplits = 0;
		private ByteBuf[] data;

		public NotFullPacket(EncapsulatedPacket startpacket) {
			if (startpacket.splitCount > maxSplits) {
				throw new IllegalStateException("Too many splits for single packet, max: "+maxSplits+", packet: "+startpacket.splitCount);
			}
			data = new ByteBuf[startpacket.splitCount];
			data[0] = startpacket.data;
		}

		public void appendData(EncapsulatedPacket packet) {
			if (data[packet.splitIndex] != null) {
				return;
			}
			receivedSplits++;
			data[packet.splitIndex] = packet.data;
		}

		public boolean isComplete() {
			return data.length - receivedSplits == 1;
		}

		public ByteBuf getData() {
			return Unpooled.wrappedBuffer(data);
		}

	}


}
