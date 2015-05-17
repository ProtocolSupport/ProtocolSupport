package protocolsupport.server.tileentity;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Container;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.PlayerInventory;
import net.minecraft.server.v1_8_R3.World;
import protocolsupport.server.container.ContainerAnvil;

public class TileEntityContainerAnvil extends net.minecraft.server.v1_8_R3.BlockAnvil.TileEntityContainerAnvil {

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