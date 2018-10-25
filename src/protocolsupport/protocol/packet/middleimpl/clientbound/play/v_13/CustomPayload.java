package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleCustomPayload;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MerchantDataSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.typeremapper.legacy.LegacyCustomPayloadChannelName;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class CustomPayload extends MiddleCustomPayload {

	public CustomPayload(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_CUSTOM_PAYLOAD_ID);
		StringSerializer.writeString(serializer, connection.getVersion(), tag);
		switch (tag) {
			case (LegacyCustomPayloadChannelName.MODERN_TRADER_LIST): {
				String locale = cache.getAttributesCache().getLocale();
				MerchantDataSerializer.writeMerchantData(
					serializer, connection.getVersion(), locale,
					MerchantDataSerializer.readMerchantData(data, ProtocolVersionsHelper.LATEST_PC, locale)
				);
				break;
			}
			default: {
				serializer.writeBytes(data);
				break;
			}
		}
		return RecyclableSingletonList.create(serializer);
	}

}
