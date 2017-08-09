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
import protocolsupport.protocol.typeremapper.pe.PEDataValues;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.utils.Utils;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;

//DKTAPPS THANK YOU FOR HELPING ME!
//The Pe GodPacket... I'm going to make him an offer he can't refuse...
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
	
	//Slot thingy numbers.
	public static final int POCKET_INVENTORY = 0;
	public static final int POCKET_OFFHAND = 119;
	public static final int POCKET_ARMOR_EQUIPMENT = 120;
	public static final int POCKET_CREATIVE_INVENTORY = 121;
	public static final int POCKET_CLICKED_SLOT = 124;
	
	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		RecyclableArrayList<ServerBoundPacketData> packets = RecyclableArrayList.create();
		for(int i = 0; i < transactions.length; i++) {
			ServerBoundPacketData packet = transactions[i].toClick(cache);
			if(packet != null) {
				packets.add(packet);
			}
		}
		switch(actionId) {
			case ACTION_USE_ITEM: {
				packets.add(MiddleHeldSlot.create(slot));
				switch(subTypeId) {
					case USE_CLICK_AIR:
						face = -1;
					case USE_CLICK_BLOCK: {
						packets.add(MiddleBlockPlace.create(position, face, 0, cX, cY, cZ));
						break;
					}
					case USE_DIG_BLOCK: { //instabreak
						if(cache.getGameMode() == 1) {
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
			case ACTION_NORMAL:
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
					transaction.inventoryId = -1;
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
			return transaction;
		}
		
		public ServerBoundPacketData toClick(NetworkDataCache cache) {
			if(inventoryId == POCKET_CLICKED_SLOT) {
				cache.setCursorItem(newItem);
			} else if (inventoryId == POCKET_INVENTORY) {
				System.out.println(cache.getLocale() + " wId: " + inventoryId + " Slot: " + PEDataValues.remapServerboundSlot(inventoryId, slot) + " Button: " + 0 + " ActionNumber.. " + /*cache.getActionNumber() +*/ " Action: " + 0 + " Cursor: " + cache.getCursorItem());
				return MiddleInventoryClick.create(cache.getLocale(), inventoryId, PEDataValues.remapServerboundSlot(inventoryId, slot), 0, cache.getActionNumber(), 0, cache.getCursorItem());
			}
			return null;
		}
		
		@Override
		public String toString() {
			return Utils.toStringAllFields(this);
		}
		
	}

}
