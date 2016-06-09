package protocolsupport.server.block;

import net.minecraft.server.v1_10_R1.BlockPosition;
import net.minecraft.server.v1_10_R1.EntityHuman;
import net.minecraft.server.v1_10_R1.EnumDirection;
import net.minecraft.server.v1_10_R1.EnumHand;
import net.minecraft.server.v1_10_R1.IBlockData;
import net.minecraft.server.v1_10_R1.ItemStack;
import net.minecraft.server.v1_10_R1.SoundEffectType;
import net.minecraft.server.v1_10_R1.World;

public class BlockAnvil extends net.minecraft.server.v1_10_R1.BlockAnvil {

	public BlockAnvil() {
		super();
		c(5.0F);
		a(SoundEffectType.k);
		b(2000.0F);
		c("anvil");
	}

	@Override
	public boolean interact(final World world, final BlockPosition blockposition, final IBlockData iblockdata, final EntityHuman entityhuman, final EnumHand enumhand, final ItemStack itemstack, final EnumDirection enumdirection, final float f, final float f1, final float f2) {
		entityhuman.openTileEntity(new protocolsupport.server.tileentity.TileEntityContainerAnvil(world, blockposition));
		return true;
	}

}
