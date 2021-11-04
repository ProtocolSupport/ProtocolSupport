package protocolsupport.protocol.packet.middle.base.clientbound;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.MiddlePacket;
import protocolsupport.protocol.packet.middle.MiddlePacketCancelException;

public abstract class ClientBoundMiddlePacket extends MiddlePacket implements IClientboundMiddlePacket {

	protected ClientBoundMiddlePacket(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	public void encode(ByteBuf serverdata) {
		encode0(serverdata);
		cleanup();
	}

	private void encode0(ByteBuf serverdata) {
		try {
			decode(serverdata);
			handle();
		} catch (MiddlePacketCancelException e) {
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
