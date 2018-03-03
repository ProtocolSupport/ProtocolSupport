package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleCustomPayload;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MerchantDataSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class CustomPayload extends MiddleCustomPayload {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_CUSTOM_PAYLOAD_ID, version);
		StringSerializer.writeString(serializer, version, tag);
		if (tag.equals("MC|TrList")) {
			MerchantDataSerializer.writeMerchantData(
				serializer, version, cache.getAttributesCache().getLocale(),
				MerchantDataSerializer.readMerchantData(data, ProtocolVersionsHelper.LATEST_PC, cache.getAttributesCache().getLocale())
			);
		} else {
			serializer.writeBytes(data);
		}
		return RecyclableSingletonList.create(serializer);
	}

}
