package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4_1_5_1_6_1_7_1_8;

import java.io.IOException;

import protocolsupport.protocol.packet.middle.serverbound.play.MiddleCreativeSetSlot;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public class CreativeSetSlot extends MiddleCreativeSetSlot {

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) throws IOException {
		slot = serializer.readShort();
		itemstack = serializer.readItemStack();
	}

}
