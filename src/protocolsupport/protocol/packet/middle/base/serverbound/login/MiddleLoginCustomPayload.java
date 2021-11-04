package protocolsupport.protocol.packet.middle.base.serverbound.login;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ServerBoundPacketData;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.base.serverbound.ServerBoundMiddlePacket;

public abstract class MiddleLoginCustomPayload extends ServerBoundMiddlePacket {

	protected MiddleLoginCustomPayload(IMiddlePacketInit init) {
		super(init);
	}

	protected int id;
	protected ByteBuf data;

	@Override
	protected void write() {
		ServerBoundPacketData custompayload = ServerBoundPacketData.create(ServerBoundPacketType.LOGIN_CUSTOM_PAYLOAD);
		VarNumberCodec.writeVarInt(custompayload, id);
		custompayload.writeBoolean(data != null);
		if (data != null) {
			custompayload.writeBytes(data);
		}
		io.writeServerbound(custompayload);
	}

}
