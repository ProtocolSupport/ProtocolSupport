package protocolsupport.protocol.packet.middle.impl.serverbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2_17r1_17r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV10;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV11;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV12r1;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV12r2;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV13;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV14r1;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV14r2;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV15;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV16r1;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV16r2;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV17r1;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV17r2;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV8;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV9r1;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV9r2;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15.AbstractSneakingCacheEntityAction;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class EntityAction extends AbstractSneakingCacheEntityAction implements
IServerboundMiddlePacketV8,
IServerboundMiddlePacketV9r1,
IServerboundMiddlePacketV9r2,
IServerboundMiddlePacketV10,
IServerboundMiddlePacketV11,
IServerboundMiddlePacketV12r1,
IServerboundMiddlePacketV12r2,
IServerboundMiddlePacketV13,
IServerboundMiddlePacketV14r1,
IServerboundMiddlePacketV14r2,
IServerboundMiddlePacketV15,
IServerboundMiddlePacketV16r1,
IServerboundMiddlePacketV16r2,
IServerboundMiddlePacketV17r1,
IServerboundMiddlePacketV17r2 {

	public EntityAction(IMiddlePacketInit init) {
		super(init);
	}

	private static final ArrayMap<Action> actionById8 = new ArrayMap<>(
		new ArrayMap.Entry<>(0, Action.START_SNEAK), new ArrayMap.Entry<>(1, Action.STOP_SNEAK),
		new ArrayMap.Entry<>(2, Action.LEAVE_BED),
		new ArrayMap.Entry<>(3, Action.START_SPRINT), new ArrayMap.Entry<>(4, Action.STOP_SPRINT),
		new ArrayMap.Entry<>(5, Action.STOP_JUMP), //this won't work now anyway, but still map it
		new ArrayMap.Entry<>(6, Action.OPEN_HORSE_INV)
	);

	@Override
	protected void read(ByteBuf clientdata) {
		entityId = VarNumberCodec.readVarInt(clientdata);
		int actionId = VarNumberCodec.readVarInt(clientdata);
		jumpBoost = VarNumberCodec.readVarInt(clientdata);
		if (version == ProtocolVersion.MINECRAFT_1_8) {
			action = actionById8.get(actionId);
		} else {
			action = Action.CONSTANT_LOOKUP.getByOrdinal(actionId);
		}
	}

}
