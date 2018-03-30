package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import protocolsupport.api.Connection;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.listeners.InternalPluginMessageRequest;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleCustomPayload;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.NetworkDataCache;
import protocolsupport.protocol.storage.netcache.PEInventoryCache;
import protocolsupport.protocol.storage.netcache.WindowCache;
import protocolsupport.protocol.typeremapper.pe.inventory.PESlotRemapper;
import protocolsupport.protocol.typeremapper.pe.inventory.PETransactionRemapper;
import protocolsupport.protocol.typeremapper.pe.inventory.PEInventory.PESource;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.types.GameMode;
import protocolsupport.protocol.utils.types.WindowType;
import protocolsupport.utils.Utils;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagType;

//The PE GodPacket! See [Documentation](https://github.com/ProtocolSupport/ProtocolSupport/wiki/PSPE:-GodPacket)
public class GodPacket extends ServerBoundMiddlePacket {

	//Wrapped packet
	protected static final int ACTION_NORMAL = 0;
	protected static final int ACTION_MISMATCH = 1;
	protected static final int ACTION_USE_ITEM = 2;
	protected static final int ACTION_USE_ENTITY = 3;
	protected static final int ACTION_RELEASE_ITEM = 4;

	protected UseItem useItemMiddlePacket = new UseItem();
	protected UseEntity useEntityMiddlePacket = new UseEntity();
	protected ReleaseItem releaseItemMiddlePacket = new ReleaseItem();

	@Override
	public void setConnection(Connection connection) {
		super.setConnection(connection);
		useItemMiddlePacket.setConnection(connection);
		useEntityMiddlePacket.setConnection(connection);
		releaseItemMiddlePacket.setConnection(connection);
	}

	@Override
	public void setSharedStorage(NetworkDataCache sharedstorage) {
		super.setSharedStorage(sharedstorage);
		useItemMiddlePacket.setSharedStorage(sharedstorage);
		useEntityMiddlePacket.setSharedStorage(sharedstorage);
		releaseItemMiddlePacket.setSharedStorage(sharedstorage);
	}

