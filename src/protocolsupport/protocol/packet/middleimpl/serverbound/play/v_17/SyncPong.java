package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_17;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleSyncPong;

public class SyncPong extends MiddleSyncPong {

	public SyncPong(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		id = clientdata.readInt();
	}

}
