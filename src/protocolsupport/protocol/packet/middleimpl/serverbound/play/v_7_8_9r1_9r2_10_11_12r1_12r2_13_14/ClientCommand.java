package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_7_8_9r1_9r2_10_11_12r1_12r2_13_14;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleClientCommand;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.Utils;

public class ClientCommand extends MiddleClientCommand {

	public ClientCommand(ConnectionImpl connection) {
		super(connection);
	}

	private static final Command[] commandsById = new Command[] { Command.REQUEST_RESPAWN, Command.GET_STATS };

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		command = Utils.getFromArrayOrNull(commandsById, VarNumberSerializer.readVarInt(clientdata));
	}

}
