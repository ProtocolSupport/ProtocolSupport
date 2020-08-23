package protocolsupport.protocol.storage.netcache;

import org.bukkit.block.Biome;

public interface IBiomeRegistry {

	public Biome getBiome(int id);

	public int getBiomeId(Biome biome);

}
