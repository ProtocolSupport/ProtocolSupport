package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleKeepAlive;
import protocolsupport.protocol.storage.netcache.KeepAliveCache;

public class KeepAlive extends MiddleKeepAlive {

	protected final KeepAliveCache keepaliveCache = cache.getKeepAliveCache();

	public KeepAlive(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void readClientData(ByteBuf clientdata) {
		keepAliveId = keepaliveCache.tryConfirmKeepAlive(clientdata.readInt());
	}

}
