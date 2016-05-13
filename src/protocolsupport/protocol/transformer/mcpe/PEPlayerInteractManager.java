package protocolsupport.protocol.transformer.mcpe;

import java.lang.invoke.MethodHandle;

import org.spigotmc.SneakyThrow;

import net.minecraft.server.v1_9_R2.Block;
import net.minecraft.server.v1_9_R2.BlockPosition;
import net.minecraft.server.v1_9_R2.Blocks;
import net.minecraft.server.v1_9_R2.EntityPlayer;
import net.minecraft.server.v1_9_R2.Material;
import net.minecraft.server.v1_9_R2.PacketPlayOutBlockChange;
import net.minecraft.server.v1_9_R2.PlayerInteractManager;

import protocolsupport.utils.Utils;

public class PEPlayerInteractManager extends PlayerInteractManager {

	public PEPlayerInteractManager(EntityPlayer player) {
		super(player.world);
		this.player = player;
	}

	@Override
	public void a(BlockPosition blockposition) {
		if (blockposition.equals(getLastInteractBlockPosition())) {
			Block block = this.world.getType(blockposition).getBlock();
			if (block.getBlockData().getMaterial() != Material.AIR && block != Blocks.BEDROCK) {
				setBreakingStateValue(false);
				world.c(this.player.getId(), blockposition, -1);
				breakBlock(blockposition);
			}
		} else {
			player.playerConnection.sendPacket(new PacketPlayOutBlockChange(this.world, blockposition));
		}
	}

	private static final MethodHandle fFieldGetter = Utils.getFieldGetter(PlayerInteractManager.class, "f");
	private BlockPosition getLastInteractBlockPosition() {
		try {
			return (BlockPosition) fFieldGetter.invokeExact((PlayerInteractManager) this);
		} catch (Throwable t) {
			SneakyThrow.sneaky(t);
		}
		return null;
	}

	private static final MethodHandle dFieldSetter = Utils.getFieldSetter(PlayerInteractManager.class, "d");
	private void setBreakingStateValue(boolean value) {
		try {
			dFieldSetter.invokeExact((PlayerInteractManager) this, value);
		} catch (Throwable t) {
			SneakyThrow.sneaky(t);
		}
	}

}
