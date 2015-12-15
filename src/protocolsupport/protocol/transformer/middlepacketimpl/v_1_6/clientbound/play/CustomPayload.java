package protocolsupport.protocol.transformer.middlepacketimpl.v_1_6.clientbound.play;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import io.netty.buffer.Unpooled;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleCustomPayload;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;
import protocolsupport.protocol.transformer.v_1_6.utils.VillagerTradeTransformer;

public class CustomPayload extends MiddleCustomPayload<Collection<PacketData>> {

	@Override
	public Collection<PacketData> toData(ProtocolVersion version) throws IOException {
		PacketDataSerializer serializer = PacketDataSerializer.createNew(version);
		serializer.writeString(tag);
		if (tag.equals("MC|TrList")) {
			data = VillagerTradeTransformer.to16VillagerTradeList(new PacketDataSerializer(Unpooled.wrappedBuffer(data), version), version);
		}
		serializer.writeArray(data);
		return Collections.singletonList(new PacketData(ClientBoundPacket.PLAY_CUSTOM_PAYLOAD_ID, serializer));
	}

}
