package protocolsupport.protocol.packet.middle.impl.serverbound.play.v_20;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.OptionalCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.base.serverbound.play.MiddleSetBeaconEffect;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV20;

public class SetBeaconEffect extends MiddleSetBeaconEffect implements
IServerboundMiddlePacketV20 {

	public SetBeaconEffect(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		primary = OptionalCodec.readOptional(clientdata, VarNumberCodec::readVarInt);
		secondary = OptionalCodec.readOptional(clientdata, VarNumberCodec::readVarInt);
	}

}
