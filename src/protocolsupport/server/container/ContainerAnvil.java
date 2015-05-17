package protocolsupport.server.container;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.ICrafting;
import net.minecraft.server.v1_8_R3.PlayerInventory;
import net.minecraft.server.v1_8_R3.World;

public class ContainerAnvil extends net.minecraft.server.v1_8_R3.ContainerAnvil {

	public ContainerAnvil(final PlayerInventory playerinventory, final World world, final BlockPosition blockposition, final EntityHuman entityhuman) {
		super(playerinventory, world, blockposition, entityhuman);
	}

	@Override
	public void b() {
		super.b();
		for (ICrafting listener : listeners) {
			listener.setContainerData(this, 0, a);
		}
	}

}
