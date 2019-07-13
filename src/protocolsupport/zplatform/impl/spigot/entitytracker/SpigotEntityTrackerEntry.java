package protocolsupport.zplatform.impl.spigot.entitytracker;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.util.Vector;

import net.minecraft.server.v1_14_R1.AttributeInstance;
import net.minecraft.server.v1_14_R1.AttributeMapServer;
import net.minecraft.server.v1_14_R1.DataWatcher;
import net.minecraft.server.v1_14_R1.Entity;
import net.minecraft.server.v1_14_R1.EntityArrow;
import net.minecraft.server.v1_14_R1.EntityItemFrame;
import net.minecraft.server.v1_14_R1.EntityLiving;
import net.minecraft.server.v1_14_R1.EntityPlayer;
import net.minecraft.server.v1_14_R1.EntityTrackerEntry;
import net.minecraft.server.v1_14_R1.EnumItemSlot;
import net.minecraft.server.v1_14_R1.ItemStack;
import net.minecraft.server.v1_14_R1.ItemWorldMap;
import net.minecraft.server.v1_14_R1.MathHelper;
import net.minecraft.server.v1_14_R1.MobEffect;
import net.minecraft.server.v1_14_R1.Packet;
import net.minecraft.server.v1_14_R1.PacketPlayOutEntity;
import net.minecraft.server.v1_14_R1.PacketPlayOutEntity.PacketPlayOutEntityLook;
import net.minecraft.server.v1_14_R1.PacketPlayOutEntityEffect;
import net.minecraft.server.v1_14_R1.PacketPlayOutEntityEquipment;
import net.minecraft.server.v1_14_R1.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_14_R1.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_14_R1.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_14_R1.PacketPlayOutEntityVelocity;
import net.minecraft.server.v1_14_R1.PacketPlayOutMount;
import net.minecraft.server.v1_14_R1.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_14_R1.PacketPlayOutUpdateAttributes;
import net.minecraft.server.v1_14_R1.PlayerConnection;
import net.minecraft.server.v1_14_R1.Vec3D;
import net.minecraft.server.v1_14_R1.WorldMap;
import net.minecraft.server.v1_14_R1.WorldServer;

public class SpigotEntityTrackerEntry extends EntityTrackerEntry {

	private final WorldServer world;
	private final Entity tracker;
	private final EntityLiving trackerLiving;
	private final AttributeMapServer trackerAttributeMap;
	private final int updateInterval;
	private final boolean alwaysUpdateVelocity;
	private final Consumer<Packet<?>> broadcast;
	private final Set<EntityPlayer> trackedPlayers;
	private long trackerX;
	private long trackerY;
	private long trackerZ;
	private int trackerYaw;
	private int trackerPitch;
	private int trackerHeadYaw;
	private Vec3D trackerVelocity;
	private int trackerTicks;
	private List<Entity> trackerPassengers;

	public SpigotEntityTrackerEntry(WorldServer worldserver, Entity entity, int updateInterval, boolean alwaysUpdateVelocity, Consumer<Packet<?>> consumer, Set<EntityPlayer> trackedPlayers) {
		super(worldserver, entity, updateInterval, alwaysUpdateVelocity, consumer, trackedPlayers);
		this.world = worldserver;
		this.tracker = entity;
		this.trackerLiving = (tracker instanceof EntityLiving) ? (EntityLiving) tracker : null;
		this.trackerAttributeMap = trackerLiving != null ? (AttributeMapServer) trackerLiving.getAttributeMap() : null;
		this.trackedPlayers = trackedPlayers;
		this.trackerVelocity = Vec3D.a;
		this.trackerPassengers = Collections.emptyList();
		this.broadcast = consumer;
		this.updateInterval = updateInterval;
		this.alwaysUpdateVelocity = alwaysUpdateVelocity;
		this.storeTrackerLocation();
		this.trackerYaw = MathHelper.d((entity.yaw * 256.0F) / 360.0F);
		this.trackerPitch = MathHelper.d((entity.pitch * 256.0F) / 360.0F);
		this.trackerHeadYaw = MathHelper.d((entity.getHeadRotation() * 256.0F) / 360.0F);
	}

