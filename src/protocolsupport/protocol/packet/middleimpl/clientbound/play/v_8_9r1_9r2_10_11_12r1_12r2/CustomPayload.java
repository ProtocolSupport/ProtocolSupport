package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2;

import io.netty.buffer.Unpooled;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleCustomPayload;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MerchantDataSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.typeremapper.legacy.LegacyCustomPayloadChannelName;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.utils.Utils;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class CustomPayload extends MiddleCustomPayload {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_CUSTOM_PAYLOAD_ID);
		StringSerializer.writeString(
			serializer, version,
			Utils.clampString(LegacyCustomPayloadChannelName.toPre13(tag), 20)
		);
		switch (tag) {
			case (LegacyCustomPayloadChannelName.MODERN_TRADER_LIST): {
				String locale = cache.getAttributesCache().getLocale();
				MerchantDataSerializer.writeMerchantData(
					serializer, connection.getVersion(), locale,
					MerchantDataSerializer.readMerchantData(Unpooled.wrappedBuffer(data), ProtocolVersionsHelper.LATEST_PC, locale)
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