	protected int actionId;
	protected InvTransaction[] transactions;
	protected ServerBoundMiddlePacket simpleActionMiddlePacket;

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		String locale = cache.getAttributesCache().getLocale();
		actionId = VarNumberSerializer.readVarInt(clientdata);
		transactions = new InvTransaction[VarNumberSerializer.readVarInt(clientdata)];
		for(int i = 0; i < transactions.length; i++) {
			transactions[i] = InvTransaction.readFromStream(clientdata, locale, connection.getVersion());
		}
		switch (actionId) {
			case ACTION_USE_ITEM: {
				simpleActionMiddlePacket = useItemMiddlePacket;
				break;
			}
			case ACTION_USE_ENTITY: {
				simpleActionMiddlePacket = useEntityMiddlePacket;
				break;
			}
			case ACTION_RELEASE_ITEM: {
				simpleActionMiddlePacket = releaseItemMiddlePacket;
				break;
			}
			case ACTION_NORMAL:
			case ACTION_MISMATCH:
			default: {
				simpleActionMiddlePacket = null;
				break;
			}
		}
		if (simpleActionMiddlePacket != null) {
			simpleActionMiddlePacket.readFromClientData(clientdata);
		}
		clientdata.skipBytes(clientdata.readableBytes());
	}

	public static final int SOURCE_CONTAINER = 0;
	public static final int SOURCE_GLOBAL = 1;
	public static final int SOURCE_WORLD_INTERACTION = 2;
	public static final int SOURCE_CREATIVE = 3;
	public static final int SOURCE_TODO = 99999;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		PEInventoryCache invCache = cache.getPEInventoryCache();
		WindowCache winCache = cache.getWindowCache();
		RecyclableArrayList<ServerBoundPacketData> packets = RecyclableArrayList.create();
		if (simpleActionMiddlePacket != null) {
			packets.addAll(simpleActionMiddlePacket.toNative());
		} else if (actionId == ACTION_NORMAL) {
			PETransactionRemapper remapper = invCache.getTransactionRemapper();
			cache.getPEInventoryCache().lockInventory();
			for (InvTransaction transaction : transactions) {
				PESlotRemapper.remapServerboundSlot(cache, transaction);
				if (remapper.isCreativeTransaction(cache)) {
					remapper.processCreativeTransaction(cache, transaction, packets);
				} else {
					if ((winCache.getOpenedWindow() != WindowType.ENCHANT) || 
							invCache.getFakeEnchanting().handleInventoryClick(cache, transaction, packets)) {
						if (winCache.getOpenedWindow() == WindowType.ANVIL) {
							processAnvilName(transaction, packets);
						}
						remapper.cacheTransaction(transaction);	
					}
				}
			}
			remapper.processTransactions(cache, packets);
		}
		if (invCache.shouldSendUpdate() && cache.getAttributesCache().getPEGameMode() != GameMode.CREATIVE) {
			//Trigger inventory update, ALWAYS since PE sometimes 'guesses' or doesn't trust the server, we generally want an inventory update scheduled.
			InternalPluginMessageRequest.receivePluginMessageRequest(connection, new InternalPluginMessageRequest.InventoryUpdateRequest(7));
			invCache.lockInventoryUpdate();
		}
		return packets;
	}

	public static class InvTransaction {
		
		//Special slot ids from remapping.
		public static final int CURSOR = -1;
		public static final int TABLE = -333;
		public static final int MISSING_REMAP = -666;
		public static final int DROP = -999;
		private int sourceId;
		private int inventoryId;
		private int action;
		private int slot;
		private ItemStackWrapper oldItem;
		private ItemStackWrapper newItem;

		private static InvTransaction readFromStream(ByteBuf from, String locale, ProtocolVersion version) {
			InvTransaction transaction = new InvTransaction();
			transaction.sourceId = VarNumberSerializer.readVarInt(from);
			switch (transaction.sourceId) {
				case SOURCE_CONTAINER: {
					transaction.inventoryId = VarNumberSerializer.readSVarInt(from);
					break;
				}
				case SOURCE_WORLD_INTERACTION: {
					transaction.inventoryId = PESource.POCKET_FAUX_DROP;
					transaction.action = VarNumberSerializer.readVarInt(from);
					break;
				}
				case SOURCE_TODO: {
					transaction.inventoryId = VarNumberSerializer.readSVarInt(from);
					transaction.action = 0;
					break;
				}
				case SOURCE_GLOBAL:
				case SOURCE_CREATIVE:
				default: {
					break;
				}
			}
			transaction.slot = VarNumberSerializer.readVarInt(from);
			transaction.oldItem = ItemStackSerializer.readItemStack(from, version, locale, true);
			transaction.newItem = ItemStackSerializer.readItemStack(from, version, locale, true);
			System.out.println("Inv transaction read:"
					+ " sId: " + transaction.sourceId 
					+ " wId: " + transaction.inventoryId 
					+ " action: " + transaction.action 
					+ " slot: " + transaction.slot 
					+ " oldItem: " + transaction.oldItem.toString()  + ((!transaction.oldItem.isNull()) ? transaction.oldItem.getTag() : "") 
					+ " newItem: " + transaction.newItem.toString() + (!transaction.newItem.isNull() ? transaction.newItem.getTag() : ""));
			return transaction;
		}

		public int getSourceId() {
			return sourceId;
		}

		public int getInventoryId() {
			return inventoryId;
		}

		public int getAction() {
			return action;
		}

		public void setSlot(int slot) {
			this.slot = slot;
		}

		public int getSlot() {
			return slot;
		}

		public boolean isValid() {
			return slot != MISSING_REMAP;
		}

		public boolean isCursor() {
			return slot == CURSOR;
		}

		public ItemStackWrapper getOldItem() {
			return oldItem;
		}

		public ItemStackWrapper getNewItem() {
			return newItem;
		}

		@Override
		public String toString() {
			return Utils.toStringAllFields(this);
		}

	}
	
	protected static void processAnvilName(InvTransaction transaction, RecyclableArrayList<ServerBoundPacketData> packets) {
		//Anvil naming is only done and known based on the clicked item.
		if (transaction.getSlot() == 2 && !transaction.getOldItem().isNull()) {
			NBTTagCompoundWrapper tag = transaction.getOldItem().getTag();
			if (tag.hasKeyOfType("display", NBTTagType.COMPOUND)) {
				NBTTagCompoundWrapper display = tag.getCompound("display");
				if (display.hasKeyOfType("Name", NBTTagType.STRING)) {
					ByteBuf payload = Unpooled.buffer();
					StringSerializer.writeString(payload, ProtocolVersionsHelper.LATEST_PC, display.getString("Name"));
					packets.add(MiddleCustomPayload.create("MC|ItemName", MiscSerializer.readAllBytes(payload)));
				}
			}
		}
	}

}
