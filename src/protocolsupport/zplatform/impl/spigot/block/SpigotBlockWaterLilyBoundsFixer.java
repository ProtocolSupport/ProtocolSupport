package protocolsupport.zplatform.impl.spigot.block;

import net.minecraft.server.v1_12_R1.AxisAlignedBB;
import net.minecraft.server.v1_12_R1.BlockWaterLily;
import protocolsupport.utils.ReflectionUtils;

public class SpigotBlockWaterLilyBoundsFixer {

	public static void inject() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		ReflectionUtils.setStaticFinalField(ReflectionUtils.getField(BlockWaterLily.class, "a"), new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.9375, 0.015625, 0.9375));
	}

}
