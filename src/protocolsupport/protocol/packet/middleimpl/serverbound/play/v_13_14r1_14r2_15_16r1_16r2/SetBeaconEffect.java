package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_13_14r1_14r2_15_16r1_16r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleSetBeaconEffect;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class SetBeaconEffect extends MiddleSetBeaconEffect {

	public SetBeaconEffect(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void readClientData(ByteBuf clientdata) {
		primary = VarNumberSerializer.readVarInt(clientdata);
		secondary = VarNumberSerializer.readVarInt(clientdata);
	}

}
