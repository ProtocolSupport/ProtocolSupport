package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2;

import java.util.function.Consumer;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleCustomPayload;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.typeremapper.legacy.LegacyCustomPayloadChannelName;
import protocolsupport.utils.MiscUtils;

public class CustomPayload extends MiddleCustomPayload {

	public CustomPayload(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected String getClientTag(String tag) {
		return MiscUtils.clampString(LegacyCustomPayloadChannelName.toLegacy(tag), 20);
	}

	@Override
	protected void write() {
		codec.writeClientbound(create(tag, data));
	}

	public static ClientBoundPacketData create(String tag, Consumer<ByteBuf> dataWriter) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_CUSTOM_PAYLOAD);
		StringCodec.writeVarIntUTF8String(serializer, tag);
		dataWriter.accept(serializer);
		return serializer;
	}

	public static ClientBoundPacketData create(String tag, ByteBuf data) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_CUSTOM_PAYLOAD);
		StringCodec.writeVarIntUTF8String(serializer, tag);
		serializer.writeBytes(data);
		return serializer;
	}

}
