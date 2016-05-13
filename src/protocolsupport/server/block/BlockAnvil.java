package protocolsupport.server.block;

import net.minecraft.server.v1_9_R2.BlockPosition;
import net.minecraft.server.v1_9_R2.EntityHuman;
import net.minecraft.server.v1_9_R2.EnumDirection;
import net.minecraft.server.v1_9_R2.EnumHand;
import net.minecraft.server.v1_9_R2.IBlockData;
import net.minecraft.server.v1_9_R2.ItemStack;
import net.minecraft.server.v1_9_R2.SoundEffectType;
import net.minecraft.server.v1_9_R2.World;

public class BlockAnvil extends net.minecraft.server.v1_9_R2.BlockAnvil {

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
