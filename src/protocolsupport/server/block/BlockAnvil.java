package protocolsupport.server.block;

import net.minecraft.server.v1_11_R1.BlockPosition;
import net.minecraft.server.v1_11_R1.EntityHuman;
import net.minecraft.server.v1_11_R1.EnumDirection;
import net.minecraft.server.v1_11_R1.EnumHand;
import net.minecraft.server.v1_11_R1.IBlockData;
import net.minecraft.server.v1_11_R1.SoundEffectType;
import net.minecraft.server.v1_11_R1.World;

public class BlockAnvil extends net.minecraft.server.v1_11_R1.BlockAnvil {

	public BlockAnvil() {
		c(5.0F);
		a(SoundEffectType.k);
		b(2000.0F);
		c("anvil");
	}

	@Override
	public boolean interact(World world, BlockPosition blockPosition, IBlockData blockData, EntityHuman entityHuman, EnumHand enumHand, EnumDirection enumDirection, float n, float n2, float n3) {
		entityHuman.openTileEntity(new protocolsupport.server.tileentity.TileEntityContainerAnvil(world, blockPosition));
		return true;
	}

}
