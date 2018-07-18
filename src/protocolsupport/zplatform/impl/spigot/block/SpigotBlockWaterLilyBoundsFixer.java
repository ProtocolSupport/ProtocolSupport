package protocolsupport.zplatform.impl.spigot.block;

import net.minecraft.server.v1_13_R1.AxisAlignedBB;
import net.minecraft.server.v1_13_R1.BlockWaterLily;
import protocolsupport.utils.ReflectionUtils;

public class SpigotBlockWaterLilyBoundsFixer {

	public static void inject() throws NoSuchFieldException, IllegalAccessException {
//TODO: update after understanding voxel shape
//		ReflectionUtils.setStaticFinalField(ReflectionUtils.getField(BlockWaterLily.class, "a"), new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.9375, 0.015625, 0.9375));
	}

}
