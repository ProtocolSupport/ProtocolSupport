package protocolsupport.protocol.packet;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_9_R1.event.CraftEventFactory;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerAnimationEvent;

import net.minecraft.server.v1_9_R1.AxisAlignedBB;
import net.minecraft.server.v1_9_R1.Entity;
import net.minecraft.server.v1_9_R1.EntityLiving;
import net.minecraft.server.v1_9_R1.EntityPlayer;
import net.minecraft.server.v1_9_R1.EnumHand;
import net.minecraft.server.v1_9_R1.MathHelper;
import net.minecraft.server.v1_9_R1.MinecraftServer;
import net.minecraft.server.v1_9_R1.MovingObjectPosition;
import net.minecraft.server.v1_9_R1.NetworkManager;
import net.minecraft.server.v1_9_R1.PacketPlayInArmAnimation;
import net.minecraft.server.v1_9_R1.PacketPlayInUseEntity;
import net.minecraft.server.v1_9_R1.PlayerConnection;
import net.minecraft.server.v1_9_R1.PlayerConnectionUtils;
import net.minecraft.server.v1_9_R1.Vec3D;
import net.minecraft.server.v1_9_R1.WorldSettings;

import protocolsupport.api.ProtocolSupportAPI;
import protocolsupport.api.ProtocolVersion;

public class ProtocolSupportPlayerConnection extends PlayerConnection {

	public ProtocolSupportPlayerConnection(MinecraftServer minecraftserver, NetworkManager networkmanager, EntityPlayer entityplayer) {
		super(minecraftserver, networkmanager, entityplayer);
	}

	@Override
	public void a(final PacketPlayInArmAnimation packetplayinarmanimation) {
		PlayerConnectionUtils.ensureMainThread(packetplayinarmanimation, this,  this.player.x());
		if (this.player.dead) {
			return;
		}
		this.player.resetIdleTimer();
		final float f1 = this.player.pitch;
		final float f2 = this.player.yaw;
		final double d0 = this.player.locX;
		final double d2 = this.player.locY + this.player.getHeadHeight();
		final double d3 = this.player.locZ;
		final Vec3D vec3d = new Vec3D(d0, d2, d3);
		final float f3 = MathHelper.cos(-f2 * 0.017453292f - 3.1415927f);
		final float f4 = MathHelper.sin(-f2 * 0.017453292f - 3.1415927f);
		final float f5 = -MathHelper.cos(-f1 * 0.017453292f);
		final float f6 = MathHelper.sin(-f1 * 0.017453292f);
		final float f7 = f4 * f5;
		final float f8 = f3 * f5;
		final double d4 = (this.player.playerInteractManager.getGameMode() == WorldSettings.EnumGamemode.CREATIVE) ? 5.0 : 4.5;
		final Vec3D vec3d2 = vec3d.add(f7 * d4, f6 * d4, f8 * d4);
		final MovingObjectPosition movingobjectposition = this.player.world.rayTrace(vec3d, vec3d2);
		if (movingobjectposition == null || movingobjectposition.type != MovingObjectPosition.EnumMovingObjectType.BLOCK) {
			CraftEventFactory.callPlayerInteractEvent(this.player, Action.LEFT_CLICK_AIR, this.player.inventory.getItemInHand(), EnumHand.MAIN_HAND);
		}
		final PlayerAnimationEvent event = new PlayerAnimationEvent(this.getPlayer());
		Bukkit.getPluginManager().callEvent(event);
		if (event.isCancelled()) {
			return;
		}
		if (ProtocolSupportAPI.getProtocolVersion(getPlayer()).isBefore(ProtocolVersion.MINECRAFT_1_9)) {
			if (!hasLivingEntityInLineOfSight(movingobjectposition)) {
				this.player.a(packetplayinarmanimation.a());
			}
		} else {
			this.player.a(packetplayinarmanimation.a());
		}
	}

	@Override
	public void a(final PacketPlayInUseEntity packet) {
		super.a(packet);
		if (ProtocolSupportAPI.getProtocolVersion(getPlayer()).isBefore(ProtocolVersion.MINECRAFT_1_9)) {
			final PlayerAnimationEvent event = new PlayerAnimationEvent(this.getPlayer());
			Bukkit.getPluginManager().callEvent(event);
			if (event.isCancelled()) {
				return;
			}
			this.player.a(packet.b());
		}
	}

	private boolean hasLivingEntityInLineOfSight(MovingObjectPosition existinghit) {
		Vec3D playerPosition = new Vec3D(player.locX, player.locY + player.getHeadHeight(), player.locZ);
		double reach = (this.player.playerInteractManager.getGameMode() == WorldSettings.EnumGamemode.CREATIVE) ? 6.0 : 4.5;

		Vec3D lookVect = player.f(1.0F);
		Vec3D rayTraceEnd = playerPosition.add(lookVect.x * reach, lookVect.y * reach, lookVect.z * reach);

		Entity entityInSight = null;
		List<Entity> entityList = player.world.getEntities(
			player,
			player
			.getBoundingBox().a(lookVect.x * reach, lookVect.y * reach, lookVect.z * reach)
			.grow(1.0F, 1.0F, 1.0F)
		);

		double reachLimit = reach;
		if (existinghit != null) {
			reachLimit = distance(playerPosition, existinghit.pos);
		}
		for (Entity entity : entityList) {
			AxisAlignedBB entitybb = entity.getBoundingBox().g(entity.aA());
			MovingObjectPosition intercept = entitybb.a(playerPosition, rayTraceEnd);
			if (entitybb.a(playerPosition)) {
				if (reachLimit >= 0.0D) {
					entityInSight = entity;
					reachLimit = 0.0D;
				}
			} else if (intercept != null) {
				double dist = distance(playerPosition, intercept.pos);

				if (dist < reachLimit || reachLimit == 0.0D) {
					if (entity == player.getVehicle()) {
						if (reachLimit == 0.0D) {
							entityInSight = entity;
						}
					} else {
						entityInSight = entity;
						reachLimit = dist;
					}
				}
			}
		}

		return entityInSight instanceof EntityLiving;
	}

	private double distance(Vec3D vec1, Vec3D vec2) {
		double xDiff = vec1.x - vec2.x;
		double yDiff = vec1.y - vec2.y;
		double zDiff = vec1.z - vec2.z;
		return Math.sqrt(xDiff * xDiff + yDiff * yDiff + zDiff * zDiff);
	}

}
