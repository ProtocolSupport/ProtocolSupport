package protocolsupport.server.block;

import net.minecraft.server.v1_8_R1.Block;
import net.minecraft.server.v1_8_R1.BlockPosition;
import net.minecraft.server.v1_8_R1.BlockStateDirection;
import net.minecraft.server.v1_8_R1.BlockStateInteger;
import net.minecraft.server.v1_8_R1.EntityHuman;
import net.minecraft.server.v1_8_R1.EnumDirection;
import net.minecraft.server.v1_8_R1.EnumDirectionLimit;
import net.minecraft.server.v1_8_R1.IBlockData;
import net.minecraft.server.v1_8_R1.World;
import protocolsupport.server.tileentity.TileEntityContainerAnvil;

public class BlockAnvil extends net.minecraft.server.v1_8_R1.BlockAnvil {

	public static final BlockStateDirection FACING = BlockStateDirection.of("facing", EnumDirectionLimit.HORIZONTAL);
	public static final BlockStateInteger DAMAGE = BlockStateInteger.of("damage", 0, 2);

	public BlockAnvil() {
		super();
		c(5.0f);
		a(Block.p);
		b(2000.0f);
		c("anvil");
	}

	@Override
	public boolean interact(final World world, final BlockPosition blockposition, final IBlockData iblockdata, final EntityHuman entityhuman, final EnumDirection enumdirection, final float f, final float f1, final float f2) {
		entityhuman.openTileEntity(new TileEntityContainerAnvil(world, blockposition));
		return true;
	}

}
