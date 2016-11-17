package protocolsupport.server.container;

import net.minecraft.server.v1_11_R1.BlockPosition;
import net.minecraft.server.v1_11_R1.EntityHuman;
import net.minecraft.server.v1_11_R1.ICrafting;
import net.minecraft.server.v1_11_R1.PlayerInventory;
import net.minecraft.server.v1_11_R1.World;

public class ContainerAnvil extends net.minecraft.server.v1_11_R1.ContainerAnvil {

	public ContainerAnvil(PlayerInventory playerinventory, World world, BlockPosition blockposition, EntityHuman entityhuman) {
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
