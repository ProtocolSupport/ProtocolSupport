package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_6_7;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleEntityAction;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class EntityAction extends MiddleEntityAction {

	public EntityAction(ConnectionImpl connection) {
		super(connection);
	}

	private static final ArrayMap<Action> actionById = new ArrayMap<>(
		new ArrayMap.Entry<>(1, Action.START_SNEAK), new ArrayMap.Entry<>(2, Action.STOP_SNEAK),
		new ArrayMap.Entry<>(3, Action.LEAVE_BED),
		new ArrayMap.Entry<>(4, Action.START_SPRINT), new ArrayMap.Entry<>(5, Action.STOP_SPRINT),
		new ArrayMap.Entry<>(6, Action.STOP_JUMP),
		new ArrayMap.Entry<>(7, Action.OPEN_HORSE_INV)
	);

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		entityId = clientdata.readInt();
		action = actionById.get(clientdata.readByte());
		jumpBoost = clientdata.readInt();
	}

}