	private void updateRotationIfChanged() {
		byte yaw = (byte) MathHelper.d((this.tracker.yaw * 256.0F) / 360.0F);
		byte pitch = (byte) MathHelper.d((this.tracker.pitch * 256.0F) / 360.0F);
		if ((Math.abs(yaw - this.trackerYaw) >= 1) || (Math.abs(pitch - this.trackerPitch) >= 1)) {
			this.broadcast.accept(new PacketPlayOutEntityLook(this.tracker.getId(), yaw, pitch, this.tracker.onGround));
			this.trackerYaw = yaw;
			this.trackerPitch = pitch;
		}
	}

	@Override
	public void a() {
		List<Entity> passengers = this.tracker.getPassengers();
		if (!passengers.equals(this.trackerPassengers)) {
			this.trackerPassengers = passengers;
			this.broadcastIncludingSelf(new PacketPlayOutMount(this.tracker));
		}

		if (this.tracker instanceof EntityItemFrame) {
			EntityItemFrame cancelled = (EntityItemFrame) this.tracker;
			ItemStack itemstack = cancelled.getItem();
			if (((this.trackerTicks % 10) == 0) && (itemstack.getItem() instanceof ItemWorldMap)) {
				WorldMap worldmap = ItemWorldMap.getSavedMap(itemstack, this.world);
				Iterator<EntityPlayer> trackedPlayer = this.trackedPlayers.iterator();
				while (trackedPlayer.hasNext()) {
					EntityPlayer player = trackedPlayer.next();
					worldmap.a(player, itemstack);
					Packet<?> packet = ((ItemWorldMap) itemstack.getItem()).a(itemstack, this.world, player);
					if (packet != null) {
						player.playerConnection.sendPacket(packet);
					}
				}
			}

			this.updateMetadataAndAttributes();
		}

		if (((this.trackerTicks % this.updateInterval) == 0) || this.tracker.impulse || this.tracker.getDataWatcher().a()) {
			if (this.tracker.isPassenger()) {
				this.updateRotationIfChanged();
				this.storeTrackerLocation();
			} else {
				Vec3D locationDistance = (new Vec3D(this.tracker.locX, this.tracker.locY, this.tracker.locZ)).d(PacketPlayOutEntity.a(this.trackerX, this.trackerY, this.trackerZ));
				boolean needsLocationUpdate = locationDistance.g() >= 7.62939453125E-6D;
				if (!needsLocationUpdate) {
					this.updateRotationIfChanged();
				} else if ((this.trackerTicks > 0) || (this.tracker instanceof EntityArrow)) {
					this.broadcast.accept(new PacketPlayOutEntityTeleport(this.tracker));
					this.storeTrackerLocation();
				}

				if ((this.alwaysUpdateVelocity || this.tracker.impulse || ((trackerLiving != null) && trackerLiving.isGliding())) && (this.trackerTicks > 0)) {
					Vec3D velocity = this.tracker.getMot();
					double velocityDiff = velocity.distanceSquared(this.trackerVelocity);
					if ((velocityDiff > 1.0E-7D) || ((velocityDiff > 0.0D) && (velocity.g() == 0.0D))) {
						this.trackerVelocity = velocity;
						this.broadcast.accept(new PacketPlayOutEntityVelocity(this.tracker.getId(), this.trackerVelocity));
					}
				}
			}

			this.updateMetadataAndAttributes();

			byte headYaw = (byte) MathHelper.d((this.tracker.getHeadRotation() * 256.0F) / 360.0F);
			if (Math.abs(headYaw - this.trackerHeadYaw) >= 1) {
				this.broadcast.accept(new PacketPlayOutEntityHeadRotation(this.tracker, headYaw));
				this.trackerHeadYaw = headYaw;
			}

			this.tracker.impulse = false;
		}

		++this.trackerTicks;

		if (this.tracker.velocityChanged) {
			boolean doNotChangeVelocity = false;
			if (this.tracker instanceof EntityPlayer) {
				Player player = (Player) this.tracker.getBukkitEntity();
				Vector velocity = player.getVelocity();
				PlayerVelocityEvent event = new PlayerVelocityEvent(player, velocity.clone());
				this.tracker.world.getServer().getPluginManager().callEvent(event);
				if (event.isCancelled()) {
					doNotChangeVelocity = true;
				} else if (!velocity.equals(event.getVelocity())) {
					player.setVelocity(event.getVelocity());
				}
			}

			if (!doNotChangeVelocity) {
				this.broadcastIncludingSelf(new PacketPlayOutEntityVelocity(this.tracker));
			}

			this.tracker.velocityChanged = false;
		}

	}

