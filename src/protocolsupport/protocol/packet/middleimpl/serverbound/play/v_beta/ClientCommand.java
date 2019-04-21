package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_beta;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleClientCommand;

public class ClientCommand extends MiddleClientCommand {

	public ClientCommand(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		clientdata.readByte();
		command = Command.REQUEST_RESPAWN;
	}

}
