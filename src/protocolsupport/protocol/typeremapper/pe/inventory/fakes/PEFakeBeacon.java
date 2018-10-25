package protocolsupport.protocol.typeremapper.pe.inventory.fakes;

import org.bukkit.Material;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.BlockChangeSingle;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.BlockTileUpdate;
import protocolsupport.protocol.storage.netcache.NetworkDataCache;
import protocolsupport.protocol.storage.netcache.PEInventoryCache;
import protocolsupport.protocol.typeremapper.pe.PEBlocks;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.protocol.utils.types.WindowType;
import protocolsupport.protocol.utils.types.nbt.NBTCompound;
import protocolsupport.protocol.utils.types.nbt.NBTInt;
import protocolsupport.protocol.utils.types.nbt.NBTString;
import protocolsupport.utils.recyclable.RecyclableArrayList;

public class PEFakeBeacon {

	private int level = 0;
	private int primary = 0;
	private int secondary = 0;

	public void setLevel(int level) {
		this.level = level;
	}

	public void setPrimaryEffect(int effect) {
		this.primary = effect;
	}

	public void setSecondaryEffect(int effect) {
		this.secondary = effect;
	}

	public RecyclableArrayList<ClientBoundPacketData> updateTemple(ProtocolVersion version, NetworkDataCache cache) {
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		PEInventoryCache invCache = cache.getPEInventoryCache();
		if (cache.getWindowCache().getOpenedWindow() == WindowType.BEACON && invCache.getFakeContainers().hasFirst()) {
			Position position = invCache.getFakeContainers().getFirst();
			for (int i = 1; i < level + 1; i++) {
				for (int x = -i; x < i + 1; x++) {
					for (int z = -i; z < i + 1; z++) {
						Position block = position.clone();
						block.mod(x, -i, z);
						invCache.getFakeContainers().addLast(block);
						BlockChangeSingle.create(version, block,
								PEBlocks.toPocketBlock(version, Material.EMERALD_BLOCK), packets);
					}
				}
			}
		}
		return packets;
	}

	public RecyclableArrayList<ClientBoundPacketData> updateNBT(ProtocolVersion version, NetworkDataCache cache) {
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		PEInventoryCache invCache = cache.getPEInventoryCache();
		if (cache.getWindowCache().getOpenedWindow() == WindowType.BEACON && invCache.getFakeContainers().hasFirst()) {
			NBTCompound tag = new NBTCompound();
			tag.setTag("id", new NBTString("beacon"));
			tag.setTag("primary", new NBTInt(primary));
			tag.setTag("secondary", new NBTInt(secondary));
			packets.add(BlockTileUpdate.create(version, invCache.getFakeContainers().getFirst(), tag));
		}
		return packets;
	}

}