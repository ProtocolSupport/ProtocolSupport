package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleCustomPayload;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.MerchantDataSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.typeremapper.legacy.LegacyCustomPayloadChannelName;
import protocolsupport.utils.Utils;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class CustomPayload extends MiddleCustomPayload {

	public CustomPayload(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_CUSTOM_PAYLOAD_ID);
		StringSerializer.writeString(
			serializer, version,
			Utils.clampString(cache.getChannelsCache().getLegacyName(LegacyCustomPayloadChannelName.toPre13(tag)), 20)
		);
		switch (tag) {
			case LegacyCustomPayloadChannelName.MODERN_TRADER_LIST: {
				String locale = cache.getAttributesCache().getLocale();
				ArraySerializer.writeShortByteArray(
					serializer,
					to -> MerchantDataSerializer.writeMerchantData(
						to, version, locale,
						MerchantDataSerializer.readMerchantData(data)
					)
				);
				break;
			}
			default: {
				ArraySerializer.writeShortByteArray(serializer, data);
				break;
			}
		}
		return RecyclableSingletonList.create(serializer);
	}

}
