package protocolsupport.server.block;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.server.v1_11_R1.AxisAlignedBB;
import net.minecraft.server.v1_11_R1.BlockPosition;
import net.minecraft.server.v1_11_R1.Entity;
import net.minecraft.server.v1_11_R1.EntityBoat;
import net.minecraft.server.v1_11_R1.IBlockAccess;
import net.minecraft.server.v1_11_R1.IBlockData;
import net.minecraft.server.v1_11_R1.SoundEffectType;
import net.minecraft.server.v1_11_R1.World;

public class BlockWaterLily extends net.minecraft.server.v1_11_R1.BlockWaterLily {

	public BlockWaterLily() {
		c(0.0f);
		a(SoundEffectType.c);
		c("waterlily");
	}

	private static final AxisAlignedBB bounds = new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.9375, 0.015625, 0.9375);

	@Override
	public void a(IBlockData iblockdata, World world, BlockPosition blockposition, AxisAlignedBB axisalignedbb, List<AxisAlignedBB> list, @Nullable Entity entity) {
		if (!(entity instanceof EntityBoat)) {
			a(blockposition, axisalignedbb, list, bounds);
		}
	}

	@Override
	public AxisAlignedBB a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
		return bounds;
	}

}
