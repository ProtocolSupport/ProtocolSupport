package protocolsupport.protocol.packet.middle.impl.serverbound.play.v_4__6;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.base.serverbound.play.MiddleClientCommand;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV6;

public class ClientCommand extends MiddleClientCommand implements
IServerboundMiddlePacketV4,
IServerboundMiddlePacketV5,
IServerboundMiddlePacketV6 {

	public ClientCommand(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		clientdata.readByte();
		command = Command.REQUEST_RESPAWN;
	}

}
