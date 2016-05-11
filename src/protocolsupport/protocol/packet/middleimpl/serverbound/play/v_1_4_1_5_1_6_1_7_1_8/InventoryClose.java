package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4_1_5_1_6_1_7_1_8;

import java.io.IOException;

import protocolsupport.protocol.packet.middle.serverbound.play.MiddleInventoryClose;
import protocolsupport.protocol.serializer.PacketDataSerializer;

public class InventoryClose extends MiddleInventoryClose {

	@Override
	public void readFromClientData(PacketDataSerializer serializer) throws IOException {
		windowId = serializer.readUnsignedByte();
	}

}
