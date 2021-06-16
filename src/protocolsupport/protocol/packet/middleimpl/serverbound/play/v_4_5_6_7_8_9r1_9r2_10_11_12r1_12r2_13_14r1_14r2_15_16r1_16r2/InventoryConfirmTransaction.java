package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleSyncPong;
import protocolsupport.protocol.storage.netcache.KeepAliveCache;

public class InventoryConfirmTransaction extends ServerBoundMiddlePacket {

	public InventoryConfirmTransaction(MiddlePacketInit init) {
		super(init);
	}

	protected final KeepAliveCache keepaliveCache = cache.getKeepAliveCache();

	protected short actionNumber;

	@Override
	protected void read(ByteBuf clientdata) {
		clientdata.readUnsignedByte(); //window id
		actionNumber = clientdata.readShort();
		clientdata.readBoolean(); //accepted
	}

	@Override
	protected void write() {
		codec.writeServerbound(MiddleSyncPong.create(keepaliveCache.tryConfirmSyncPing(actionNumber)));
	}

}
