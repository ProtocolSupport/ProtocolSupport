package protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v_1_4_1_5_1_6_1_7;

import java.io.IOException;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.serverbound.play.MiddleInventoryClose;

public class InventoryClose extends MiddleInventoryClose {

	@Override
	public void readFromClientData(PacketDataSerializer serializer) throws IOException {
		windowId = serializer.readUnsignedByte();
	}

}
