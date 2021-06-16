package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import java.util.function.BiConsumer;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleCustomPayload;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.typeremapper.legacy.LegacyCustomPayloadChannelName;
import protocolsupport.utils.Utils;

public class CustomPayload extends MiddleCustomPayload {

	public CustomPayload(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected String getClientTag(String tag) {
		return Utils.clampString(LegacyCustomPayloadChannelName.toLegacy(tag), 20);
	}

	@Override
	protected void write() {
		codec.writeClientbound(create(version, tag, data));
	}

	public static <T> ClientBoundPacketData create(ProtocolVersion version, String tag, T type, BiConsumer<ByteBuf, T> typeWriter) {
		ClientBoundPacketData custompayload = ClientBoundPacketData.create(ClientBoundPacketType.CLIENTBOUND_PLAY_CUSTOM_PAYLOAD);
		StringSerializer.writeString(custompayload, version, tag);
		MiscSerializer.writeShortLengthPrefixedType(custompayload, type, typeWriter);
		return custompayload;
	}

	public static ClientBoundPacketData create(ProtocolVersion version, String tag, ByteBuf data) {
		ClientBoundPacketData custompayload = ClientBoundPacketData.create(ClientBoundPacketType.CLIENTBOUND_PLAY_CUSTOM_PAYLOAD);
		StringSerializer.writeString(custompayload, version, tag);
		ArraySerializer.writeShortByteArray(custompayload, data);
		return custompayload;
	}

}
