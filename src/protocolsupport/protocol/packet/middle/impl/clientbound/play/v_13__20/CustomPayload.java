package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_13__20;

import java.util.function.Consumer;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleCustomPayload;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV13;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV14r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV14r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV15;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV16r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV16r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV18;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV20;

public class CustomPayload extends MiddleCustomPayload implements
IClientboundMiddlePacketV13,
IClientboundMiddlePacketV14r1,
IClientboundMiddlePacketV14r2,
IClientboundMiddlePacketV15,
IClientboundMiddlePacketV16r1,
IClientboundMiddlePacketV16r2,
IClientboundMiddlePacketV17r1,
IClientboundMiddlePacketV17r2,
IClientboundMiddlePacketV18,
IClientboundMiddlePacketV20 {

	public CustomPayload(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected String getClientTag(String tag) {
		return tag;
	}

	@Override
	protected void write() {
		io.writeClientbound(create(tag, data));
	}

	public static ClientBoundPacketData create(String tag, Consumer<ByteBuf> dataWriter) {
		ClientBoundPacketData custompayload = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_CUSTOM_PAYLOAD);
		StringCodec.writeVarIntUTF8String(custompayload, tag);
		dataWriter.accept(custompayload);
		return custompayload;
	}

	public static ClientBoundPacketData create(String tag, ByteBuf data) {
		ClientBoundPacketData custompayload = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_CUSTOM_PAYLOAD);
		StringCodec.writeVarIntUTF8String(custompayload, tag);
		custompayload.writeBytes(data);
		return custompayload;
	}

}
