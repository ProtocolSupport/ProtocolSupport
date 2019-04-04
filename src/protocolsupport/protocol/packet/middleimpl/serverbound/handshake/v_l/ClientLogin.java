package protocolsupport.protocol.packet.middleimpl.serverbound.handshake.v_l;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolType;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class ClientLogin extends ServerBoundMiddlePacket {

	public ClientLogin(ConnectionImpl connection) {
		super(connection);
	}

	protected String username;
	protected String hostname;
	protected int port;

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		String[] data = StringSerializer.readString(clientdata, ProtocolVersion.getOldest(ProtocolType.PC)).split("[;]");
		String[] addrdata = data[1].split("[:]");
		username = data[0];
		hostname = addrdata[0];
		port = Integer.parseInt(addrdata[1]);
	}

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		RecyclableArrayList<ServerBoundPacketData> packets = RecyclableArrayList.create();
		ServerBoundPacketData hsscreator = ServerBoundPacketData.create(ServerBoundPacket.HANDSHAKE_START);
		VarNumberSerializer.writeVarInt(hsscreator, ProtocolVersionsHelper.LATEST_PC.getId());
		StringSerializer.writeVarIntUTF8String(hsscreator, hostname);
		hsscreator.writeShort(port);
		VarNumberSerializer.writeVarInt(hsscreator, 2);
		packets.add(hsscreator);
		ServerBoundPacketData lscreator = ServerBoundPacketData.create(ServerBoundPacket.LOGIN_START);
		StringSerializer.writeVarIntUTF8String(lscreator, username);
		packets.add(lscreator);
		return packets;
	}

}
