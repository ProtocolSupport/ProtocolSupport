package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;

public abstract class MiddleClientCommand extends ServerBoundMiddlePacket {

	protected MiddleClientCommand(MiddlePacketInit init) {
		super(init);
	}

	protected Command command;

	@Override
	protected void write() {
		if (command != null) {
			codec.writeServerbound(create(command));
		}
	}

	public static ServerBoundPacketData create(Command command) {
		ServerBoundPacketData clientcommand = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_CLIENT_COMMAND);
		MiscSerializer.writeVarIntEnum(clientcommand, command);
		return clientcommand;
	}

	public enum Command {
		REQUEST_RESPAWN, GET_STATS
	}

}
