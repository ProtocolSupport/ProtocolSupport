package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6_1_7;

import java.io.IOException;

import io.netty.buffer.Unpooled;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleCustomPayload;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;
import protocolsupport.protocol.typeremapper.nbt.custompayload.CustomPayloadSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class CustomPayload extends MiddleCustomPayload<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) throws IOException {
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_CUSTOM_PAYLOAD_ID, version);
		serializer.writeString(tag);
		CustomPayloadSerializer serverdata = new CustomPayloadSerializer(new PacketDataSerializer(Unpooled.wrappedBuffer(data), ProtocolVersion.getLatest()));
		CustomPayloadSerializer clientdata = new CustomPayloadSerializer(version);
		if (tag.equals("MC|TrList")) {
			clientdata.writeMerchantData(serverdata.readMerchantData());
		} else {
			clientdata.copyAll(serverdata);
		}
		serializer.writeArray(clientdata.toData());
		return RecyclableSingletonList.create(serializer);
	}

}
