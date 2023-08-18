package protocolsupport.protocol.packet.middle.impl.serverbound.play.v_8;

import org.bukkit.util.Vector;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV8;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_4__15.AbstractSneakingCacheUseEntity;
import protocolsupport.protocol.types.UsedHand;

public class UseEntity extends AbstractSneakingCacheUseEntity implements IServerboundMiddlePacketV8 {

	public UseEntity(IMiddlePacketInit init) {
		super(init);
		hand = UsedHand.MAIN;
	}

	@Override
	protected void read(ByteBuf clientdata) {
		entityId = VarNumberCodec.readVarInt(clientdata);
		action = MiscDataCodec.readVarIntEnum(clientdata, Action.CONSTANT_LOOKUP);
		if (action == Action.INTERACT_AT) {
			interactedAt = new Vector(clientdata.readFloat(), clientdata.readFloat(), clientdata.readFloat());
		}
	}

}
