package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2;

import java.util.function.Consumer;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleCustomPayload;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV10;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV11;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV12r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV12r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV8;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV9r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV9r2;
import protocolsupport.protocol.typeremapper.legacy.LegacyCustomPayloadChannelName;
import protocolsupport.utils.MiscUtils;

public class CustomPayload extends MiddleCustomPayload implements
IClientboundMiddlePacketV8,
IClientboundMiddlePacketV9r1,
IClientboundMiddlePacketV9r2,
IClientboundMiddlePacketV10,
IClientboundMiddlePacketV11,
IClientboundMiddlePacketV12r1,
IClientboundMiddlePacketV12r2 {

	public CustomPayload(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected String getClientTag(String tag) {
		return MiscUtils.clampString(LegacyCustomPayloadChannelName.toLegacy(tag), 20);
	}

	@Override
	protected void write() {
		io.writeClientbound(create(tag, data));
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
