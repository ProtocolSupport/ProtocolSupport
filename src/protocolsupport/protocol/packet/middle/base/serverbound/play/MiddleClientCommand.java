package protocolsupport.protocol.packet.middle.base.serverbound.play;

import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.packet.ServerBoundPacketData;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.base.serverbound.ServerBoundMiddlePacket;

public abstract class MiddleClientCommand extends ServerBoundMiddlePacket {

	protected MiddleClientCommand(IMiddlePacketInit init) {
		super(init);
	}

	protected Command command;

	@Override
	protected void write() {
		if (command != null) {
			io.writeServerbound(create(command));
		}
	}

	public static ServerBoundPacketData create(Command command) {
		ServerBoundPacketData clientcommand = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_CLIENT_COMMAND);
		MiscDataCodec.writeVarIntEnum(clientcommand, command);
		return clientcommand;
	}

	public enum Command {
		REQUEST_RESPAWN, GET_STATS
	}

}
