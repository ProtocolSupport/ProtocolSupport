package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_13_14r1_14r2_15;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleSetBeaconEffect;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class SetBeaconEffect extends MiddleSetBeaconEffect {

	public SetBeaconEffect(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void readClientData(ByteBuf clientdata) {
		primary = VarNumberSerializer.readVarInt(clientdata);
		secondary = VarNumberSerializer.readVarInt(clientdata);
	}

}
