package protocolsupport.zplatform.impl.spigot.block;

import net.minecraft.server.v1_15_R1.BlockCarpet;
import net.minecraft.server.v1_15_R1.BlockLadder;
import net.minecraft.server.v1_15_R1.BlockWaterLily;
import net.minecraft.server.v1_15_R1.VoxelShapes;
import protocolsupport.utils.ReflectionUtils;

public class SpigotBlocksBoundsAdjust {

	public static void inject() {
		ReflectionUtils.setStaticFinalField(BlockWaterLily.class, "a", VoxelShapes.create(0.0625D, 0.0, 0.0625D, 0.9375D, 0.015625D, 0.9375D));
		ReflectionUtils.setStaticFinalField(BlockLadder.class, "c", VoxelShapes.create(0.0D, 0.0D, 0.0D, 0.125D, 1.0D, 1.0D));
		ReflectionUtils.setStaticFinalField(BlockLadder.class, "d", VoxelShapes.create(0.875D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D));
		ReflectionUtils.setStaticFinalField(BlockLadder.class, "e", VoxelShapes.create(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.125D));
		ReflectionUtils.setStaticFinalField(BlockLadder.class, "f", VoxelShapes.create(0.0D, 0.0D, 0.875D, 1.0D, 1.0D, 1.0D));
		ReflectionUtils.setStaticFinalField(BlockCarpet.class, "a", VoxelShapes.create(0, 0, 0, 1.0D, 0.00001, 1.0D));
	}

}
