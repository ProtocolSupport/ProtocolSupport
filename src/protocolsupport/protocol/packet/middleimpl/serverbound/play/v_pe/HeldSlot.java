package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleHeldSlot;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class HeldSlot extends MiddleHeldSlot {

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		slot = VarNumberSerializer.readVarInt(clientdata);
		int windowId = clientdata.readByte(); //windowID
		int[] linkedSlots = new int[VarNumberSerializer.readVarInt(clientdata)];
		for(int i = 0; i < linkedSlots.length; i++) {
			linkedSlots[i] = VarNumberSerializer.readVarInt(clientdata);
		}
		System.out.println("Client held slot: " + slot + " windowId: " + windowId + " linkedSlots: " + linkedSlots.toString());
	}

}
