package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15.AbstractSneakingCacheEntityAction;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class EntityAction extends AbstractSneakingCacheEntityAction {

	public EntityAction(MiddlePacketInit init) {
		super(init);
	}

	private static final ArrayMap<Action> actionById = new ArrayMap<>(
		new ArrayMap.Entry<>(1, Action.START_SNEAK), new ArrayMap.Entry<>(2, Action.STOP_SNEAK),
		new ArrayMap.Entry<>(3, Action.LEAVE_BED),
		new ArrayMap.Entry<>(4, Action.START_SPRINT), new ArrayMap.Entry<>(5, Action.STOP_SPRINT)
	);

	@Override
	protected void readClientData(ByteBuf clientdata) {
		entityId = clientdata.readInt();
		action = actionById.get(clientdata.readByte());
	}

}
