package protocolsupport.server.block;

import net.minecraft.server.v1_10_R1.TileEntity;
import net.minecraft.server.v1_10_R1.World;
import protocolsupport.server.tileentity.TileEntityEnchantTable;

public class BlockEnchantTable extends net.minecraft.server.v1_10_R1.BlockEnchantmentTable {

	public BlockEnchantTable() {
		super();
		c(5.0F);
		b(2000.0F);
		c("enchantmentTable");
	}

	@Override
	public TileEntity a(final World world, final int n) {
		return new TileEntityEnchantTable();
	}

}
