package protocolsupport.server.block;

import net.minecraft.server.v1_8_R3.TileEntity;
import net.minecraft.server.v1_8_R3.World;
import protocolsupport.server.tileentity.TileEntityEnchantTable;

public class BlockEnchantTable extends net.minecraft.server.v1_8_R3.BlockEnchantmentTable {

	public BlockEnchantTable() {
		super();
		c(5.0f);
		b(2000.0f);
		c("enchantmentTable");
	}

	@Override
	public TileEntity a(final World world, final int n) {
		return new TileEntityEnchantTable();
	}

}
