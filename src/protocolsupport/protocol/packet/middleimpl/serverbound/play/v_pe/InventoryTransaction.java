package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.utils.recyclable.RecyclableCollection;

//The Pe GodPacket
public class InventoryTransaction extends ServerBoundMiddlePacket {

	protected int actionId;
	protected int invActionCount;
	
	@Override
	public void readFromClientData(ByteBuf clientdata) {
		actionId = VarNumberSerializer.readVarInt(clientdata);
		invActionCount = VarNumberSerializer.readVarInt(clientdata);
		for(int i = 0; i < invActionCount; i++) {
			//TODO: write these actions.
			int invActionType = VarNumberSerializer.readVarInt(clientdata);
		}
		switch(actionId) {
			//case 
		}
	}
	
	public static final int TRANSACTION_TYPE_NORMAL = 0;
	public static final int TRANSACTION_TYPE_INVENTORY_MISMATCH = 1;
	public static final int TRANSACTION_TYPE_ITEM_USE = 2;
	public static final int TRANSACTION_TYPE_ITEM_USE_ON_ENTITY = 3;
	public static final int TRANSACTION_TYPE_ITEM_RELEASE = 4;	

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		// TODO Auto-generated method stub
		return null;
	}

}
