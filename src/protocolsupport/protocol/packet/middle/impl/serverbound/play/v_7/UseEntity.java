package protocolsupport.protocol.packet.middle.impl.serverbound.play.v_7;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV7;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15.AbstractSneakingCacheUseEntity;
import protocolsupport.protocol.types.UsedHand;

public class UseEntity extends AbstractSneakingCacheUseEntity implements IServerboundMiddlePacketV7 {

	public UseEntity(IMiddlePacketInit init) {
		super(init);
		hand = UsedHand.MAIN;
	}

	@Override
	protected void read(ByteBuf clientdata) {
		entityId = clientdata.readInt();
		action = MiscDataCodec.readByteEnum(clientdata, Action.CONSTANT_LOOKUP);
	}

}
