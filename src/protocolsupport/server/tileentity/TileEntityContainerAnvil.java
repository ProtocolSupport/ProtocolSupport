package protocolsupport.server.tileentity;

import net.minecraft.server.v1_11_R1.BlockPosition;
import net.minecraft.server.v1_11_R1.Container;
import net.minecraft.server.v1_11_R1.EntityHuman;
import net.minecraft.server.v1_11_R1.PlayerInventory;
import net.minecraft.server.v1_11_R1.World;
import protocolsupport.server.container.ContainerAnvil;

public class TileEntityContainerAnvil extends net.minecraft.server.v1_11_R1.BlockAnvil.TileEntityContainerAnvil {

	private World world;
	private BlockPosition position;

	public TileEntityContainerAnvil(World world, BlockPosition position) {
		super(world, position);
		this.world = world;
		this.position = position;
	}

	@Override
	public Container createContainer(PlayerInventory playerinventory, EntityHuman entityhuman) {
		return new ContainerAnvil(playerinventory, world, position, entityhuman);
	}

}