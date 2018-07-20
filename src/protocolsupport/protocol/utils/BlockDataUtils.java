package protocolsupport.protocol.utils;

import java.util.function.Consumer;

import org.bukkit.Material;
import org.bukkit.block.data.BlockData;

public class BlockDataUtils {

	@SafeVarargs
	@SuppressWarnings("unchecked")
	public static <T extends BlockData> T create(Material material, Consumer<T>... consumers) {
		T blockdata = (T) material.createBlockData();
		for (Consumer<T> consumer : consumers) {
			consumer.accept(blockdata);
		}
		return blockdata;
	}

}
