package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_13;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleSetBeaconEffect;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class SetBeaconEffect extends MiddleSetBeaconEffect {

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		primary = VarNumberSerializer.readVarInt(clientdata);
		secondary = VarNumberSerializer.readVarInt(clientdata);
	}

}
