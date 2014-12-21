package protocolsupport.server.tileentity;

import protocolsupport.server.container.ContainerEnchantTable;
import net.minecraft.server.v1_8_R1.Container;
import net.minecraft.server.v1_8_R1.EntityHuman;
import net.minecraft.server.v1_8_R1.PlayerInventory;

public class TileEntityEnchantTable extends net.minecraft.server.v1_8_R1.TileEntityEnchantTable {

	@Override
	public void c() {
	}

	@Override
	public Container createContainer(final PlayerInventory playerinventory, final EntityHuman entityHuman) {
		return new ContainerEnchantTable(playerinventory, this.world, this.position);
	}

}