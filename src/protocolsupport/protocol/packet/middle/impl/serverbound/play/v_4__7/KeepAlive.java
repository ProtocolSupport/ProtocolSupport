package protocolsupport.protocol.packet.middle.impl.serverbound.play.v_4__7;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.base.serverbound.play.MiddleKeepAlive;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV6;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV7;
import protocolsupport.protocol.storage.netcache.KeepAliveCache;

public class KeepAlive extends MiddleKeepAlive implements
IServerboundMiddlePacketV4,
IServerboundMiddlePacketV5,
IServerboundMiddlePacketV6,
IServerboundMiddlePacketV7 {

	protected final KeepAliveCache keepaliveCache = cache.getKeepAliveCache();

	public KeepAlive(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		keepAliveId = keepaliveCache.tryConfirmKeepAlive(clientdata.readInt());
	}

}
