package protocolsupport.server.block;

import net.minecraft.server.v1_11_R1.TileEntity;
import net.minecraft.server.v1_11_R1.World;
import protocolsupport.server.tileentity.TileEntityEnchantTable;

public class BlockEnchantTable extends net.minecraft.server.v1_11_R1.BlockEnchantmentTable {

	public BlockEnchantTable() {
		c(5.0F);
		b(2000.0F);
		c("enchantmentTable");
	}

	@Override
	public TileEntity a(World world, int n) {
		return new TileEntityEnchantTable();
	}

}
