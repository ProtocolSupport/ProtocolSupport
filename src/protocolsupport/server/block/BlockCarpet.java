package protocolsupport.server.block;

import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.World;

public class BlockCarpet extends net.minecraft.server.v1_8_R3.BlockCarpet {

	public BlockCarpet() {
		c(0.1f);
		a(Block.l);
		c("woolCarpet");
		e(0);
	}

	@Override
	public AxisAlignedBB a(final World world, final BlockPosition blockposition, final IBlockData iblockdata) {
		return null;
	}

}
