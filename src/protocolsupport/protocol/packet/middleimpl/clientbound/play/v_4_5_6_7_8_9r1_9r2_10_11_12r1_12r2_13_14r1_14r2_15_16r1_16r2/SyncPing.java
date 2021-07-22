package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSyncPing;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.storage.netcache.InventoryTransactionCache;

public class SyncPing extends MiddleSyncPing {

	public SyncPing(MiddlePacketInit init) {
		super(init);
	}

	protected final InventoryTransactionCache transactioncache = cache.getTransactionCache();

	@Override
	protected void write() {
		codec.writeClientbound(SyncPing.createTransaction(transactioncache.storeSyncPingServerId(id)));
	}

	public static ClientBoundPacketData createTransaction(int id) {
		ClientBoundPacketData inventorytransactionPacket = ClientBoundPacketData.create(ClientBoundPacketType.CLIENTBOUND_LEGACY_PLAY_WINDOW_TRANSACTION);
		inventorytransactionPacket.writeByte(0); //window id (0 - player window id)
		inventorytransactionPacket.writeShort(id);
		inventorytransactionPacket.writeBoolean(false); //accepted (false - not)
		return inventorytransactionPacket;
	}

}
