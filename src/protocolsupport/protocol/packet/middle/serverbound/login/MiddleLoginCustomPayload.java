package protocolsupport.protocol.packet.middle.serverbound.login;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleLoginCustomPayload extends ServerBoundMiddlePacket {

	protected MiddleLoginCustomPayload(MiddlePacketInit init) {
		super(init);
	}

	protected int id;
	protected ByteBuf data;

	@Override
	protected void write() {
		ServerBoundPacketData custompayload = ServerBoundPacketData.create(ServerBoundPacketType.LOGIN_CUSTOM_PAYLOAD);
		VarNumberSerializer.writeVarInt(custompayload, id);
		custompayload.writeBoolean(data != null);
		if (data != null) {
			custompayload.writeBytes(data);
		}
		codec.writeServerbound(custompayload);
	}

}
