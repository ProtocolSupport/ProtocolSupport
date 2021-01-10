package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15.AbstractSneakingCacheUseEntity;
import protocolsupport.protocol.types.UsedHand;

public class UseEntity extends AbstractSneakingCacheUseEntity {

	public UseEntity(MiddlePacketInit init) {
		super(init);
		hand = UsedHand.MAIN;
	}

	@Override
	protected void read(ByteBuf clientdata) {
		clientdata.skipBytes(Integer.BYTES);
		entityId = clientdata.readInt();
		action = clientdata.readBoolean() ? Action.ATTACK : Action.INTERACT;
	}

}
