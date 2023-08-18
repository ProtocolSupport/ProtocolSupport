package protocolsupport.protocol.packet.middle.impl.serverbound.play.v_6__7;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV6;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV7;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_4__15.AbstractSneakingCacheEntityAction;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class EntityAction extends AbstractSneakingCacheEntityAction implements
IServerboundMiddlePacketV6,
IServerboundMiddlePacketV7 {

	public EntityAction(IMiddlePacketInit init) {
		super(init);
	}

	private static final ArrayMap<Action> actionById = new ArrayMap<>(
		new ArrayMap.Entry<>(1, Action.START_SNEAK), new ArrayMap.Entry<>(2, Action.STOP_SNEAK),
		new ArrayMap.Entry<>(3, Action.LEAVE_BED),
		new ArrayMap.Entry<>(4, Action.START_SPRINT), new ArrayMap.Entry<>(5, Action.STOP_SPRINT),
		new ArrayMap.Entry<>(6, Action.STOP_JUMP),
		new ArrayMap.Entry<>(7, Action.OPEN_HORSE_INV)
	);

	@Override
	protected void read(ByteBuf clientdata) {
		entityId = clientdata.readInt();
		action = actionById.get(clientdata.readByte());
		jumpBoost = clientdata.readInt();
	}

}
