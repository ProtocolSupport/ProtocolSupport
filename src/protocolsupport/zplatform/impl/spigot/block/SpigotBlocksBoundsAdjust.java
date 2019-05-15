package protocolsupport.zplatform.impl.spigot.block;

import net.minecraft.server.v1_14_R1.BlockWaterLily;
import net.minecraft.server.v1_14_R1.VoxelShapes;
import protocolsupport.utils.ReflectionUtils;

public class SpigotBlocksBoundsAdjust {

	public static void inject() throws IllegalAccessException {
		ReflectionUtils.cloneFields(VoxelShapes.create(0.0625, 0.0, 0.0625, 0.9375, 0.015625, 0.9375), ReflectionUtils.getField(BlockWaterLily.class, "a").get(null));
	}

}
