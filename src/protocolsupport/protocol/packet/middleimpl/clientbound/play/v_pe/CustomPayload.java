package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import io.netty.buffer.ByteBuf;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.listeners.InternalPluginMessageRequest;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleCustomPayload;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.pipeline.version.v_pe.PEPacketDecoder;
import protocolsupport.protocol.serializer.MerchantDataSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.typeremapper.legacy.LegacyCustomPayloadChannelName;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class CustomPayload extends MiddleCustomPayload {

	public CustomPayload(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		if (tag.equals(LegacyCustomPayloadChannelName.MODERN_TRADER_LIST)) {
			return RecyclableSingletonList.create(cache.getPEInventoryCache().getFakeVillager().updateTrade(
				cache, connection.getVersion(),
				MerchantDataSerializer.readMerchantData(data)
			));
		} else if (tag.equals(InternalPluginMessageRequest.PESkinUpdateChannel)) {
			//we do this using the normal ClientBoundPacketData stream so the queue can cache these on login
			ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.PLAYER_INFO);
			serializer.writeBytes(data);
			return RecyclableSingletonList.create(serializer);
		}
		return RecyclableSingletonList.create(create(connection.getVersion(), tag, data));
	}

	public static ClientBoundPacketData create(ProtocolVersion version, String tag) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.CUSTOM_EVENT);
		StringSerializer.writeString(serializer, version, tag);
		return serializer;
	}

	public static ClientBoundPacketData create(ProtocolVersion version, String tag, ByteBuf data) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.CUSTOM_EVENT);
		StringSerializer.writeString(serializer, version, tag);
		serializer.writeBytes(data);
		return serializer;
	}

	public static boolean isTag(ByteBuf data, String tag) {
		if (PEPacketDecoder.sPeekPacketId(data) == PEPacketIDs.CUSTOM_EVENT) {
			final ByteBuf copy = data.duplicate();
			PEPacketDecoder.sReadPacketId(copy);
			final String thisTag = StringSerializer.readString(copy, ProtocolVersionsHelper.LATEST_PE);
			return tag.equals(thisTag);
		}
		return false;
	}

}
