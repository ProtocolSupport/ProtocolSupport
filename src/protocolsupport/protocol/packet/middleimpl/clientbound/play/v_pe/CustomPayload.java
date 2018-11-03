package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleCustomPayload;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MerchantDataSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.typeremapper.legacy.LegacyCustomPayloadChannelName;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class CustomPayload extends MiddleCustomPayload {

	public CustomPayload(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();

		if (tag.equals(LegacyCustomPayloadChannelName.MODERN_TRADER_LIST)) {
			return RecyclableSingletonList.create(cache.getPEInventoryCache().getFakeVillager().updateTrade(
					cache, connection.getVersion(),
					MerchantDataSerializer.readMerchantData(data, ProtocolVersionsHelper.LATEST_PC, cache.getAttributesCache().getLocale()))
			);
		}
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		packets.add(create(version, tag, data));
		return packets;
	}

	public static ClientBoundPacketData create(ProtocolVersion version, String tag, ByteBuf data) {
		System.out.println("CustomPayload " + tag);
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.CUSTOM_EVENT);
		StringSerializer.writeString(serializer, version, tag);
		serializer.writeBytes(data);
		return serializer;
	}
}
