package protocolsupport.zplatform.impl.spigot.block;

import net.minecraft.server.v1_14_R1.BlockLadder;
import net.minecraft.server.v1_14_R1.BlockWaterLily;
import net.minecraft.server.v1_14_R1.VoxelShapes;
import protocolsupport.utils.ReflectionUtils;

public class SpigotBlocksBoundsAdjust {

	public static void inject() throws IllegalAccessException {
		ReflectionUtils.cloneFields(VoxelShapes.create(0.0625D, 0.0, 0.0625D, 0.9375D, 0.015625D, 0.9375D), ReflectionUtils.getField(BlockWaterLily.class, "a").get(null));
		ReflectionUtils.cloneFields(VoxelShapes.create(0.0D, 0.0D, 0.0D, 0.124D, 1.0D, 1.0D), ReflectionUtils.getField(BlockLadder.class, "c").get(null));
		ReflectionUtils.cloneFields(VoxelShapes.create(0.876D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D), ReflectionUtils.getField(BlockLadder.class, "d").get(null));
		ReflectionUtils.cloneFields(VoxelShapes.create(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.124D), ReflectionUtils.getField(BlockLadder.class, "e").get(null));
		ReflectionUtils.cloneFields(VoxelShapes.create(0.0D, 0.0D, 0.876D, 1.0D, 1.0D, 1.0D), ReflectionUtils.getField(BlockLadder.class, "f").get(null));
	}

}
