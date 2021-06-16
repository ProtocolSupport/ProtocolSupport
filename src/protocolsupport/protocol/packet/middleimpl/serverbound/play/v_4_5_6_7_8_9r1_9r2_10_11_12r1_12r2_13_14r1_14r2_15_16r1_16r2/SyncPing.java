package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSyncPing;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.storage.netcache.KeepAliveCache;

public class SyncPing extends MiddleSyncPing {

	public SyncPing(MiddlePacketInit init) {
		super(init);
	}

	protected final KeepAliveCache keepaliveCache = cache.getKeepAliveCache();

	/*
	 * Emulate sync ping packet with inventory transaction packet
	 * Send player window container not accepted transaction with action number of ping id
	 * Client will respond with apology transaction with the same action number
	 */
	@Override
	protected void write() {
		ClientBoundPacketData inventorytransactionPacket = ClientBoundPacketData.create(ClientBoundPacketType.CLIENTBOUND_LEGACY_PLAY_WINDOW_TRANSACTION);
		inventorytransactionPacket.writeByte(0); //window id (0 - player window id)
		inventorytransactionPacket.writeShort(keepaliveCache.storeServerSyncPingId(id));
		inventorytransactionPacket.writeBoolean(false); //accepted (false - not)
		codec.writeClientbound(inventorytransactionPacket);
	}

}
