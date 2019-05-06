package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleEntityAction;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class EntityAction extends MiddleEntityAction {

	public EntityAction(ConnectionImpl connection) {
		super(connection);
	}

	private static final ArrayMap<Action> actionById8 = new ArrayMap<>(
		new ArrayMap.Entry<>(0, Action.START_SNEAK), new ArrayMap.Entry<>(1, Action.STOP_SNEAK),
		new ArrayMap.Entry<>(2, Action.LEAVE_BED),
		new ArrayMap.Entry<>(3, Action.START_SPRINT), new ArrayMap.Entry<>(4, Action.STOP_SPRINT),
		new ArrayMap.Entry<>(5, Action.STOP_JUMP), //this won't work now anyway, but still map it
		new ArrayMap.Entry<>(6, Action.OPEN_HORSE_INV)
	);

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		entityId = VarNumberSerializer.readVarInt(clientdata);
		int actionId = VarNumberSerializer.readVarInt(clientdata);
		jumpBoost = VarNumberSerializer.readVarInt(clientdata);
		if (version == ProtocolVersion.MINECRAFT_1_8) {
			action = actionById8.get(actionId);
		} else {
			action = Action.CONSTANT_LOOKUP.getByOrdinal(actionId);
		}
	}

}
