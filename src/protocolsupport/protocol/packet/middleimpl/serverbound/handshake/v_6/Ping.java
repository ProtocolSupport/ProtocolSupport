package protocolsupport.protocol.packet.middleimpl.serverbound.handshake.v_6;

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

public class Ping extends ServerBoundMiddlePacket {

	protected String hostname;
	protected int port;

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		ProtocolVersion version = connection.getVersion();
		clientdata.readUnsignedByte();
		clientdata.readUnsignedByte();
		StringSerializer.readString(clientdata, version);
		clientdata.readUnsignedShort();
		clientdata.readUnsignedByte();
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
		VarNumberSerializer.writeVarInt(hsscreator, 1);
		packets.add(hsscreator);
		packets.add(ServerBoundPacketData.create(ServerBoundPacket.STATUS_REQUEST));
		return packets;
	}

}
