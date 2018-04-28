package protocolsupport.protocol.typeremapper.pe.inventory.fakes;

import java.util.EnumMap;

import protocolsupport.api.Connection;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.utils.Any;
import protocolsupport.listeners.InternalPluginMessageRequest;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.BlockChangeSingle;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.BlockTileUpdate;
import protocolsupport.protocol.storage.netcache.NetworkDataCache;
import protocolsupport.protocol.storage.netcache.PEInventoryCache;
import protocolsupport.protocol.storage.netcache.WindowCache;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.protocol.utils.types.TileEntityType;
import protocolsupport.protocol.utils.types.WindowType;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

public class PEFakeContainer {

	//Table with PE ids and access to tile id, to place the inventory blocks.
	private static void regInvBlockType(WindowType type, int containerId, TileEntityType tileType) {
		invBlockType.put(type, new Any<Integer, TileEntityType>(containerId << 4, tileType));
	}
	private static EnumMap<WindowType, Any<Integer, TileEntityType>> invBlockType = new EnumMap<>(WindowType.class);
	static {
		regInvBlockType(WindowType.CHEST, 			54, TileEntityType.CHEST);
		regInvBlockType(WindowType.CRAFTING_TABLE, 	58, TileEntityType.UNKNOWN);
		regInvBlockType(WindowType.FURNACE, 		61, TileEntityType.FURNACE);
		regInvBlockType(WindowType.DISPENSER, 		23, TileEntityType.DISPENSER);
		regInvBlockType(WindowType.ENCHANT, 	   154, TileEntityType.HOPPER); //Fake with hopper
		regInvBlockType(WindowType.BREWING, 	   117, TileEntityType.BREWING_STAND);
		regInvBlockType(WindowType.BEACON, 		   138, TileEntityType.BEACON);
		regInvBlockType(WindowType.ANVIL, 		   145, TileEntityType.UNKNOWN);
		regInvBlockType(WindowType.HOPPER, 		   154, TileEntityType.HOPPER);
		regInvBlockType(WindowType.DROPPER,		   158, TileEntityType.DROPPER);
		regInvBlockType(WindowType.SHULKER, 		54, TileEntityType.CHEST); //Fake with chest
	}
	private static Any<Integer, TileEntityType> getContainerData(WindowType type) {
		return invBlockType.get(type);
	}

	//Create matching block and tile change packets to fake inventories and store positions of to reset them later. 
	public static Position prepareContainer(BaseComponent title, Connection connection, NetworkDataCache cache, RecyclableArrayList<ClientBoundPacketData> packets) {
		ProtocolVersion version = connection.getVersion();
		WindowCache winCache = cache.getWindowCache();
		PEInventoryCache invCache = cache.getPEInventoryCache();
		Any<Integer, TileEntityType> typeData = getContainerData(winCache.getOpenedWindow());
		Position position = new Position(0,0,0);
		if (typeData != null) {
			//Get position under client's feet.
			position.setX((int) cache.getMovementCache().getPEClientX() - 2);
			position.setY((int) cache.getMovementCache().getPEClientY() - 2);
			position.setZ((int) cache.getMovementCache().getPEClientZ());
			//If client is falling or extremely low, get above head.
			if (cache.getAttributesCache().isPEFlying() || cache.getMovementCache().getPEClientY() < 4) {
				position.modifyY(6);
			}
			invCache.getFakeContainers().addFirst(position);
			//Create fake inventory block.
			packets.add(BlockChangeSingle.create(version, position, typeData.getObj1()));
			//Set tile data for fake block.
			NBTTagCompoundWrapper tag = ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound();
			tag.setString("CustomName", title.toLegacyText(cache.getAttributesCache().getLocale()));
			if (typeData.getObj2() != TileEntityType.UNKNOWN) {
				tag.setString("id", typeData.getObj2().getRegistryId());
			}
			//Large inventories require doublechest that requires two blocks and nbt.
			if (shouldDoDoubleChest(cache)) {
				Position auxPos = position.clone();
				auxPos.modifyX(1); //Get adjacend block.
				invCache.getFakeContainers().addLast(auxPos);
				packets.add(BlockChangeSingle.create(version, auxPos, typeData.getObj1()));
				tag.setInt("pairx", auxPos.getX());
				tag.setInt("pairz", auxPos.getZ());
				tag.setByte("pairlead", 1);
				packets.add(BlockTileUpdate.create(version, position, tag));
				NBTTagCompoundWrapper auxTag = ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound();;
				auxTag.setString("CustomName", title.toLegacyText(cache.getAttributesCache().getLocale()));
				auxTag.setString("id", typeData.getObj2().getRegistryId());
				auxTag.setInt("pairx", position.getX());
				auxTag.setInt("pairz", position.getZ());
				auxTag.setByte("pairlead", 0);
				packets.add(BlockTileUpdate.create(version, auxPos, auxTag));
			} else {
				packets.add(BlockTileUpdate.create(version, position, tag));
			}
		}
		return position;
	}

	//Check if player has / needs "fake" double chest.
	public static boolean shouldDoDoubleChest(NetworkDataCache cache) {
		return (cache.getWindowCache().getOpenedWindow() == WindowType.CHEST && cache.getWindowCache().getOpenedWindowSlots() > 27);
	}

	//Request reset for all fake container blocks.
	public static void destroyContainers(Connection connection, NetworkDataCache cache) {
		cache.getPEInventoryCache().getFakeContainers().cycleDown(position -> {
			InternalPluginMessageRequest.receivePluginMessageRequest(connection, new InternalPluginMessageRequest.BlockUpdateRequest(position));
			return true;
		});
		if (cache.getPEInventoryCache().getFakeVillager().isSpawned()) {
			cache.getPEInventoryCache().getFakeVillager().despawnVillager(connection);
		}
	}

}
