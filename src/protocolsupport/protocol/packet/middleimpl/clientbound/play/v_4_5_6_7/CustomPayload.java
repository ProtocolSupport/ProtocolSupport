package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleCustomPayload;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.MerchantDataSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class CustomPayload extends MiddleCustomPayload {

	private final ByteBuf newdata = Unpooled.buffer();

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_CUSTOM_PAYLOAD_ID, version);
		StringSerializer.writeString(serializer, version, tag);
		if (tag.equals("MC|TrList")) {
			MerchantDataSerializer.writeMerchantData(newdata, version, cache.getLocale(), MerchantDataSerializer.readMerchantData(data, ProtocolVersionsHelper.LATEST_PC, cache.getLocale()), true);
		} else {
			newdata.writeBytes(data);
		}
		ArraySerializer.writeByteArray(serializer, version, newdata);
		return RecyclableSingletonList.create(serializer);
	}

}
