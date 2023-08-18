package protocolsupport.protocol.packet.middle.impl.serverbound.play.v_4__5;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_4__15.AbstractSneakingCacheEntityAction;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class EntityAction extends AbstractSneakingCacheEntityAction implements
IServerboundMiddlePacketV4,
IServerboundMiddlePacketV5 {

	public EntityAction(IMiddlePacketInit init) {
		super(init);
	}

	private static final ArrayMap<Action> actionById = new ArrayMap<>(
		new ArrayMap.Entry<>(1, Action.START_SNEAK), new ArrayMap.Entry<>(2, Action.STOP_SNEAK),
		new ArrayMap.Entry<>(3, Action.LEAVE_BED),
		new ArrayMap.Entry<>(4, Action.START_SPRINT), new ArrayMap.Entry<>(5, Action.STOP_SPRINT)
	);

	@Override
	protected void read(ByteBuf clientdata) {
		entityId = clientdata.readInt();
		action = actionById.get(clientdata.readByte());
	}

}
