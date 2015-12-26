package protocolsupport.server.block;

import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.World;

public class BlockSnow extends net.minecraft.server.v1_8_R3.BlockSnow {

	public BlockSnow() {
		c(0.1f);
		a(Block.n);
		c("snow");
		e(0);
	}

	@Override
	public AxisAlignedBB a(final World world, final BlockPosition blockposition, final IBlockData iblockdata) {
		return null;
	}

	public AxisAlignedBB getRealBB(final World world, final BlockPosition blockposition, final IBlockData iblockdata) {
		return super.a(world, blockposition, iblockdata);
	}

}
