package protocolsupport.protocol.packet.middle;

import io.netty.buffer.ByteBuf;

public abstract class ServerBoundMiddlePacket extends MiddlePacket {

	public ServerBoundMiddlePacket(MiddlePacketInit init) {
		super(init);
	}

	public void decode(ByteBuf clientdata) {
		decode0(clientdata);
		cleanup();
	}

	private void decode0(ByteBuf serverdata) {
		try {
			read(serverdata);
			handle();
		} catch (CancelMiddlePacketException e) {
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
