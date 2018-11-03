package protocolsupport.zplatform.impl.spigot.block;

import net.minecraft.server.v1_13_R2.Block;
import net.minecraft.server.v1_13_R2.BlockWaterLily;
import protocolsupport.utils.ReflectionUtils;

public class SpigotBlockWaterLilyBoundsFixer {

	public static void inject() throws NoSuchFieldException, IllegalAccessException {
		ReflectionUtils.setStaticFinalField(ReflectionUtils.getField(BlockWaterLily.class, "a"), Block.a(0.0625, 0.0, 0.0625, 0.9375, 0.015625, 0.9375));
	}

}
