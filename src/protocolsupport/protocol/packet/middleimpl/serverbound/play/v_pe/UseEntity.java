package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import org.bukkit.util.Vector;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleUseEntity;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class UseEntity extends MiddleUseEntity{

	private static final int ATTACK = 1;
	private static final int INTERACT = 2;
//	private static final int LEAVE_VEHICLE = 3;
//	private static final int HOVER = 4;
	
	@Override
	public void readFromClientData(ByteBuf clientdata, ProtocolVersion version) {
		int type = clientdata.readUnsignedByte();
		entityId = (int) VarNumberSerializer.readVarLong(clientdata);
		switch(type){
			case ATTACK: {
				action = Action.ATTACK;
			break;
			}
			case INTERACT:
				action = Action.INTERACT;
			break;
		}
		interactedAt = new Vector();
		usedHand = 0;
	}

}
