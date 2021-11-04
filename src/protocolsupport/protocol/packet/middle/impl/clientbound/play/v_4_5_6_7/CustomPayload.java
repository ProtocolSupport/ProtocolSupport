package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6_7;

import java.util.function.BiConsumer;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleCustomPayload;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV6;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV7;
import protocolsupport.protocol.typeremapper.legacy.LegacyCustomPayloadChannelName;
import protocolsupport.utils.MiscUtils;

public class CustomPayload extends MiddleCustomPayload implements
IClientboundMiddlePacketV4,
IClientboundMiddlePacketV5,
IClientboundMiddlePacketV6,
IClientboundMiddlePacketV7
{

	public CustomPayload(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected String getClientTag(String tag) {
		return MiscUtils.clampString(LegacyCustomPayloadChannelName.toLegacy(tag), 20);
	}

	@Override
	protected void write() {
		io.writeClientbound(create(version, tag, data));
	}

	public static <T> ClientBoundPacketData create(ProtocolVersion version, String tag, T type, BiConsumer<ByteBuf, T> typeWriter) {
		ClientBoundPacketData custompayload = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_CUSTOM_PAYLOAD);
		StringCodec.writeString(custompayload, version, tag);
		MiscDataCodec.writeShortLengthPrefixedType(custompayload, type, typeWriter);
		return custompayload;
	}

	public static ClientBoundPacketData create(ProtocolVersion version, String tag, ByteBuf data) {
		ClientBoundPacketData custompayload = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_CUSTOM_PAYLOAD);
		StringCodec.writeString(custompayload, version, tag);
		ArrayCodec.writeShortByteArray(custompayload, data);
		return custompayload;
	}

}
