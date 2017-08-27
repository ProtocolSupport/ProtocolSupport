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

//DKTAPPS THANK YOU FOR HELPING ME! - This would have been insufferable without him.
//The Pe GodPacket. I'm going to make him an offer he can't refuse...
public class InventoryTransaction extends ServerBoundMiddlePacket {

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

	@Override
	public void readFromClientData(ByteBuf clientdata) {
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
			//For creative?
			case ACTION_RELEASE_ITEM: {
				packets.add(MiddleHeldSlot.create(slot));
				System.out.println("Realease item. Action " + subTypeId + " ");
				switch(subTypeId) {
					default: {
						break;
					}
				}
				break;
			}
			case ACTION_NORMAL: { //Normal inventory transaction.
				int button = 0, mode = 0;
				
				if(transactions.length > 2) { //Drop action
					packets.add(MiddleInventoryClick.create(cache.getLocale(), cache.getOpenedWindowId(), PESource.POCKET_FAUX_DROP, 0, cache.getActionNumber(), 5, ServerPlatform.get().getWrapperFactory().createItemStack(0)));
				}
				for(int i = 0; i < transactions.length; i++) {
					ServerBoundPacketData packet = transactions[i].toClick(cache, button, mode);
					if(packet != null) {
						packets.add(packet);
					}
				}
				if(transactions.length > 2) { //Drop action
					packets.add(MiddleInventoryClick.create(cache.getLocale(), cache.getOpenedWindowId(), PESource.POCKET_FAUX_DROP, 2, cache.getActionNumber(), 5, ServerPlatform.get().getWrapperFactory().createItemStack(0)));
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

	@SuppressWarnings("unused") //TODO: IMPLEMENT!
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
			System.out.println("Inv transaction read: sId: " + transaction.sourceId + " wId: " + transaction.inventoryId + " action: " + transaction.action + " slot: " + transaction.slot);
			return transaction;
		}

		public ServerBoundPacketData toClick(NetworkDataCache cache, int button, int mode) {
			int sSlot = -1; //Slot to send.
			
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
				//Maybe more integred stuff is needed for complexer things like horses or something. So I'll leave the switch for now.
				default: {
					//return MiddleInventoryClick.create(cache.getLocale(), cache.getOpenedWindowId(), invSlotToContainerSlot(inventoryId, cache.getOpenedWindowSlots(), slot), 0, cache.getActionNumber(), 0, cache.getCursorItem());
					sSlot = invSlotToContainerSlot(inventoryId, cache.getOpenedWindowSlots(), slot);
					break;
				}
			}
			if (sSlot != -1) {
				System.out.println(cache.getLocale() + " wId: " + cache.getOpenedWindowId() + " Slot: " + sSlot + " Button: " + button + " ActionNumber.. " + /*cache.getActionNumber() +*/ " Action: " + mode + " Cursor: " + oldItem);
				return MiddleInventoryClick.create(cache.getLocale(), cache.getOpenedWindowId(), sSlot, button, cache.getActionNumber(), mode, oldItem /*cache.getCursorItem()*/);
			}
			return null;
		}
		
		private int invSlotToContainerSlot(int inventoryId, int start, int slot) {
			if(inventoryId == PESource.POCKET_INVENTORY) {
				if (slot < 9) {
					return slot + (27 + start);
				} else {
					return slot - (9 - start);
				}
			}
			return slot;
		}
		
		@Override
		public String toString() {
			return Utils.toStringAllFields(this);
		}

	}

}
