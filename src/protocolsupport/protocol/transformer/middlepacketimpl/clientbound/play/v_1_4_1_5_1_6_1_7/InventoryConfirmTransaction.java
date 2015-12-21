package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6_1_7;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleInventoryConfirmTransaction;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;

public class InventoryConfirmTransaction extends MiddleInventoryConfirmTransaction<Collection<PacketData>> {

	@Override
	public Collection<PacketData> toData(ProtocolVersion version) throws IOException {
		PacketDataSerializer serializer = PacketDataSerializer.createNew(version);
		serializer.writeByte(windowId);
		serializer.writeShort(actionNumber);
		serializer.writeBoolean(accepted);
		return Collections.singletonList(new PacketData(ClientBoundPacket.PLAY_WINDOW_TRANSACTION_ID, serializer));
	}

}
