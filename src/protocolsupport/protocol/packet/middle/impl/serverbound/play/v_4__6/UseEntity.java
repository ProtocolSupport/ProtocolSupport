package protocolsupport.protocol.packet.middle.impl.serverbound.play.v_4__6;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV6;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_4__15.AbstractSneakingCacheUseEntity;
import protocolsupport.protocol.types.UsedHand;

public class UseEntity extends AbstractSneakingCacheUseEntity implements
IServerboundMiddlePacketV4,
IServerboundMiddlePacketV5,
IServerboundMiddlePacketV6 {

	public UseEntity(IMiddlePacketInit init) {
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
