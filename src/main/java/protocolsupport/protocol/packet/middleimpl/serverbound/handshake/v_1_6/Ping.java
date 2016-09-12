package protocolsupport.protocol.packet.middleimpl.serverbound.handshake.v_1_6;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.PacketCreator;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class Ping extends ServerBoundMiddlePacket {

	protected String hostname;
	protected int port;

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) throws IOException {
		serializer.readUnsignedByte();
		serializer.readUnsignedByte();
		serializer.readString();
		serializer.readUnsignedShort();
		serializer.readUnsignedByte();
		hostname = serializer.readString();
		port = serializer.readInt();
	}

	@Override
	public RecyclableCollection<PacketCreator> toNative() throws Exception {
		RecyclableArrayList<PacketCreator> packets = RecyclableArrayList.create();
		PacketCreator hsscreator = PacketCreator.create(ServerBoundPacket.HANDSHAKE_START);
		hsscreator.writeVarInt(ProtocolVersion.getLatest().getId());
		hsscreator.writeString(hostname);
		hsscreator.writeShort(port);
		hsscreator.writeVarInt(1);
		packets.add(hsscreator);
		packets.add(PacketCreator.create(ServerBoundPacket.STATUS_REQUEST));
		return packets;
	}

}
