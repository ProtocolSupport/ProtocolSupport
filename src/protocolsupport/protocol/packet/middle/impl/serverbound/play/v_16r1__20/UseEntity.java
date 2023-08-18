package protocolsupport.protocol.packet.middle.impl.serverbound.play.v_16r1__20;

import org.bukkit.util.Vector;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.base.serverbound.play.MiddleUseEntity;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV16r1;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV16r2;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV17r1;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV17r2;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV18;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV20;
import protocolsupport.protocol.types.UsedHand;

public class UseEntity extends MiddleUseEntity implements
IServerboundMiddlePacketV16r1,
IServerboundMiddlePacketV16r2,
IServerboundMiddlePacketV17r1,
IServerboundMiddlePacketV17r2,
IServerboundMiddlePacketV18,
IServerboundMiddlePacketV20 {

	public UseEntity(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		entityId = VarNumberCodec.readVarInt(clientdata);
		action = MiscDataCodec.readVarIntEnum(clientdata, Action.CONSTANT_LOOKUP);
		switch (action) {
			case INTERACT: {
				hand = MiscDataCodec.readVarIntEnum(clientdata, UsedHand.CONSTANT_LOOKUP);
				break;
			}
			case INTERACT_AT: {
				interactedAt = new Vector(clientdata.readFloat(), clientdata.readFloat(), clientdata.readFloat());
				hand = MiscDataCodec.readVarIntEnum(clientdata, UsedHand.CONSTANT_LOOKUP);
				break;
			}
			case ATTACK: {
				break;
			}
		}
		sneaking = clientdata.readBoolean();
	}

}
