package protocolsupport.server.tileentity;

import protocolsupport.server.container.ContainerAnvil;
import net.minecraft.server.v1_8_R1.BlockPosition;
import net.minecraft.server.v1_8_R1.Container;
import net.minecraft.server.v1_8_R1.EntityHuman;
import net.minecraft.server.v1_8_R1.PlayerInventory;
import net.minecraft.server.v1_8_R1.World;

public class TileEntityContainerAnvil extends net.minecraft.server.v1_8_R1.TileEntityContainerAnvil {

	private final World world;
	private final BlockPosition position;

	public TileEntityContainerAnvil(final World world, final BlockPosition position) {
		super(world, position);
		this.world = world;
		this.position = position;
	}

	@Override
	public Container createContainer(final PlayerInventory playerinventory, final EntityHuman entityhuman) {
		return new ContainerAnvil(playerinventory, world, position, entityhuman);
	}

}