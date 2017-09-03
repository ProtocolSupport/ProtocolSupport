package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import org.bukkit.util.Vector;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleBlockDig;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleBlockPlace;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleHeldSlot;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleInventoryClick;
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
	
	//Transactions
	protected InfTransaction[] transactions;
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

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		System.out.println("NEEWWW GODPACKET!");
		actionId = VarNumberSerializer.readVarInt(clientdata);

		transactions = new InfTransaction[VarNumberSerializer.readVarInt(clientdata)];
		for(int i = 0; i < transactions.length; i++) {
			transactions[i] = InfTransaction.readFromStream(clientdata, cache.getLocale(), connection.getVersion());
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
				ClickType clickType = calculateClickType();
				
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
				
				break;
			}
			case ACTION_MISMATCH:
			default: {
				break;
			}
		}
		return packets;
	}

	private static class InfTransaction {

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

		public RecyclableArrayList<ServerBoundPacketData> toClick(NetworkDataCache cache, ClickType transType) {
			int sSlot = -1; //Slot to send.
			
			//Repetitive.
			if(newItem.equals(oldItem)) {
				return null;
			}
			
			switch(cache.getOpenedWindow()) {
				case PLAYER: {
					switch(inventoryId) {
						case PESource.POCKET_CRAFTING_RESULT: {
							sSlot = 0;
							break;
						}
						case PESource.POCKET_CRAFTING_GRID: {
							sSlot = slot + 1;
							break;
						}
						case PESource.POCKET_ARMOR_EQUIPMENT: {
							sSlot = slot + 5;
							break;
						}
						case PESource.POCKET_INVENTORY: {
								if (slot < 9) {
									sSlot = slot + 36;
								} else {
									sSlot = slot;
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
					sSlot = invSlotToContainerSlot(inventoryId, 10, slot);
					break;
				}
				default: {
					sSlot = invSlotToContainerSlot(inventoryId, cache.getOpenedWindowSlots(), slot);
					break;
				}
			}
			
			//Special cases
			switch(inventoryId) {
				case PESource.POCKET_CLICKED_SLOT: {
					return null;
				}
				case PESource.POCKET_FAUX_DROP: {
					sSlot = inventoryId;
					break;
				}
			}
			
			if (sSlot != -1) {
				return transType.process(cache, sSlot, oldItem);
			}
			return null;
		}
		
		@Override
		public String toString() {
			return Utils.toStringAllFields(this);
		}

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
	
	private ClickType calculateClickType() {
		System.out.println("Calculating clicktype...");
		if (transactions.length > 0) {
			System.out.println("Transactions > 0");
			if(transactions[0].inventoryId == PESource.POCKET_CLICKED_SLOT) {
				System.out.println("Click based action");
				if(transactions.length > 2) {
					System.out.println("Drag based action");
					if (transactions[transactions.length - 1].newItem.getAmount() == 1) {
						System.out.println("Single item. (DropOff)");
						return ClickType.DRAGRIGHT;
					} else if (transactions[transactions.length - 1].newItem.getAmount() == 0) { 
						System.out.println("Double click.");
						return ClickType.DUBBLECLICK;
					} else {
						System.out.println("Multiple items (SplitStack).");
						return ClickType.DRAGLEFT;
					}
				} else {
					System.out.println("Single click action");
					if (transactions[transactions.length - 1].newItem.getAmount() == 1) {
						System.out.println("Single item. (DropOff)");
						return ClickType.RIGHTCLICK;
					} else {
						System.out.println("Multiple item. (Drop Stack)");
						return ClickType.LEFTCLICK;
					}
				}
			} else {
				System.out.println("Tap based action");
				if (transactions[transactions.length - 1].newItem.getAmount() == 1) {
					System.out.println("Single item transfer.");
					return ClickType.TRANSFERITEM;
				} else {
					System.out.println("Stack transfer.");
					return ClickType.TRANSFERSTACK;
				}
			}
		}
		
		return ClickType.UNKNOWN;
	}
	
	public enum ClickType {
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
		
	}
	
	private static ServerBoundPacketData pcClick(NetworkDataCache cache, int mode, int button, int slot, ItemStackWrapper lastItem) {
		return MiddleInventoryClick.create(cache.getLocale(), cache.getOpenedWindowId(), slot, button, cache.getActionNumber(), mode, lastItem);
	}

}
