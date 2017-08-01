package protocolsupport.protocol.packet.middleimpl.serverbound.handshake.v_4_5_6;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class ClientLogin extends ServerBoundMiddlePacket {

	protected String username;
	protected String hostname;
	protected int port;

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		clientdata.readUnsignedByte();
		ProtocolVersion version = connection.getVersion();
		username = StringSerializer.readString(clientdata, version, 16);
		hostname = StringSerializer.readString(clientdata, version);
		port = clientdata.readInt();
	}

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative()  {
		RecyclableArrayList<ServerBoundPacketData> packets = RecyclableArrayList.create();
		ServerBoundPacketData hsscreator = ServerBoundPacketData.create(ServerBoundPacket.HANDSHAKE_START);
		VarNumberSerializer.writeVarInt(hsscreator, ProtocolVersionsHelper.LATEST_PC.getId());
		StringSerializer.writeString(hsscreator, ProtocolVersionsHelper.LATEST_PC, hostname);
		hsscreator.writeShort(port);
		VarNumberSerializer.writeVarInt(hsscreator, 2);
		packets.add(hsscreator);
		ServerBoundPacketData lscreator = ServerBoundPacketData.create(ServerBoundPacket.LOGIN_START);
		StringSerializer.writeString(lscreator, ProtocolVersionsHelper.LATEST_PC, username);
		packets.add(lscreator);
		return packets;
	}

}
