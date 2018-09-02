package protocolsupport.zplatform.impl.spigot.entitytracker;

import net.minecraft.server.v1_13_R2.BlockPosition;
import net.minecraft.server.v1_13_R2.Entity;
import net.minecraft.server.v1_13_R2.EntityHuman;
import net.minecraft.server.v1_13_R2.EntityPlayer;
import net.minecraft.server.v1_13_R2.IBlockAccess;
import net.minecraft.server.v1_13_R2.IBlockData;
import net.minecraft.server.v1_13_R2.IWorldAccess;
import net.minecraft.server.v1_13_R2.PacketPlayOutBlockBreakAnimation;
import net.minecraft.server.v1_13_R2.ParticleParam;
import net.minecraft.server.v1_13_R2.SoundCategory;
import net.minecraft.server.v1_13_R2.SoundEffect;
import net.minecraft.server.v1_13_R2.WorldServer;
import protocolsupport.api.ProtocolType;
import protocolsupport.protocol.ConnectionImpl;

//TODO Check if this still operates... :F
public class SpigotEntityTrackerBlock implements IWorldAccess {

	private final WorldServer worldserver;

	public SpigotEntityTrackerBlock(WorldServer worldserver) {
		this.worldserver = worldserver;
	}

	@Override
	public void a(BlockPosition arg0) {
	}

	@Override
	public void a(Entity arg0) {
	}

	@Override
	public void a(SoundEffect arg0, BlockPosition arg1) {
	}

	@Override
	public void a(int arg0, BlockPosition arg1, int arg2) {
	}

	@Override
	public void a(EntityHuman arg0, int arg1, BlockPosition arg2, int arg3) {
	}

	@Override
	public void a(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
	}

	@Override
	public void a(EntityHuman arg0, SoundEffect arg1, SoundCategory arg2, double arg3, double arg4, double arg5,
			float arg6, float arg7) {
	}

	@Override
	public void b(Entity arg0) {
	}

	@Override // Block break. We hook into this notifier to also update PE blocks.
	public void b(int id, BlockPosition position, int progress) {
		worldserver.players.stream()
			.filter(he -> ((he != null) && (he.getId() == id) && (he instanceof EntityPlayer))).map(he -> (EntityPlayer) he)
			.forEach(player -> {
				ConnectionImpl connection = ConnectionImpl.getFromChannel(player.playerConnection.networkManager.channel);
				if ((connection != null) && (connection.getVersion().getProtocolType() == ProtocolType.PE)) {
					player.playerConnection.sendPacket(new PacketPlayOutBlockBreakAnimation(id, position, progress));
				}
			}
		);
	}

	@Override
	public void a(IBlockAccess arg0, BlockPosition arg1, IBlockData arg2, IBlockData arg3, int arg4) {
	}

	@Override
	public void a(ParticleParam arg0, boolean arg1, double arg2, double arg3, double arg4, double arg5, double arg6,
			double arg7) {
	}

	@Override
	public void a(ParticleParam arg0, boolean arg1, boolean arg2, double arg3, double arg4, double arg5, double arg6,
			double arg7, double arg8) {
	}

}
