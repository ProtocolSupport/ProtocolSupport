package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_13_14r1_14r2_15_16r1_16r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleSetBeaconEffect;

public class SetBeaconEffect extends MiddleSetBeaconEffect {

	public SetBeaconEffect(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		primary = VarNumberCodec.readVarInt(clientdata);
		secondary = VarNumberCodec.readVarInt(clientdata);
	}

}
