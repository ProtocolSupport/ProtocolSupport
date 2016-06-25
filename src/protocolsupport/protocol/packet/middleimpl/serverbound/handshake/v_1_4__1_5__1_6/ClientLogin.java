package protocolsupport.protocol.packet.middleimpl.serverbound.handshake.v_1_4__1_5__1_6;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.PacketCreator;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class ClientLogin extends ServerBoundMiddlePacket {

	protected String username;
	protected String hostname;
	protected int port;

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) {
		serializer.readUnsignedByte();
		username = serializer.readString(16);
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
		hsscreator.writeVarInt(2);
		packets.add(hsscreator);
		PacketCreator lscreator = PacketCreator.create(ServerBoundPacket.LOGIN_START);
		lscreator.writeString(username);
		packets.add(lscreator);
		return packets;
	}

}
