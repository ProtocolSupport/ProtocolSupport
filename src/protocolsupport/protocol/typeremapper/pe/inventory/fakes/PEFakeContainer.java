package protocolsupport.protocol.typeremapper.pe.inventory.fakes;

import java.util.EnumMap;

import org.bukkit.Material;

import protocolsupport.api.Connection;
import protocolsupport.api.MaterialAPI;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.utils.Any;
import protocolsupport.listeners.InternalPluginMessageRequest;
import protocolsupport.listeners.internal.BlockUpdateRequest;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.BlockChangeSingle;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.BlockTileUpdate;
import protocolsupport.protocol.storage.netcache.NetworkDataCache;
import protocolsupport.protocol.storage.netcache.PEInventoryCache;
import protocolsupport.protocol.storage.netcache.WindowCache;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.TileEntity;
import protocolsupport.protocol.types.TileEntityType;
import protocolsupport.protocol.types.WindowType;
import protocolsupport.protocol.types.nbt.NBTByte;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTInt;
import protocolsupport.protocol.types.nbt.NBTString;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupportbuildprocessor.Preload;

@Preload
public class PEFakeContainer {

	public static final int SMALLCONTAINERSLOTS = 27;

	//Table with PE ids and access to tile id, to place the inventory blocks.
	private static void regInvBlockType(WindowType type, Material container, TileEntityType tileType) {
		invBlockType.put(type, new Any<Material, TileEntityType>(container, tileType));
	}

	private static EnumMap<WindowType, Any<Material, TileEntityType>> invBlockType = new EnumMap<>(WindowType.class);

	static {
		regInvBlockType(WindowType.GENERIC_9X1, 	Material.CHEST,			 	TileEntityType.CHEST);
		regInvBlockType(WindowType.GENERIC_9X2, 	Material.CHEST,			 	TileEntityType.CHEST);
		regInvBlockType(WindowType.GENERIC_9X3, 	Material.CHEST,			 	TileEntityType.CHEST);
		regInvBlockType(WindowType.GENERIC_9X4, 	Material.CHEST,			 	TileEntityType.CHEST);
		regInvBlockType(WindowType.GENERIC_9X5, 	Material.CHEST,			 	TileEntityType.CHEST);
		regInvBlockType(WindowType.GENERIC_9X6, 	Material.CHEST,			 	TileEntityType.CHEST);
		regInvBlockType(WindowType.CRAFTING, 		Material.CRAFTING_TABLE, 	TileEntityType.UNKNOWN);
		regInvBlockType(WindowType.FURNACE, 		Material.FURNACE, 			TileEntityType.FURNACE);
		regInvBlockType(WindowType.GENERIC_3X3, 	Material.DISPENSER, 		TileEntityType.DISPENSER);
		regInvBlockType(WindowType.ENCHANTMENT,		Material.HOPPER,		 	TileEntityType.HOPPER); //Fake with hopper
		regInvBlockType(WindowType.BREWING_STAND,	Material.BREWING_STAND, 	TileEntityType.BREWING_STAND);
		regInvBlockType(WindowType.BEACON,			Material.BEACON, 			TileEntityType.BEACON);
		regInvBlockType(WindowType.ANVIL,			Material.ANVIL, 			TileEntityType.UNKNOWN);
		regInvBlockType(WindowType.HOPPER,			Material.HOPPER, 			TileEntityType.HOPPER);
		regInvBlockType(WindowType.GENERIC_3X3,		Material.DROPPER, 			TileEntityType.DROPPER);
		regInvBlockType(WindowType.SHULKER_BOX,		Material.CHEST,				TileEntityType.CHEST); //Fake with chest
	}

	private static Any<Material, TileEntityType> getContainerData(WindowType type) {
		return invBlockType.get(type);
	}

	//Create matching block and tile change packets to fake inventories and store positions of to reset them later. 
	public static Position prepareContainer(BaseComponent title, Connection connection, NetworkDataCache cache, RecyclableArrayList<ClientBoundPacketData> packets) {
		ProtocolVersion version = connection.getVersion();
		WindowCache winCache = cache.getWindowCache();
		PEInventoryCache invCache = cache.getPEInventoryCache();
		Any<Material, TileEntityType> typeData = getContainerData(winCache.getOpenedWindow());
		Position position = new Position(0, 0, 0);
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
			final int networkTileId = MaterialAPI.getBlockDataNetworkId(typeData.getObj1().createBlockData());
			BlockChangeSingle.create(version, position, networkTileId, packets);
			//Set tile data for fake block.
			if (typeData.getObj2() != TileEntityType.UNKNOWN) {
				NBTCompound tag = new NBTCompound();
				TileEntity tile = new TileEntity(typeData.getObj2(), position, tag);
				tag.setTag("CustomName", new NBTString(title.toLegacyText(cache.getAttributesCache().getLocale())));
				//Large inventories require doublechest that requires two blocks and nbt.
				if (shouldDoDoubleChest(cache)) {
					Position auxPos = position.clone();
					auxPos.modifyX(1); //Get adjacent block.
					invCache.getFakeContainers().addLast(auxPos);
					BlockChangeSingle.create(version, auxPos, networkTileId, packets);
					tag.setTag("pairx", new NBTInt(auxPos.getX()));
					tag.setTag("pairz", new NBTInt(auxPos.getZ()));
					tag.setTag("pairlead", new NBTByte((byte) 1));
					packets.add(BlockTileUpdate.create(version, tile));
					NBTCompound auxTag = new NBTCompound();
					TileEntity auxTile = new TileEntity(typeData.getObj2(), auxPos, auxTag);
					auxTag.setTag("CustomName", new NBTString(title.toLegacyText(cache.getAttributesCache().getLocale())));
					auxTag.setTag("pairx", new NBTInt(position.getX()));
					auxTag.setTag("pairz", new NBTInt(position.getZ()));
					auxTag.setTag("pairlead", new NBTByte((byte) 0));
					packets.add(BlockTileUpdate.create(version, auxTile));
				} else {
					packets.add(BlockTileUpdate.create(version, tile));
				}
			}
		}
		return position;
	}

	//Check if player has / needs "fake" double chest.
	public static boolean shouldDoDoubleChest(NetworkDataCache cache) {
		return (cache.getWindowCache().getOpenedWindow().ordinal() < WindowType.GENERIC_9X6.ordinal()
			&& cache.getWindowCache().getOpenedWindowSlots() > SMALLCONTAINERSLOTS);
	}

	//Request reset for all fake container blocks.
	public static void destroyContainers(Connection connection, NetworkDataCache cache) {
		cache.getPEInventoryCache().getFakeContainers().cycleDown(position -> {
			InternalPluginMessageRequest.receivePluginMessageRequest(connection, new BlockUpdateRequest(position));
			return true;
		});
		if (cache.getPEInventoryCache().getFakeVillager().isSpawned()) {
			cache.getPEInventoryCache().getFakeVillager().despawnVillager(connection);
		}
	}

}
