package protocolsupport.zplatform.impl.spigot.block;

import java.lang.reflect.Field;
import java.util.function.Consumer;

import net.minecraft.server.v1_16_R3.Block;
import net.minecraft.server.v1_16_R3.BlockCarpet;
import net.minecraft.server.v1_16_R3.BlockIronBars;
import net.minecraft.server.v1_16_R3.BlockLadder;
import net.minecraft.server.v1_16_R3.BlockTall;
import net.minecraft.server.v1_16_R3.BlockWaterLily;
import net.minecraft.server.v1_16_R3.Blocks;
import net.minecraft.server.v1_16_R3.VoxelShape;
import net.minecraft.server.v1_16_R3.VoxelShapes;
import protocolsupport.utils.ReflectionUtils;

public class SpigotBlocksBoundsAdjust {

	public static void inject() {
		ReflectionUtils.setStaticFinalField(BlockWaterLily.class, "a", VoxelShapes.create(0.0625D, 0.0, 0.0625D, 0.9375D, 0.015625D, 0.9375D));

		ReflectionUtils.setStaticFinalField(BlockLadder.class, "c", VoxelShapes.create(0.0D, 0.0D, 0.0D, 0.125D, 1.0D, 1.0D));
		ReflectionUtils.setStaticFinalField(BlockLadder.class, "d", VoxelShapes.create(0.875D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D));
		ReflectionUtils.setStaticFinalField(BlockLadder.class, "e", VoxelShapes.create(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.125D));
		ReflectionUtils.setStaticFinalField(BlockLadder.class, "f", VoxelShapes.create(0.0D, 0.0D, 0.875D, 1.0D, 1.0D, 1.0D));

		ReflectionUtils.setStaticFinalField(BlockCarpet.class, "a", VoxelShapes.create(0, 0, 0, 1.0D, 0.00001, 1.0D));

		{
			Field shapesField1 = ReflectionUtils.getField(BlockTall.class, "g");
			Field shapesField2 = ReflectionUtils.getField(BlockTall.class, "h");
			Consumer<BlockIronBars> setBarsBounds = bars -> {
				VoxelShape[] shapes = createBarsShapes();
				ReflectionUtils.setField(shapesField1, bars, shapes);
				ReflectionUtils.setField(shapesField2, bars, shapes);
			};

			setBarsBounds.accept((BlockIronBars) Blocks.IRON_BARS);
			setBarsBounds.accept((BlockIronBars) Blocks.GLASS_PANE);
			setBarsBounds.accept((BlockIronBars) Blocks.WHITE_STAINED_GLASS_PANE);
			setBarsBounds.accept((BlockIronBars) Blocks.ORANGE_STAINED_GLASS_PANE);
			setBarsBounds.accept((BlockIronBars) Blocks.MAGENTA_STAINED_GLASS_PANE);
			setBarsBounds.accept((BlockIronBars) Blocks.LIGHT_BLUE_STAINED_GLASS_PANE);
			setBarsBounds.accept((BlockIronBars) Blocks.YELLOW_STAINED_GLASS_PANE);
			setBarsBounds.accept((BlockIronBars) Blocks.LIME_STAINED_GLASS_PANE);
			setBarsBounds.accept((BlockIronBars) Blocks.PINK_STAINED_GLASS_PANE);
			setBarsBounds.accept((BlockIronBars) Blocks.GRAY_STAINED_GLASS_PANE);
			setBarsBounds.accept((BlockIronBars) Blocks.LIGHT_GRAY_STAINED_GLASS_PANE);
			setBarsBounds.accept((BlockIronBars) Blocks.CYAN_STAINED_GLASS_PANE);
			setBarsBounds.accept((BlockIronBars) Blocks.PURPLE_STAINED_GLASS_PANE);
			setBarsBounds.accept((BlockIronBars) Blocks.BLUE_STAINED_GLASS_PANE);
			setBarsBounds.accept((BlockIronBars) Blocks.BROWN_STAINED_GLASS_PANE);
			setBarsBounds.accept((BlockIronBars) Blocks.GREEN_STAINED_GLASS_PANE);
			setBarsBounds.accept((BlockIronBars) Blocks.RED_STAINED_GLASS_PANE);
			setBarsBounds.accept((BlockIronBars) Blocks.BLACK_STAINED_GLASS_PANE);
		}
	}

	protected static VoxelShape[] createBarsShapes() {
		double centerOffsetNeg = 7.0D;
		double centerOffsetPos = 9.0D;

		VoxelShape shapeMinZ = Block.a(
			centerOffsetNeg, 0.0D, 0.0D,
			centerOffsetPos, 16.0D, 8.0D
		);
		VoxelShape shapeMaxZ = Block.a(
			centerOffsetNeg, 0.0D, 8.0D,
			centerOffsetPos, 16.0D, 16.0D
		);
		VoxelShape shapeMinX = Block.a(
			0.0D, 0.0D, centerOffsetNeg,
			8.0D, 16.0D, centerOffsetPos
		);
		VoxelShape shapeMaxX = Block.a(
			8.0D, 0.0D, centerOffsetNeg,
			16.0D, 16.0D, centerOffsetPos
		);
		VoxelShape shapeMinZMaxX = VoxelShapes.a(shapeMinZ, shapeMaxX);
		VoxelShape shapeMaxZMinX = VoxelShapes.a(shapeMaxZ, shapeMinX);

		return new VoxelShape[] {
			Block.a(centerOffsetNeg, 0.0D, centerOffsetNeg, centerOffsetPos, 16.0D, centerOffsetPos),
			shapeMaxZ,
			shapeMinX,
			shapeMaxZMinX,
			shapeMinZ,
			VoxelShapes.a(shapeMaxZ, shapeMinZ),
			VoxelShapes.a(shapeMinX, shapeMinZ),
			VoxelShapes.a(shapeMaxZMinX, shapeMinZ),
			shapeMaxX,
			VoxelShapes.a(shapeMaxZ, shapeMaxX),
			VoxelShapes.a(shapeMinX, shapeMaxX),
			VoxelShapes.a(shapeMaxZMinX, shapeMaxX),
			shapeMinZMaxX,
			VoxelShapes.a(shapeMaxZ, shapeMinZMaxX),
			VoxelShapes.a(shapeMinX, shapeMinZMaxX),
			VoxelShapes.a(shapeMaxZMinX, shapeMinZMaxX)
		};
	}

}
