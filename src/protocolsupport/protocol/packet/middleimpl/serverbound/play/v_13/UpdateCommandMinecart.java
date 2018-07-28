package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_13;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleUpdateCommandMinecart;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class UpdateCommandMinecart extends MiddleUpdateCommandMinecart {

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		entityId = VarNumberSerializer.readVarInt(clientdata);
		command = StringSerializer.readString(clientdata, connection.getVersion());
		trackOutput = clientdata.readBoolean();
	}

}
