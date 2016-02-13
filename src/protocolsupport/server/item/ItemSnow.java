package protocolsupport.server.item;

import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.World;
import protocolsupport.server.block.BlockSnow;

public class ItemSnow extends net.minecraft.server.v1_8_R3.ItemSnow {

	private BlockSnow blockSnow;
	public ItemSnow(BlockSnow blockSnow) {
		super(blockSnow);
		this.blockSnow = blockSnow;
	}

	@Override
	public boolean interactWith(final ItemStack itemStack, final EntityHuman entityHuman, final World world, final BlockPosition blockPosition, final EnumDirection enumDirection, final float n, final float n2, final float n3) {
		if (itemStack.count == 0) {
			return false;
		}
		if (!entityHuman.a(blockPosition, enumDirection, itemStack)) {
			return false;
		}
		IBlockData blockData = world.getType(blockPosition);
		Block block = blockData.getBlock();
		BlockPosition shift = blockPosition;
		if ((enumDirection != EnumDirection.UP || block != this.a) && !block.a(world, blockPosition)) {
			shift = blockPosition.shift(enumDirection);
			blockData = world.getType(shift);
			block = blockData.getBlock();
		}
		if (block == this.a) {
			final int intValue = blockData.get(net.minecraft.server.v1_8_R3.BlockSnow.LAYERS);
			if (intValue <= 7) {
				final IBlockData set = blockData.set(net.minecraft.server.v1_8_R3.BlockSnow.LAYERS, (intValue + 1));
				final AxisAlignedBB a = this.blockSnow.getRealBB(world, shift, set);
				if (a != null && world.b(a) && world.setTypeAndData(shift, set, 2)) {
					world.makeSound(shift.getX() + 0.5f, shift.getY() + 0.5f, shift.getZ() + 0.5f, this.a.stepSound.getPlaceSound(), (this.a.stepSound.getVolume1() + 1.0f) / 2.0f, this.a.stepSound.getVolume2() * 0.8f);
					--itemStack.count;
					return true;
				}
			}
		}
		return super.interactWith(itemStack, entityHuman, world, shift, enumDirection, n, n2, n3);
	}

}
