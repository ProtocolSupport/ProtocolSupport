package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2;

import java.util.function.Consumer;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleCustomPayload;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.storage.netcache.CustomPayloadChannelsCache;
import protocolsupport.protocol.typeremapper.legacy.LegacyCustomPayloadChannelName;
import protocolsupport.utils.Utils;

public class CustomPayload extends MiddleCustomPayload {

	protected final CustomPayloadChannelsCache channelsCache = cache.getChannelsCache();

	public CustomPayload(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		codec.write(create(Utils.clampString(channelsCache.getLegacyName(LegacyCustomPayloadChannelName.toPre13(tag)), 20), data));
	}

	public static ClientBoundPacketData create(String tag, Consumer<ByteBuf> dataWriter) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_CUSTOM_PAYLOAD);
		StringSerializer.writeVarIntUTF8String(serializer, tag);
		dataWriter.accept(serializer);
		return serializer;
	}

	public static ClientBoundPacketData create(String tag, ByteBuf data) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_CUSTOM_PAYLOAD);
		StringSerializer.writeVarIntUTF8String(serializer, tag);
		serializer.writeBytes(data);
		return serializer;
	}

}
