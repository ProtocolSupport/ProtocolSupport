package protocolsupport.server.item;

import net.minecraft.server.v1_9_R1.AxisAlignedBB;
import net.minecraft.server.v1_9_R1.Block;
import net.minecraft.server.v1_9_R1.BlockPosition;
import net.minecraft.server.v1_9_R1.EntityHuman;
import net.minecraft.server.v1_9_R1.EnumDirection;
import net.minecraft.server.v1_9_R1.EnumHand;
import net.minecraft.server.v1_9_R1.EnumInteractionResult;
import net.minecraft.server.v1_9_R1.IBlockData;
import net.minecraft.server.v1_9_R1.ItemStack;
import net.minecraft.server.v1_9_R1.SoundCategory;
import net.minecraft.server.v1_9_R1.SoundEffectType;
import net.minecraft.server.v1_9_R1.World;
import protocolsupport.server.block.BlockSnow;

public class ItemSnow extends net.minecraft.server.v1_9_R1.ItemSnow {

	public ItemSnow(BlockSnow blockSnow) {
		super(blockSnow);
	}

	@Override
	public EnumInteractionResult a(final ItemStack itemStack, final EntityHuman entityHuman, final World world, final BlockPosition blockPosition, final EnumHand paramEnumHand, final EnumDirection enumDirection, final float n, final float n2, final float n3) {
		if (itemStack.count == 0) {
			return EnumInteractionResult.FAIL;
		}
		if (!entityHuman.a(blockPosition, enumDirection, itemStack)) {
			return EnumInteractionResult.FAIL;
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
			final int intValue = blockData.get(net.minecraft.server.v1_9_R1.BlockSnow.LAYERS);
			if (intValue <= 7) {
				final IBlockData set = blockData.set(net.minecraft.server.v1_9_R1.BlockSnow.LAYERS, (intValue + 1));
				final AxisAlignedBB a = set.d(world, shift);
				if (a != null && world.b(a) && world.setTypeAndData(shift, set, 2)) {
					SoundEffectType localSoundEffectType = this.a.w();
					world.a(entityHuman, blockPosition, localSoundEffectType.e(), SoundCategory.BLOCKS, (localSoundEffectType.a() + 1.0F) / 2.0F, localSoundEffectType.b() * 0.8F);
					--itemStack.count;
					return EnumInteractionResult.SUCCESS;
				}
			}
		}
		return super.a(itemStack, entityHuman, world, shift, paramEnumHand, enumDirection, n, n2, n3);
	}

}
