package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleUseEntity;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class Interact extends ServerBoundMiddlePacket{
	
	protected short action;
	protected long target;
	
	
	@Override
	public void readFromClientData(ByteBuf clientdata, ProtocolVersion version) {
		action = clientdata.readUnsignedByte();
		target = VarNumberSerializer.readVarLong(clientdata);
	}
	
	private static final int ATTACK = 2; //The site says one but wth? I 
//	private static final int INTERACT = 2;
//	private static final int LEAVE_VEHICLE = 3;
//	private static final int HOVER = 4;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		System.out.println("E: " + target + " - " + action);
		switch(action){
			case ATTACK: {
				System.out.println("ATTACKING!!");
				return RecyclableSingletonList.create(MiddleUseEntity.create((int) target, MiddleUseEntity.Action.ATTACK, null, 0));
			}
			//case INTERACT: {
			//	return RecyclableSingletonList.create(MiddleUseEntity.create((int) target, MiddleUseEntity.Action.INTERACT, null, 0));
			//}
			default: {
				return RecyclableEmptyList.get();
			}
		}
	}

}
