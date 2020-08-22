package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;

public abstract class MiddleClientCommand extends ServerBoundMiddlePacket {

	public MiddleClientCommand(MiddlePacketInit init) {
		super(init);
	}

	protected Command command;

	@Override
	protected void writeToServer() {
		if (command != null) {
			codec.read(create(command));
		}
	}

	public static ServerBoundPacketData create(Command command) {
		ServerBoundPacketData clientcommand = ServerBoundPacketData.create(PacketType.SERVERBOUND_PLAY_CLIENT_COMMAND);
		MiscSerializer.writeVarIntEnum(clientcommand, command);
		return clientcommand;
	}

	public static enum Command {
		REQUEST_RESPAWN, GET_STATS
	}

}
