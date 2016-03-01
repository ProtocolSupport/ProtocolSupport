package protocolsupport.server.block;

import net.minecraft.server.v1_9_R1.SoundEffectType;

public class BlockSnow extends net.minecraft.server.v1_9_R1.BlockSnow {

	public BlockSnow() {
		c(0.1F);
		a(SoundEffectType.i);
		c("snow");
		d(0);
	}

	/*TODO: Moved to blockdata
	@Override
	public AxisAlignedBB a(final World world, final BlockPosition blockposition) {
		return null;
	}

	public AxisAlignedBB getRealBB(final World world, final BlockPosition blockposition, final IBlockData iblockdata) {
		return super.a(world, blockposition);
	}*/

}