	@Override
	public void a(EntityPlayer entityplayer) {
		this.tracker.c(entityplayer);
		entityplayer.c(this.tracker);
	}

	@Override
	public void b(EntityPlayer entityplayer) {
		PlayerConnection playerconnection = entityplayer.playerConnection;
		this.a(playerconnection::sendPacket, entityplayer);
		this.tracker.b(entityplayer);
		entityplayer.d(this.tracker);
	}

	@Override
	public void a(Consumer<Packet<?>> consumer, EntityPlayer entityplayer) {
		if (!this.tracker.dead) {
			Packet<?> spawnPacket = this.tracker.N();
			this.trackerHeadYaw = MathHelper.d((this.tracker.getHeadRotation() * 256.0F) / 360.0F);
			consumer.accept(spawnPacket);
			consumer.accept(new PacketPlayOutEntityHeadRotation(this.tracker, (byte) this.trackerHeadYaw));

			if (!this.tracker.getDataWatcher().d()) {
				consumer.accept(new PacketPlayOutEntityMetadata(this.tracker.getId(), this.tracker.getDataWatcher(), true));
			}

			boolean updateVelocityVelocity = this.alwaysUpdateVelocity;

			if (trackerLiving != null) {
				if (trackerAttributeMap != null) {
					Collection<AttributeInstance> updateAttributes = trackerAttributeMap.c();
					if (this.tracker.getId() == entityplayer.getId()) {
						((EntityPlayer) this.trackerLiving).getBukkitEntity().injectScaledMaxHealth(updateAttributes, false);
					}

					if (!updateAttributes.isEmpty()) {
						consumer.accept(new PacketPlayOutUpdateAttributes(this.trackerLiving.getId(), updateAttributes));
					}
				}

				for (MobEffect potioneffect : trackerLiving.getEffects()) {
					consumer.accept(new PacketPlayOutEntityEffect(this.trackerLiving.getId(), potioneffect));
				}

				for (EnumItemSlot enumitemslot : EnumItemSlot.values()) {
					ItemStack itemstack = trackerLiving.getEquipment(enumitemslot);
					if (!itemstack.isEmpty()) {
						consumer.accept(new PacketPlayOutEntityEquipment(this.trackerLiving.getId(), enumitemslot, itemstack));
					}
				}

				if (trackerLiving.isGliding()) {
					updateVelocityVelocity = true;
				}
			}

			this.trackerVelocity = this.tracker.getMot();
			if (updateVelocityVelocity && !(spawnPacket instanceof PacketPlayOutSpawnEntityLiving)) {
				consumer.accept(new PacketPlayOutEntityVelocity(this.tracker.getId(), this.trackerVelocity));
			}

			if (!this.tracker.getPassengers().isEmpty()) {
				consumer.accept(new PacketPlayOutMount(this.tracker));
			}

			if (this.tracker.isPassenger()) {
				consumer.accept(new PacketPlayOutMount(this.tracker.getVehicle()));
			}
		}
	}

	private void updateMetadataAndAttributes() {
		DataWatcher datawatcher = this.tracker.getDataWatcher();
		if (datawatcher.a()) {
			this.broadcastIncludingSelf(new PacketPlayOutEntityMetadata(this.tracker.getId(), datawatcher, false));
		}

		if (trackerAttributeMap != null) {
			Set<AttributeInstance> updateAttributes = trackerAttributeMap.getAttributes();
			if (!updateAttributes.isEmpty()) {
				if (this.tracker instanceof EntityPlayer) {
					((EntityPlayer) this.tracker).getBukkitEntity().injectScaledMaxHealth(updateAttributes, false);
				}

				this.broadcastIncludingSelf(new PacketPlayOutUpdateAttributes(this.tracker.getId(), updateAttributes));
			}
			updateAttributes.clear();
		}
	}

	@Override
	public Vec3D b() {
		return PacketPlayOutEntity.a(this.trackerX, this.trackerY, this.trackerZ);
	}

	private void storeTrackerLocation() {
		this.trackerX = PacketPlayOutEntity.a(this.tracker.locX);
		this.trackerY = PacketPlayOutEntity.a(this.tracker.locY);
		this.trackerZ = PacketPlayOutEntity.a(this.tracker.locZ);
	}

	private void broadcastIncludingSelf(Packet<?> packet) {
		this.broadcast.accept(packet);
		if (this.tracker instanceof EntityPlayer) {
			((EntityPlayer) this.tracker).playerConnection.sendPacket(packet);
		}
	}

}
