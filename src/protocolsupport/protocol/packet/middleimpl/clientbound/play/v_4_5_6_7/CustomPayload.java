package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import java.util.function.Consumer;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleCustomPayload;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.storage.netcache.CustomPayloadChannelsCache;
import protocolsupport.protocol.typeremapper.legacy.LegacyCustomPayloadChannelName;
import protocolsupport.utils.Utils;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class CustomPayload extends MiddleCustomPayload {

	protected final CustomPayloadChannelsCache channelsCache = cache.getChannelsCache();

	public CustomPayload(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<? extends IPacketData> toData() {
		return RecyclableSingletonList.create(create(
			version, Utils.clampString(channelsCache.getLegacyName(LegacyCustomPayloadChannelName.toPre13(tag)), 20), data
		));
	}

	public static ClientBoundPacketData create(ProtocolVersion version, String tag, Consumer<ByteBuf> dataWriter) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_CUSTOM_PAYLOAD);
		StringSerializer.writeString(serializer, version, tag);
		ArraySerializer.writeShortByteArray(serializer, dataWriter);
		return serializer;
	}

	public static ClientBoundPacketData create(ProtocolVersion version, String tag, ByteBuf data) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_CUSTOM_PAYLOAD);
		StringSerializer.writeString(serializer, version, tag);
		ArraySerializer.writeShortByteArray(serializer, data);
		return serializer;
	}

}
