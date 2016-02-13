package protocolsupport.protocol.transformer.middlepacketimpl.serverbound.handshake.v_1_4_1_5_1_6;

import net.minecraft.server.v1_8_R3.Packet;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.ServerBoundPacket;
import protocolsupport.protocol.transformer.middlepacket.ServerBoundMiddlePacket;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketCreator;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class ClientLogin extends ServerBoundMiddlePacket {

	protected String username;
	protected String hostname;
	protected int port;

	@Override
	public void readFromClientData(PacketDataSerializer serializer) {
		serializer.readUnsignedByte();
		username = serializer.readString(16);
		hostname = serializer.readString(Short.MAX_VALUE);
		port = serializer.readInt();
	}

	@Override
	public RecyclableCollection<Packet<?>> toNative() throws Exception {
		RecyclableArrayList<Packet<?>> packets = RecyclableArrayList.create();
		PacketCreator hsscreator = PacketCreator.create(ServerBoundPacket.HANDSHAKE_START.get());
		hsscreator.writeVarInt(ProtocolVersion.getLatest().getId());
		hsscreator.writeString(hostname);
		hsscreator.writeShort(port);
		hsscreator.writeVarInt(2);
		packets.add(hsscreator.create());
		PacketCreator lscreator = PacketCreator.create(ServerBoundPacket.LOGIN_START.get());
		lscreator.writeString(username);
		packets.add(lscreator.create());
		return packets;
	}

}
