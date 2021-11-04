package protocolsupport.protocol.packet.middle.base.serverbound;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.MiddlePacket;
import protocolsupport.protocol.packet.middle.MiddlePacketCancelException;

public abstract class ServerBoundMiddlePacket extends MiddlePacket implements IServerboundMiddlePacket {

	protected ServerBoundMiddlePacket(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	public void decode(ByteBuf clientdata) {
		decode0(clientdata);
		cleanup();
	}

	private void decode0(ByteBuf serverdata) {
		try {
			read(serverdata);
			handle();
		} catch (MiddlePacketCancelException e) {
			return;
		}
		write();
	}

	protected abstract void read(ByteBuf clientdata);

	protected void handle() {
	}

	protected abstract void write();

	protected void cleanup() {
	}

}
