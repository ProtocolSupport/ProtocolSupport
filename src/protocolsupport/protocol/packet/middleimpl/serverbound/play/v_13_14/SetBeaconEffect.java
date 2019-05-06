package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_13_14;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleSetBeaconEffect;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class SetBeaconEffect extends MiddleSetBeaconEffect {

	public SetBeaconEffect(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		primary = VarNumberSerializer.readVarInt(clientdata);
		secondary = VarNumberSerializer.readVarInt(clientdata);
	}

}
