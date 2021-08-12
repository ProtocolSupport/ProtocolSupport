package protocolsupport.zplatform.impl.spigot.block;

import java.lang.reflect.Field;
import java.util.function.Consumer;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BlockIronBars;
import net.minecraft.world.level.block.BlockLadder;
import net.minecraft.world.level.block.BlockTall;
import net.minecraft.world.level.block.BlockWaterLily;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapes;
import protocolsupport.ProtocolSupport;
import protocolsupport.utils.reflection.FieldWriter;
import protocolsupport.utils.reflection.ReflectionUtils;

public class SpigotBlocksBoundsAdjust {

	private SpigotBlocksBoundsAdjust() {
	}

	public static void inject() {
		try {
			ReflectionUtils.setStaticFinalFieldValue(BlockWaterLily.class, "a", VoxelShapes.create(0.0625D, 0.0, 0.0625D, 0.9375D, 0.015625D, 0.9375D));

			ReflectionUtils.setStaticFinalFieldValue(BlockLadder.class, "d", VoxelShapes.create(0.0D, 0.0D, 0.0D, 0.125D, 1.0D, 1.0D));
			ReflectionUtils.setStaticFinalFieldValue(BlockLadder.class, "e", VoxelShapes.create(0.875D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D));
			ReflectionUtils.setStaticFinalFieldValue(BlockLadder.class, "f", VoxelShapes.create(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.125D));
			ReflectionUtils.setStaticFinalFieldValue(BlockLadder.class, "g", VoxelShapes.create(0.0D, 0.0D, 0.875D, 1.0D, 1.0D, 1.0D));

			ReflectionUtils.setStaticFinalFieldValue(CarpetBlock.class, "a", VoxelShapes.create(0, 0, 0, 1.0D, 0.00001, 1.0D));

			{
				FieldWriter<VoxelShape[]> shapesField1 = FieldWriter.of(BlockTall.class, "g", VoxelShape[].class);
				FieldWriter<VoxelShape[]> shapesField2 = FieldWriter.of(BlockTall.class, "h", VoxelShape[].class);
				Consumer<BlockIronBars> setBarsBounds = bars -> {
					VoxelShape[] shapes = createBarsShapes();
					shapesField1.set(bars, shapes);
					shapesField2.set(bars, shapes);
				};
				for (Field field : Blocks.class.getFields()) {
					Block block = (Block) field.get(null);
					if (block instanceof BlockIronBars blockbars) {
						setBarsBounds.accept(blockbars);
					}
				}
			}
		} catch (Throwable t) {
			ProtocolSupport.logErrorSevere("Unable to inject custom block bounds", t);
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
