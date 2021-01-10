package protocolsupport.protocol.packet.middle;

import io.netty.buffer.ByteBuf;

public abstract class ClientBoundMiddlePacket extends MiddlePacket {

	public ClientBoundMiddlePacket(MiddlePacketInit init) {
		super(init);
	}

	public void encode(ByteBuf serverdata) {
		encode0(serverdata);
		cleanup();
	}

	private void encode0(ByteBuf serverdata) {
		try {
			decode(serverdata);
			handle();
		} catch (CancelMiddlePacketException e) {
			return;
		}
		write();
	}

	protected abstract void decode(ByteBuf serverdata);

	protected void handle() {
	}

	protected abstract void write();

	protected void cleanup() {
	}

}
