package protocolsupport.protocol.packet.middleimpl.serverbound.handshake.v_1_6;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class Ping extends ServerBoundMiddlePacket {

	protected String hostname;
	protected int port;

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) {
		serializer.readUnsignedByte();
		serializer.readUnsignedByte();
		serializer.readString();
		serializer.readUnsignedShort();
		serializer.readUnsignedByte();
		hostname = serializer.readString();
		port = serializer.readInt();
	}

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative()  {
		RecyclableArrayList<ServerBoundPacketData> packets = RecyclableArrayList.create();
		ServerBoundPacketData hsscreator = ServerBoundPacketData.create(ServerBoundPacket.HANDSHAKE_START);
		hsscreator.writeVarInt(ProtocolVersion.getLatest().getId());
		hsscreator.writeString(hostname);
		hsscreator.writeShort(port);
		hsscreator.writeVarInt(1);
		packets.add(hsscreator);
		packets.add(ServerBoundPacketData.create(ServerBoundPacket.STATUS_REQUEST));
		return packets;
	}

}
