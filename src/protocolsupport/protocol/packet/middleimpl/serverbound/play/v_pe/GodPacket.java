package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.util.Vector;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleBlockDig;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleBlockPlace;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleHeldSlot;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleInventoryClick;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleInventoryTransaction;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleUseEntity;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.NetworkDataCache;
import protocolsupport.protocol.typeremapper.pe.PEInventory.PESource;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.utils.Utils;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;

/**
 * Lo and behold. The PE GodPacket. 
 * DKTAPPS THANK YOU FOR HELPING ME! - This would be insufferable without him.
 * 
 * This packet handles among other things
 *  - Using items (Right clicking)
 *  - Interacting (left and right clicking mob)
 *  - Releasing items (shoot bow, eat food)
 *  - Getting creative items
 *  - MANAGING INVENTORY
 *  
 *  Apart from managing inventories it is the usual packet deal,
 *  except that we have an extra layer of encapsulation.
 *  
 *  Managing inventories works very different in PE, so for now excpect some hacky code.
 */
public class GodPacket extends ServerBoundMiddlePacket {
	
	//Complex Actions
	protected int actionId;
	protected int subTypeId = -1;
	protected ItemStackWrapper itemstack;
	protected int slot;
	protected Position position;
	protected float fromX, fromY, fromZ;
	protected float cX, cY, cZ;
	protected int face;
	protected int targetId;
	//Misc
	private static ItemStackWrapper AIR = ServerPlatform.get().getWrapperFactory().createItemStack(0);

	public static void bug(String bugger) {
		System.out.println(bugger);
	}
	
	@Override
	public void readFromClientData(ByteBuf clientdata) {
		System.out.println("NEEWWW GODPACKET! Hooraayy it's a god packet. Don't we all reeeaaally love godpackets? I do. IDDOODD I REALLY DO LOVE THEM. THTHISS IS THE BEST DAY IN MY LIFE.");
		actionId = VarNumberSerializer.readVarInt(clientdata);

		int transactionLength = VarNumberSerializer.readVarInt(clientdata);
		for(int i = 0; i < transactionLength; i++) {
			cache.getInfTransactions().cacheTransaction(cache, InfTransaction.readFromStream(clientdata, cache.getLocale(), connection.getVersion()));
			bug("cached.. Deficit size: " + cache.getInfTransactions().deficit.size() + " surplus size: " + cache.getInfTransactions().surplus.size());
		}

		switch(actionId) {
			case ACTION_USE_ITEM: {
				subTypeId = VarNumberSerializer.readVarInt(clientdata);
				position = PositionSerializer.readPEPosition(clientdata);
				face = VarNumberSerializer.readSVarInt(clientdata);
				slot = VarNumberSerializer.readSVarInt(clientdata);
				itemstack = ItemStackSerializer.readItemStack(clientdata, connection.getVersion(), cache.getLocale(), true);
				fromX = MiscSerializer.readLFloat(clientdata); fromY = MiscSerializer.readLFloat(clientdata); fromZ = MiscSerializer.readLFloat(clientdata);
				cX = MiscSerializer.readLFloat(clientdata); cY = MiscSerializer.readLFloat(clientdata); cZ = MiscSerializer.readLFloat(clientdata);
				break;
			}
			case ACTION_INTERACT: {
				targetId = (int) VarNumberSerializer.readVarLong(clientdata);
				subTypeId = VarNumberSerializer.readVarInt(clientdata);
				slot = VarNumberSerializer.readSVarInt(clientdata);
				itemstack = ItemStackSerializer.readItemStack(clientdata, connection.getVersion(), cache.getLocale(), true);
				fromX = MiscSerializer.readLFloat(clientdata); fromY = MiscSerializer.readLFloat(clientdata); fromZ = MiscSerializer.readLFloat(clientdata);
				cX = MiscSerializer.readLFloat(clientdata); cY = MiscSerializer.readLFloat(clientdata); cZ = MiscSerializer.readLFloat(clientdata);
				break;
			}
			case ACTION_RELEASE_ITEM: {
				subTypeId = VarNumberSerializer.readVarInt(clientdata);
				slot = VarNumberSerializer.readSVarInt(clientdata);
				itemstack = ItemStackSerializer.readItemStack(clientdata, connection.getVersion(), cache.getLocale(), true);
				fromX = MiscSerializer.readLFloat(clientdata); fromY = MiscSerializer.readLFloat(clientdata); fromZ = MiscSerializer.readLFloat(clientdata);
				break;
			}
			case ACTION_NORMAL:
			case ACTION_MISMATCH:
			default: {
				break;
			}
		}
		//BLEEHH!
		clientdata.readBytes(clientdata.readableBytes());
	}

