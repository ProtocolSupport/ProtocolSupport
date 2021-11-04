package protocolsupport.protocol.packet.middle.base.serverbound.play;

import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ServerBoundPacketData;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.base.serverbound.ServerBoundMiddlePacket;

public abstract class MiddleUpdateCommandMinecart extends ServerBoundMiddlePacket {

	protected MiddleUpdateCommandMinecart(IMiddlePacketInit init) {
		super(init);
	}

	protected int entityId;
	protected String command;
	protected boolean trackOutput;

	@Override
	protected void write() {
		io.writeServerbound(create(entityId, command, trackOutput));
	}

	public static ServerBoundPacketData create(int entityId, String command, boolean trackOutput) {
		ServerBoundPacketData updatecommandminecart = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_UPDATE_COMMAND_MINECART);
		VarNumberCodec.writeVarInt(updatecommandminecart, entityId);
		StringCodec.writeVarIntUTF8String(updatecommandminecart, command);
		updatecommandminecart.writeBoolean(trackOutput);
		return updatecommandminecart;
	}

}
