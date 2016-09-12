package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_4__1_5__1_6__1_7;

import java.io.IOException;

import io.netty.buffer.Unpooled;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleCustomPayload;
import protocolsupport.protocol.packet.middleimpl.PacketData;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.protocol.typeremapper.nbt.custompayload.CustomPayloadSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class CustomPayload extends MiddleCustomPayload<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) throws IOException {
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_CUSTOM_PAYLOAD_ID, version);
		serializer.writeString(tag);
		CustomPayloadSerializer serverdata = new CustomPayloadSerializer(new ProtocolSupportPacketDataSerializer(Unpooled.wrappedBuffer(data), ProtocolVersion.getLatest()));
		CustomPayloadSerializer clientdata = new CustomPayloadSerializer(version);
		if (tag.equals("MC|TrList")) {
			clientdata.writeMerchantData(serverdata.readMerchantData());
		} else {
			clientdata.copyAll(serverdata);
		}
		serializer.writeByteArray(clientdata.toData());
		return RecyclableSingletonList.create(serializer);
	}

}
