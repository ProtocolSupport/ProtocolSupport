package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleUpdateCommandMinecart extends ServerBoundMiddlePacket {

	public MiddleUpdateCommandMinecart(ConnectionImpl connection) {
		super(connection);
	}

	protected int entityId;
	protected String command;
	protected boolean trackOutput;

	@Override
	protected void writeToServer() {
		codec.read(create(entityId, command, trackOutput));
	}

	public static ServerBoundPacketData create(int entityId, String command, boolean trackOutput) {
		ServerBoundPacketData updatecommandminecart = ServerBoundPacketData.create(PacketType.SERVERBOUND_PLAY_UPDATE_COMMAND_MINECART);
		VarNumberSerializer.writeVarInt(updatecommandminecart, entityId);
		StringSerializer.writeVarIntUTF8String(updatecommandminecart, command);
		updatecommandminecart.writeBoolean(trackOutput);
		return updatecommandminecart;
	}

}
