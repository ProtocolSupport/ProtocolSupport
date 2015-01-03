package protocolsupport.server.container;

import java.util.List;

import net.minecraft.server.v1_8_R1.BlockPosition;
import net.minecraft.server.v1_8_R1.EntityHuman;
import net.minecraft.server.v1_8_R1.ICrafting;
import net.minecraft.server.v1_8_R1.PlayerInventory;
import net.minecraft.server.v1_8_R1.World;

public class ContainerAnvil extends net.minecraft.server.v1_8_R1.ContainerAnvil {

	public ContainerAnvil(final PlayerInventory playerinventory, final World world, final BlockPosition blockposition, final EntityHuman entityhuman) {
		super(playerinventory, world, blockposition, entityhuman);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void b() {
		super.b();
		for (ICrafting listener : (List<ICrafting>) this.listeners) {
			listener.setContainerData(this, 0, this.a);
		}
	}

}
