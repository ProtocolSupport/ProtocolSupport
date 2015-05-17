package protocolsupport.server.tileentity;

import net.minecraft.server.v1_8_R3.Container;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.PlayerInventory;
import protocolsupport.server.container.ContainerEnchantTable;

public class TileEntityEnchantTable extends net.minecraft.server.v1_8_R3.TileEntityEnchantTable {

	@Override
	public void c() {
	}

	@Override
	public Container createContainer(final PlayerInventory playerinventory, final EntityHuman entityHuman) {
		return new ContainerEnchantTable(playerinventory, world, position);
	}

}