	//Sources
	public static final int SOURCE_CONTAINER = 0;
	public static final int SOURCE_GLOBAL = 1;
	public static final int SOURCE_WORLD_INTERACTION = 2;
	public static final int SOURCE_CREATIVE = 3;
	public static final int SOURCE_TODO = 99999;
	//Actions
	public static final int ACTION_NORMAL = 0;
	public static final int ACTION_MISMATCH = 1;
	public static final int ACTION_USE_ITEM = 2;
	public static final int ACTION_INTERACT = 3;
	public static final int ACTION_RELEASE_ITEM = 4;
	//Action - Item Release
	public static final int RELEASE_RELEASE = 0;
	public static final int RELEASE_DESTROY = 1;
	//Action - Item Use
	public static final int USE_CLICK_BLOCK = 0;
	public static final int USE_CLICK_AIR = 1;
	public static final int USE_DIG_BLOCK = 2;
	//Action - Interact
	public static final int INTERACT_INTERACT = 0;
	public static final int INTERACT_ATTACK = 1;
	public static final int INTERACT_AT = 2;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		RecyclableArrayList<ServerBoundPacketData> packets = RecyclableArrayList.create();
		switch(actionId) {
			case ACTION_USE_ITEM: {
				packets.add(MiddleHeldSlot.create(slot));
				switch(subTypeId) {
					case USE_CLICK_AIR:
						face = -1;
					case USE_CLICK_BLOCK: {
						cache.setClickedPosition(position);
						packets.add(MiddleBlockPlace.create(position, face, 0, cX, cY, cZ));
						break;
					}
					case USE_DIG_BLOCK: {
						if(cache.getGameMode() == 1) { //instabreak
							packets.add(MiddleBlockDig.create(MiddleBlockDig.Action.START_DIG, position, 0));
							packets.add(MiddleBlockDig.create(MiddleBlockDig.Action.FINISH_DIG, position, 0));
						}
						break;
					}
				}
				break;
			}
			case ACTION_INTERACT: {
				packets.add(MiddleHeldSlot.create(slot));
				switch(subTypeId) {
					case INTERACT_INTERACT: {
						packets.add(MiddleUseEntity.create(targetId, MiddleUseEntity.Action.INTERACT, null, 0));
						break;
					}
					case INTERACT_ATTACK: {
						packets.add(MiddleUseEntity.create(targetId, MiddleUseEntity.Action.ATTACK, null, 0));
						break;
					}
					case INTERACT_AT: {
						packets.add(MiddleUseEntity.create(targetId, MiddleUseEntity.Action.INTERACT_AT, new Vector(cX, cY, cZ), 0));
						break;
					}
				}
				break;
			}
			case ACTION_RELEASE_ITEM: {
				packets.add(MiddleHeldSlot.create(slot));
				//Special place packet.
				packets.add(MiddleBlockPlace.create(new Position(-1, 255, -1), -1, 0, cX, cY, cZ));
				break;
			}
			case ACTION_NORMAL: { //Normal inventory transaction.
				
				packets.addAll(cache.getInfTransactions().process(cache));
				
				/*ClickType clickType = calculateClickType();
				
				if(clickType.doPrefix()) {
					packets.add(clickType.createPrefix(cache));
				}
				for(int i = 0; i < transactions.length; i++) {
					RecyclableArrayList<ServerBoundPacketData> clickPackets = transactions[i].toClick(cache, clickType);
					if(clickPackets != null) {
						packets.addAll(clickPackets);
					}
				}
				if(clickType.doSuffix()) {
					packets.add(clickType.createSuffix(cache));
				}
				*/
				
				
				break;
			}
			case ACTION_MISMATCH:
			default: {
				break;
			}
		}
		return packets;
	}

	public static class InfTransaction {

		private int sourceId;
		private int inventoryId;
		private int action;
		private int slot;
		private ItemStackWrapper oldItem;
		private ItemStackWrapper newItem;

		private static InfTransaction readFromStream(ByteBuf from, String locale, ProtocolVersion version) {
			InfTransaction transaction = new InfTransaction();
			transaction.sourceId = VarNumberSerializer.readVarInt(from);
			switch(transaction.sourceId) {
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
			System.out.println("Inv transaction read: sId: " + transaction.sourceId + " wId: " + transaction.inventoryId + " action: " + transaction.action + " slot: " + transaction.slot 
					+ " oldItem: " + transaction.oldItem.toString() + " newItem: " + transaction.newItem.toString());
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


		public int getSlot() {
			return slot;
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
	
	public static class InfTransactions {
		Map<String, List<Integer>> surplus = new HashMap<>();
		Map<String, List<Integer>> deficit = new HashMap<>();
		
		public void clear() {
			bug("Clearing cache with surplus: " + surplus.toString() + " and deficit: " + deficit.toString());
			surplus.clear();
			deficit.clear();
		}
		
		public void cacheTransaction(NetworkDataCache cache, InfTransaction transaction) {
			if (!transaction.getOldItem().equals(transaction.getNewItem())) {
				String oldId = getItemIdentity(transaction.getOldItem());
				String newId = getItemIdentity(transaction.getNewItem());
				if (oldId.equals(newId)) {
					int amount = transaction.getOldItem().getAmount() - transaction.getNewItem().getAmount();
					if (amount > 0) {
						put(surplus, oldId, transformSlot(cache, transaction.getInventoryId(), transaction.getSlot()), amount);
						bug("adding surplus C: " + oldId + " : " + transformSlot(cache, transaction.getInventoryId(), transaction.getSlot()) + " : " + amount);
					} else {
						put(deficit, oldId, transformSlot(cache, transaction.getInventoryId(), transaction.getSlot()), amount * -1);
						bug("adding deficit C: " + oldId + " : " + transformSlot(cache, transaction.getInventoryId(), transaction.getSlot()) + " : " + amount * -1);
					}
				} else {
					if (transaction.getOldItem().getType() != Material.AIR) {
						put(surplus, oldId, transformSlot(cache, transaction.getInventoryId(), transaction.getSlot()), transaction.getOldItem().getAmount());
						bug("adding surplus N: " + oldId + " : " + transformSlot(cache, transaction.getInventoryId(), transaction.getSlot()) + " : " + transaction.getOldItem().getAmount());
					} if (transaction.getNewItem().getType() != Material.AIR) {
						put(deficit, newId, transformSlot(cache, transaction.getInventoryId(), transaction.getSlot()), transaction.getNewItem().getAmount());
						bug("adding deficit N: " + newId + " : " + transformSlot(cache, transaction.getInventoryId(), transaction.getSlot()) + " : " + transaction.getNewItem().getAmount());
					}
				}
			}
		}
		
		private void put(Map<String, List<Integer>> map, String id, int slot, int amount) {
			bug("slot: " + slot + " amount: " + amount);
			List<Integer> keys = map.get(id);
			if(keys == null) {
				keys = new ArrayList<Integer>();
				map.put(id, keys);
			}
			bug("Adding keyValue: " + Integer.toBinaryString(slot << 8 | amount));
			keys.add(slot << 8 | amount);
			bug("keysize: " + keys.size());
		}
		
		public RecyclableArrayList<ServerBoundPacketData> process(NetworkDataCache cache) {
			RecyclableArrayList<ServerBoundPacketData> packets = RecyclableArrayList.create();
			bug("processing..");
			Iterator<Entry<String, List<Integer>>> deficitorator = deficit.entrySet().iterator();
			while(deficitorator.hasNext()) {
				Entry<String, List<Integer>> deficitEntry = deficitorator.next();
				List<Integer> surplusSlotValues = surplus.get(deficitEntry.getKey());
				bug("Deficits of " + deficitEntry.getKey() + "...");
				if(surplusSlotValues != null) {
					bug("Found surplus! (Where's the money lebowsky?!)");
					Iterator<Integer> deficitSlotValueIterator = deficitEntry.getValue().iterator();
					while(deficitSlotValueIterator.hasNext()) {
						Integer deficitSlotValue = deficitSlotValueIterator.next();
						bug("Local debt: " + Integer.toBinaryString(deficitSlotValue));
						int	deficitSlot = ((deficitSlotValue >> 8) & 0xFF), deficitAmount = (deficitSlotValue & 0xFF);
						bug("Debt slot: " + deficitSlot + " Debt amount: " + deficitAmount);
						Iterator<Integer> surplusSlotValueIterator = surplusSlotValues.iterator();
						while(surplusSlotValueIterator.hasNext()) {
							bug("Rolling cashier..");
							Integer surplusSlotValue = surplusSlotValueIterator.next();
							bug("Surplus value: " + Integer.toBinaryString(surplusSlotValue));
							int	surplusSlot = ((surplusSlotValue >> 8) & 0xFF), surplusAmount = (surplusSlotValue & 0xFF);
							bug("Surplus slot: " + surplusSlot + " surplus amount: " + surplusAmount);
							bug("Left clicking (stealing) surplus...");
							packets.addAll(Click.LEFT.create(cache, surplusSlot));
							bug("Getting all we can get our hands on...");
							for(int i = 0; i < (deficitAmount < surplusAmount ? deficitAmount : surplusAmount); i++) {
								bug("right..");
								packets.addAll(Click.RIGHT.create(cache, deficitSlot));
								deficitSlotValue--; surplusSlotValue--;
							}
							packets.addAll(Click.LEFT.create(cache, surplusSlot));
							if((surplusSlotValue & 0xFF) == 0) {
								bug("The suitcase is empty!");
								surplusSlotValueIterator.remove();
							}
							if((deficitSlotValue & 0xFF) == 0) {
								bug("One debt less!");
								deficitSlotValueIterator.remove();
							}
						}
					}
					if(deficitEntry.getValue().isEmpty()) {
						bug("All debt is payed! I AM REALLY FREE!!!");
						deficitorator.remove();
					}
				}
			}
			return packets;
		}
		
		private String getItemIdentity(ItemStackWrapper item) {
			return item.getTypeId() + "-" + item.getData() + "-" + item.getTag().toString();
		}
		
		/*protected class ClickBait {
			
			private int fromSlot, toSlot, amount;
			
			public ClickBait(int fromSlot, int toSlot, int amount) {
				this.fromSlot = fromSlot; 
				this.toSlot = toSlot; 
				this.amount = amount;
			}

			public int getFromSlot() {
				return fromSlot;
			}

			public int getToSlot() {
				return toSlot;
			}

			public int getAmount() {
				return amount;
			}
			
		}*/
		
	}
	
	private static int transformSlot(NetworkDataCache cache, int peInventoryId, int peSlot) {
		int sSlot = -1; //Slot to send.
		
		switch(cache.getOpenedWindow()) {
			case PLAYER: {
				switch(peInventoryId) {
					case PESource.POCKET_CRAFTING_RESULT: {
						sSlot = 0;
						break;
					}
					case PESource.POCKET_CRAFTING_GRID: {
						sSlot = peSlot + 1;
						break;
					}
					case PESource.POCKET_ARMOR_EQUIPMENT: {
						sSlot = peSlot + 5;
						break;
					}
					case PESource.POCKET_INVENTORY: {
							if (peSlot < 9) {
								sSlot = peSlot + 36;
							} else {
								sSlot = peSlot;
							}
						break;
					}
					case PESource.POCKET_OFFHAND: {
						sSlot = 45;
						break;
					}
					default: {
						break;
					}
				}
				break;
			}
			case CRAFTING_TABLE: {
				sSlot = invSlotToContainerSlot(peInventoryId, 10, peSlot);
				break;
			}
			default: {
				sSlot = invSlotToContainerSlot(peInventoryId, cache.getOpenedWindowSlots(), peSlot);
				break;
			}
		}
		
		return sSlot;
	}
	
	private static int invSlotToContainerSlot(int inventoryId, int start, int slot) {
		if(inventoryId == PESource.POCKET_INVENTORY) {
			if (slot < 9) {
				return slot + (27 + start);
			} else {
				return slot - (9 - start);
			}
		}
		return slot;
	}
	
	protected enum Click {
		LEFT	(0, 0),
		RIGHT	(0, 1);
		
		private final int mode;
		private final int button;
		
		Click(int mode, int button) {
			this.mode = mode;
			this.button = button;
		}
		
		public RecyclableArrayList<ServerBoundPacketData> create(NetworkDataCache cache, int slot) {
			RecyclableArrayList<ServerBoundPacketData> clickPackets = RecyclableArrayList.create();
			int actionNumber = cache.getActionNumber();
			clickPackets.add(MiddleInventoryClick.create(cache.getLocale(), cache.getOpenedWindowId(), slot, button, actionNumber, mode, AIR));
			clickPackets.add(MiddleInventoryTransaction.create(cache.getOpenedWindowId(), actionNumber, false)); //Instant apology :D
			return clickPackets;
		}
		
	}
	
	/*public enum ClickType {
		LEFTCLICK		( 0,  0, -1, -1, false), 
		RIGHTCLICK		( 0,  1, -1, -1, false), 
		DUBBLECLICK		( 6,  0, -1, -1, false),
		DRAGLEFT		( 5,  1,  0,  2, false), 
		DRAGRIGHT		( 5,  5,  4,  6, false), 
		TRANSFERSTACK	( 0,  0, -1, -1, true ),
		TRANSFERITEM	( 0,  1, -1, -1, true ),
		UNKNOWN			(-1, -1, -1, -1, false);
		
		private final int mode;
		private final int button;
		private final int prefixButton;
		private final int suffixButton;
		private final boolean faux;
		
		private boolean doneFirst = false;
		private int fauxFirstSlot;
		
		ClickType(int mode, int button, int prefixButton, int suffixButton, boolean faux) {
			this.mode = mode;
			this.button = button;
			this.prefixButton = prefixButton;
			this.suffixButton = suffixButton;
			this.faux = faux;
		}
		
		public boolean doPrefix() {
			return prefixButton != -1 ? true : false;
		}
		
		public boolean doSuffix() {
			return suffixButton != -1 ? true : false;
		}
		
		public ServerBoundPacketData createPrefix(NetworkDataCache cache) {
			return pcClick(cache, mode, prefixButton, -999, AIR);
		}
		
		public ServerBoundPacketData createSuffix(NetworkDataCache cache) {
			return pcClick(cache, mode, suffixButton, -999, AIR);
		}
		
		RecyclableArrayList<ServerBoundPacketData> process(NetworkDataCache cache, int slot, ItemStackWrapper lastItem) {
			RecyclableArrayList<ServerBoundPacketData> packets = RecyclableArrayList.create();
			System.out.print(mode != -1); System.out.print(!faux || doneFirst); System.out.print(mode != 6 || !doneFirst);
			if(mode != -1 && (!faux || doneFirst) && (mode != 6 || !doneFirst)) {
				packets.add(pcClick(cache, mode, button, slot, lastItem)); doneFirst = true;
			}
			if(faux) {
				if (!doneFirst) { 
					packets.add(pcClick(cache, 0, 0, slot, lastItem)); 
					fauxFirstSlot = slot;
				} else { 
					packets.add(pcClick(cache, 0, 0, fauxFirstSlot, AIR));
				}
			}
			return packets;
		}
		
	}*/

}
