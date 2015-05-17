package protocolsupport.server.block;

import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.World;

public class BlockAnvil extends net.minecraft.server.v1_8_R3.BlockAnvil {

	public BlockAnvil() {
		super();
		c(5.0f);
		a(Block.p);
		b(2000.0f);
		c("anvil");
	}

	@Override
	public boolean interact(final World world, final BlockPosition blockposition, final IBlockData iblockdata, final EntityHuman entityhuman, final EnumDirection enumdirection, final float f, final float f1, final float f2) {
		entityhuman.openTileEntity(new protocolsupport.server.tileentity.TileEntityContainerAnvil(world, blockposition));
		return true;
	}

}
