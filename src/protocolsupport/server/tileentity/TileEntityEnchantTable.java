package protocolsupport.server.tileentity;

import net.minecraft.server.v1_10_R1.Container;
import net.minecraft.server.v1_10_R1.EntityHuman;
import net.minecraft.server.v1_10_R1.PlayerInventory;
import protocolsupport.server.container.ContainerEnchantTable;

public class TileEntityEnchantTable extends net.minecraft.server.v1_10_R1.TileEntityEnchantTable {

	@Override
	public void E_() {
	}

	@Override
	public Container createContainer(final PlayerInventory playerinventory, final EntityHuman entityHuman) {
		return new ContainerEnchantTable(playerinventory, world, position);
	}

}