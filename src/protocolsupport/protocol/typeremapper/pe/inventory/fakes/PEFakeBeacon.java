package protocolsupport.protocol.typeremapper.pe.inventory.fakes;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.BlockChangeSingle;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.BlockTileUpdate;
import protocolsupport.protocol.storage.netcache.NetworkDataCache;
import protocolsupport.protocol.storage.netcache.PEInventoryCache;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.protocol.utils.types.WindowType;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

public class PEFakeBeacon {

	private static final int EMERALD_BLOCK = 133 << 4;

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
						packets.add(BlockChangeSingle.create(version, block, EMERALD_BLOCK));
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
			NBTTagCompoundWrapper tag = ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound();
			tag.setString("id", "beacon");
			tag.setInt("primary", primary);
			tag.setInt("secondary", secondary);
			packets.add(BlockTileUpdate.create(version, invCache.getFakeContainers().getFirst(), tag));
		}
		return packets;
	}

}
