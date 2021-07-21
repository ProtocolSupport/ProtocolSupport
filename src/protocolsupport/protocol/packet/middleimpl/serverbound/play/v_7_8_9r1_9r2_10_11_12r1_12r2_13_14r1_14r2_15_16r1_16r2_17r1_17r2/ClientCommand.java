package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2_17r1_17r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleClientCommand;
import protocolsupport.utils.CollectionsUtils;

public class ClientCommand extends MiddleClientCommand {

	public ClientCommand(MiddlePacketInit init) {
		super(init);
	}

	private static final Command[] commandsById = { Command.REQUEST_RESPAWN, Command.GET_STATS };

	@Override
	protected void read(ByteBuf clientdata) {
		command = CollectionsUtils.getFromArrayOrNull(commandsById, VarNumberCodec.readVarInt(clientdata));
	}

}
