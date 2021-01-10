package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleClientCommand;

public class ClientCommand extends MiddleClientCommand {

	public ClientCommand(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		clientdata.readByte();
		command = Command.REQUEST_RESPAWN;
	}

}
