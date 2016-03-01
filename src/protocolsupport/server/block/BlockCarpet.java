package protocolsupport.server.block;

import net.minecraft.server.v1_9_R1.SoundEffectType;

public class BlockCarpet extends net.minecraft.server.v1_9_R1.BlockCarpet {

	public BlockCarpet() {
		c(0.1F);
		a(SoundEffectType.g);
		c("woolCarpet");
		d(0);
	}

	/*TODO: Moved to blockdata
	@Override
	public AxisAlignedBB a(final World world, final BlockPosition blockposition, final IBlockData iblockdata) {
		return null;
	}
	*/

}
