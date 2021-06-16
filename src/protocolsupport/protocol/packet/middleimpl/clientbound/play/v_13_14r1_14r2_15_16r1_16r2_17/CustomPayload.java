package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13_14r1_14r2_15_16r1_16r2_17;

import java.util.function.Consumer;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleCustomPayload;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;

public class CustomPayload extends MiddleCustomPayload {

	public CustomPayload(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected String getClientTag(String tag) {
		return tag;
	}

	@Override
	protected void write() {
		codec.writeClientbound(create(tag, data));
	}

	public static ClientBoundPacketData create(String tag, Consumer<ByteBuf> dataWriter) {
		ClientBoundPacketData custompayload = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_CUSTOM_PAYLOAD);
		StringSerializer.writeVarIntUTF8String(custompayload, tag);
		dataWriter.accept(custompayload);
		return custompayload;
	}

	public static ClientBoundPacketData create(String tag, ByteBuf data) {
		ClientBoundPacketData custompayload = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_CUSTOM_PAYLOAD);
		StringSerializer.writeVarIntUTF8String(custompayload, tag);
		custompayload.writeBytes(data);
		return custompayload;
	}

}
