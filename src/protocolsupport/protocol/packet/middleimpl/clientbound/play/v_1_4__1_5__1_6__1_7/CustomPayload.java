package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_4__1_5__1_6__1_7;

import io.netty.buffer.Unpooled;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleCustomPayload;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class CustomPayload extends MiddleCustomPayload<RecyclableCollection<ClientBoundPacketData>> {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_CUSTOM_PAYLOAD_ID, version);
		serializer.writeString(tag);
		ProtocolSupportPacketDataSerializer newdata = new ProtocolSupportPacketDataSerializer(Unpooled.buffer(), version);
		if (tag.equals("MC|TrList")) {
			newdata.writeMerchantData(data.readMerchantData());
		} else {
			newdata.writeBytes(data);
		}
		serializer.writeByteArray(newdata);
		return RecyclableSingletonList.create(serializer);
	}

}
