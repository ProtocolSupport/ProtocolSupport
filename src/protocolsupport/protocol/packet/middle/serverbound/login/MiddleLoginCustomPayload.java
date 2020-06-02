package protocolsupport.protocol.packet.middle.serverbound.login;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleLoginCustomPayload extends ServerBoundMiddlePacket {

	public MiddleLoginCustomPayload(ConnectionImpl connection) {
		super(connection);
	}

	protected int id;
	protected ByteBuf data;

	@Override
	protected void writeToServer() {
		ServerBoundPacketData custompayload = ServerBoundPacketData.create(PacketType.SERVERBOUND_LOGIN_CUSTOM_PAYLOAD);
		VarNumberSerializer.writeVarInt(custompayload, id);
		custompayload.writeBoolean(data != null);
		if (data != null) {
			custompayload.writeBytes(data);
		}
		codec.read(custompayload);
	}

}
