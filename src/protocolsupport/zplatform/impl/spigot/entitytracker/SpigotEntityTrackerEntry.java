package protocolsupport.zplatform.impl.spigot.entitytracker;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.util.Vector;
import org.spigotmc.AsyncCatcher;

import net.minecraft.server.v1_12_R1.AttributeInstance;
import net.minecraft.server.v1_12_R1.AttributeMapServer;
import net.minecraft.server.v1_12_R1.Block;
import net.minecraft.server.v1_12_R1.BlockPosition;
import net.minecraft.server.v1_12_R1.Entity;
import net.minecraft.server.v1_12_R1.EntityAreaEffectCloud;
import net.minecraft.server.v1_12_R1.EntityArmorStand;
import net.minecraft.server.v1_12_R1.EntityArrow;
import net.minecraft.server.v1_12_R1.EntityBoat;
import net.minecraft.server.v1_12_R1.EntityDragonFireball;
import net.minecraft.server.v1_12_R1.EntityEgg;
import net.minecraft.server.v1_12_R1.EntityEnderCrystal;
import net.minecraft.server.v1_12_R1.EntityEnderPearl;
import net.minecraft.server.v1_12_R1.EntityEnderSignal;
import net.minecraft.server.v1_12_R1.EntityEvokerFangs;
import net.minecraft.server.v1_12_R1.EntityExperienceOrb;
import net.minecraft.server.v1_12_R1.EntityFallingBlock;
import net.minecraft.server.v1_12_R1.EntityFireball;
import net.minecraft.server.v1_12_R1.EntityFireworks;
import net.minecraft.server.v1_12_R1.EntityFishingHook;
import net.minecraft.server.v1_12_R1.EntityHuman;
import net.minecraft.server.v1_12_R1.EntityItem;
import net.minecraft.server.v1_12_R1.EntityItemFrame;
import net.minecraft.server.v1_12_R1.EntityLeash;
import net.minecraft.server.v1_12_R1.EntityLiving;
import net.minecraft.server.v1_12_R1.EntityLlamaSpit;
import net.minecraft.server.v1_12_R1.EntityMinecartAbstract;
import net.minecraft.server.v1_12_R1.EntityPainting;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.EntityPotion;
import net.minecraft.server.v1_12_R1.EntityShulkerBullet;
import net.minecraft.server.v1_12_R1.EntitySmallFireball;
import net.minecraft.server.v1_12_R1.EntitySnowball;
import net.minecraft.server.v1_12_R1.EntitySpectralArrow;
import net.minecraft.server.v1_12_R1.EntityTNTPrimed;
import net.minecraft.server.v1_12_R1.EntityThrownExpBottle;
import net.minecraft.server.v1_12_R1.EntityTippedArrow;
import net.minecraft.server.v1_12_R1.EntityTrackerEntry;
import net.minecraft.server.v1_12_R1.EntityTypes;
import net.minecraft.server.v1_12_R1.EntityWitherSkull;
import net.minecraft.server.v1_12_R1.EnumItemSlot;
import net.minecraft.server.v1_12_R1.IAnimal;
import net.minecraft.server.v1_12_R1.ItemStack;
import net.minecraft.server.v1_12_R1.ItemWorldMap;
import net.minecraft.server.v1_12_R1.Items;
import net.minecraft.server.v1_12_R1.MathHelper;
import net.minecraft.server.v1_12_R1.MobEffect;
import net.minecraft.server.v1_12_R1.Packet;
import net.minecraft.server.v1_12_R1.PacketPlayOutBed;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntity;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityEffect;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityEquipment;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityVelocity;
import net.minecraft.server.v1_12_R1.PacketPlayOutMount;
import net.minecraft.server.v1_12_R1.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_12_R1.PacketPlayOutSpawnEntity;
import net.minecraft.server.v1_12_R1.PacketPlayOutSpawnEntityExperienceOrb;
import net.minecraft.server.v1_12_R1.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_12_R1.PacketPlayOutSpawnEntityPainting;
import net.minecraft.server.v1_12_R1.PacketPlayOutUpdateAttributes;
import net.minecraft.server.v1_12_R1.WorldMap;
import protocolsupport.utils.CachedInstanceOfChain;

public class SpigotEntityTrackerEntry extends EntityTrackerEntry {

	protected static final boolean paperTrackedPlayersMapPresent = checkPaperTrackedPlayersMap();
	protected static final boolean checkPaperTrackedPlayersMap() {
		try {
			EntityTrackerEntry.class.getDeclaredField("trackedPlayerMap");
			return true;
		} catch (NoSuchFieldException | SecurityException e) {
			return false;
		}
	}

	protected final Entity entity;
	protected final Set<AttributeInstance> attributes;
	protected final int trackRange;
	protected final int updateInterval;
	protected final boolean updateVelocity;

	protected int viewDistance;

	protected double lastScanLocX;
	protected double lastScanLocY;
	protected double lastScanLocZ;

	protected double lastLocX;
	protected double lastLocY;
	protected double lastLocZ;
	protected float lastYaw;
	protected float lastPitch;
	protected float lastHeadYaw;
	protected double lastMotX;
	protected double lastMotY;
	protected double lastMotZ;
	protected List<Entity> lastPassengers = Collections.emptyList();

	public SpigotEntityTrackerEntry(Entity entity, int trackRange, int viewDistance, int updateInterval, boolean updateVelocity) {
		super(entity, trackRange, viewDistance, updateInterval, updateVelocity);
		this.entity = entity;
		if (entity instanceof EntityLiving) {
			this.attributes = ((AttributeMapServer) ((EntityLiving) entity).getAttributeMap()).getAttributes();
		} else {
			this.attributes = Collections.emptySet();
		}
		this.trackRange = trackRange;
		this.viewDistance = viewDistance;
		this.updateInterval = updateInterval;
		this.updateVelocity = updateVelocity;
		this.lastScanLocX = entity.locX;
		this.lastScanLocY = entity.locY;
		this.lastScanLocZ = entity.locZ;
	}

	@Override
	public boolean equals(Object object) {
		if (object == null) {
			return false;
		}
		if (!object.getClass().equals(this.getClass())) {
			return false;
		}
		return (((SpigotEntityTrackerEntry) object).entity.getId() == entity.getId());
	}

	@Override
	public int hashCode() {
		return entity.getId();
	}

	protected void updateRotationIfChanged() {
		float eYaw = entity.yaw;
		float ePitch = entity.pitch;
		if (
			Math.abs(eYaw - lastYaw) >= 1 ||
			Math.abs(ePitch - lastPitch) >= 1
		) {
			lastYaw = eYaw;
			lastPitch = ePitch;
			broadcast(new PacketPlayOutEntity.PacketPlayOutEntityLook(
				entity.getId(),
				(byte) MathHelper.d((eYaw * 256.0f) / 360.0f),
				(byte) MathHelper.d((ePitch * 256.0f) / 360.0f),
				entity.onGround
			));
		}
	}

	@Override
	public void track(List<EntityHuman> worldPlayers) {
		b = false;
		if (entity.d(lastScanLocX, lastScanLocY, lastScanLocZ) > 16.0) {
			lastScanLocX = entity.locX;
			lastScanLocY = entity.locY;
			lastScanLocZ = entity.locZ;
			b = true;
			scanPlayers(worldPlayers);
		}
		List<Entity> passengers = entity.bF();
		if (!passengers.equals(lastPassengers)) {
			lastPassengers = passengers;
			broadcastIncludingSelf(new PacketPlayOutMount(entity));
		}
		if (((a % 10) == 0) && entity instanceof EntityItemFrame) {
			EntityItemFrame frame = (EntityItemFrame) entity;
			ItemStack itemstack = frame.getItem();
			if (itemstack.getItem() instanceof ItemWorldMap) {
				WorldMap worldmap = Items.FILLED_MAP.getSavedMap(itemstack, entity.world);
				for (EntityHuman entityhuman : trackedPlayers) {
					EntityPlayer entityplayer = (EntityPlayer) entityhuman;
					worldmap.a(entityplayer, itemstack);
					Packet<?> packet = Items.FILLED_MAP.a(itemstack, entity.world, entityplayer);
					if (packet != null) {
						entityplayer.playerConnection.sendPacket(packet);
					}
				}
			}
		}
		if (((a > 0) && (a % updateInterval) == 0) || entity.impulse) {
			entity.impulse = false;
			if (entity.isPassenger()) {
				updateRotationIfChanged();
			} else {
				if (
					Math.abs(entity.locX - lastLocX) >= 0.03125D ||
					Math.abs(entity.locY - lastLocY) >= 0.015625D ||
					Math.abs(entity.locZ - lastLocZ) >= 0.03125D
				) {
					lastLocX = entity.locX;
					lastLocY = entity.locY;
					lastLocZ = entity.locZ;
					broadcast(new PacketPlayOutEntityTeleport(entity));
				} else {
					updateRotationIfChanged();
				}
				if (updateVelocity) {
					double diffMotX = entity.motX - lastMotX;
					double diffMotY = entity.motY - lastMotY;
					double diffMotZ = entity.motZ - lastMotZ;
					double diffMot = (diffMotX * diffMotX) + (diffMotY * diffMotY) + (diffMotZ * diffMotZ);
					if ((diffMot > 4.0E-4) || ((diffMot > 0.0) && (entity.motX == 0.0) && (entity.motY == 0.0) && (entity.motZ == 0.0))) {
						lastMotX = entity.motX;
						lastMotY = entity.motY;
						lastMotZ = entity.motZ;
						broadcast(new PacketPlayOutEntityVelocity(entity.getId(), lastMotX, lastMotY, lastMotZ));
					}
				}
			}
			float eHeadYaw = entity.getHeadRotation();
			if (Math.abs(eHeadYaw - lastHeadYaw) >= 1) {
				lastHeadYaw = eHeadYaw;
				broadcast(new PacketPlayOutEntityHeadRotation(entity, (byte) MathHelper.d((eHeadYaw * 256.0f) / 360.0f)));
			}
		}
		if (entity.getDataWatcher().a()) {
			broadcastIncludingSelf(new PacketPlayOutEntityMetadata(entity.getId(), entity.getDataWatcher(), false));
		}
		if (!attributes.isEmpty()) {
			if (entity instanceof EntityPlayer) {
				((EntityPlayer) this.entity).getBukkitEntity().injectScaledMaxHealth(attributes, false);
			}
			broadcastIncludingSelf(new PacketPlayOutUpdateAttributes(entity.getId(), attributes));
			attributes.clear();
		}
		++this.a;
		if (entity.velocityChanged) {
			boolean cancelled = false;
			if (entity instanceof EntityPlayer) {
				Player player = (Player) this.entity.getBukkitEntity();
				Vector velocity = player.getVelocity();
				PlayerVelocityEvent event = new PlayerVelocityEvent(player, velocity.clone());
				Bukkit.getPluginManager().callEvent(event);
				if (event.isCancelled()) {
					cancelled = true;
				} else if (!velocity.equals(event.getVelocity())) {
					player.setVelocity(event.getVelocity());
				}
			}
			if (!cancelled) {
				broadcastIncludingSelf(new PacketPlayOutEntityVelocity(this.entity));
			}
			entity.velocityChanged = false;
		}
	}

	@Override
	public void a(final EntityPlayer entityplayer) {
		if (removeTrackedPlayer(entityplayer)) {
			entity.c(entityplayer);
			entityplayer.c(entity);
		}
	}

	@Override
	public void updatePlayer(EntityPlayer entityplayer) {
		AsyncCatcher.catchOp("player tracker update");
		if (entityplayer != entity) {
			if (c(entityplayer)) {
				if (!trackedPlayers.contains(entityplayer) && (canPlayerSeeTrackerChunk(entityplayer) || entity.attachedToPlayer)) {
					if (entity instanceof EntityPlayer) {
						Player player = ((EntityPlayer) entity).getBukkitEntity();
						if (!entityplayer.getBukkitEntity().canSee(player)) {
							return;
						}
					}
					entityplayer.d(entity);
					addTrackedPlayer(entityplayer);
					Packet<?> spawnPacket = createSpawnPacket();
					lastLocX = entity.locX;
					lastLocY = entity.locY;
					lastLocZ = entity.locZ;
					lastYaw = entity.yaw;
					lastPitch = entity.pitch;
					entityplayer.playerConnection.sendPacket(spawnPacket);
					lastHeadYaw = entity.getHeadRotation();
					broadcast(new PacketPlayOutEntityHeadRotation(entity, (byte) MathHelper.d((lastHeadYaw * 256.0f) / 360.0f)));
					if (!entity.getDataWatcher().d()) {
						entityplayer.playerConnection.sendPacket(new PacketPlayOutEntityMetadata(entity.getId(), entity.getDataWatcher(), true));
					}
					if (entity instanceof EntityLiving) {
						EntityLiving entityliving = (EntityLiving) entity;
						Collection<AttributeInstance> updateAttrs = ((AttributeMapServer) entityliving.getAttributeMap()).c();
						if (entity.getId() == entityplayer.getId()) {
							((EntityPlayer) entity).getBukkitEntity().injectScaledMaxHealth(updateAttrs, false);
						}
						if (!updateAttrs.isEmpty()) {
							entityplayer.playerConnection.sendPacket(new PacketPlayOutUpdateAttributes(entity.getId(), updateAttrs));
						}
						for (EnumItemSlot enumitemslot : EnumItemSlot.values()) {
							ItemStack itemstack = entityliving.getEquipment(enumitemslot);
							if (!itemstack.isEmpty()) {
								entityplayer.playerConnection.sendPacket(new PacketPlayOutEntityEquipment(entity.getId(), enumitemslot, itemstack));
							}
						}
						for (MobEffect mobeffect : entityliving.getEffects()) {
							entityplayer.playerConnection.sendPacket(new PacketPlayOutEntityEffect(entity.getId(), mobeffect));
						}
					}
					if (updateVelocity && !(spawnPacket instanceof PacketPlayOutSpawnEntityLiving)) {
						lastMotX = entity.motX;
						lastMotY = entity.motY;
						lastMotZ = entity.motZ;
						entityplayer.playerConnection.sendPacket(new PacketPlayOutEntityVelocity(entity.getId(), entity.motX, entity.motY, entity.motZ));
					}
					if (entity instanceof EntityHuman) {
						EntityHuman entityhuman = (EntityHuman) entity;
						if (entityhuman.isSleeping()) {
							entityplayer.playerConnection.sendPacket(new PacketPlayOutBed(entityhuman, new BlockPosition(entity)));
						}
					}
					if (!entity.bF().isEmpty()) {
						entityplayer.playerConnection.sendPacket(new PacketPlayOutMount(entity));
					}
					if (entity.isPassenger()) {
						entityplayer.playerConnection.sendPacket(new PacketPlayOutMount(entity.bJ()));
					}
					entity.b(entityplayer);
					entityplayer.d(entity);
				}
			} else {
				a(entityplayer);
			}
		}
	}

	protected void addTrackedPlayer(EntityPlayer entityplayer) {
		if (paperTrackedPlayersMapPresent) {
			trackedPlayerMap.put(entityplayer, Boolean.TRUE);
		} else {
			trackedPlayers.add(entityplayer);
		}
	}

	protected boolean removeTrackedPlayer(EntityPlayer entityplayer) {
		if (paperTrackedPlayersMapPresent) {
			return trackedPlayerMap.remove(entityplayer) != null;
		} else {
			return trackedPlayers.remove(entityplayer);
		}
	}

	@Override
	public boolean c(EntityPlayer entityplayer) {
		double diffX = entityplayer.locX - entity.locX;
		double diffZ = entityplayer.locZ - entity.locZ;
		int lTrackRange = Math.min(trackRange, viewDistance);
		return (diffX >= -lTrackRange) && (diffX <= lTrackRange) && (diffZ >= -lTrackRange) && (diffZ <= lTrackRange) && entity.a(entityplayer);
	}

	protected boolean canPlayerSeeTrackerChunk(EntityPlayer entityplayer) {
		return entityplayer.x().getPlayerChunkMap().a(entityplayer, entity.ab, entity.ad);
	}

	protected static final CachedInstanceOfChain<Function<Entity, Packet<?>>> createSpawnPacketMethods = new CachedInstanceOfChain<>();
	static {
		createSpawnPacketMethods.setKnownPath(EntityPlayer.class, entity -> new PacketPlayOutNamedEntitySpawn((EntityHuman) entity));
		createSpawnPacketMethods.setKnownPath(IAnimal.class, entity -> new PacketPlayOutSpawnEntityLiving((EntityLiving) entity));
		createSpawnPacketMethods.setKnownPath(EntityPainting.class, entity -> new PacketPlayOutSpawnEntityPainting((EntityPainting) entity));
		createSpawnPacketMethods.setKnownPath(EntityItem.class, entity -> new PacketPlayOutSpawnEntity(entity, 2, 1));
		createSpawnPacketMethods.setKnownPath(EntityMinecartAbstract.class, entity -> {
			EntityMinecartAbstract entityminecartabstract = (EntityMinecartAbstract) entity;
			return new PacketPlayOutSpawnEntity(entityminecartabstract, 10, entityminecartabstract.v().a());
		});
		createSpawnPacketMethods.setKnownPath(EntityBoat.class, entity -> new PacketPlayOutSpawnEntity(entity, 1));
		createSpawnPacketMethods.setKnownPath(EntityExperienceOrb.class, entity -> new PacketPlayOutSpawnEntityExperienceOrb((EntityExperienceOrb) entity));
		createSpawnPacketMethods.setKnownPath(EntityFishingHook.class, entity -> {
			EntityHuman entityhuman = ((EntityFishingHook) entity).l();
			return new PacketPlayOutSpawnEntity(entity, 90, (entityhuman == null) ? entity.getId() : entityhuman.getId());
		});
		createSpawnPacketMethods.setKnownPath(EntitySpectralArrow.class, entity -> {
			Entity shooter = ((EntitySpectralArrow) entity).shooter;
			return new PacketPlayOutSpawnEntity(entity, 91, 1 + ((shooter == null) ? entity.getId() : shooter.getId()));
		});
		createSpawnPacketMethods.setKnownPath(EntityTippedArrow.class, entity -> {
			Entity shooter = ((EntityArrow) entity).shooter;
			return new PacketPlayOutSpawnEntity(entity, 60, 1 + ((shooter == null) ? entity.getId() : shooter.getId()));
		});
		createSpawnPacketMethods.setKnownPath(EntitySnowball.class, entity -> new PacketPlayOutSpawnEntity(entity, 61));
		createSpawnPacketMethods.setKnownPath(EntityLlamaSpit.class, entity -> new PacketPlayOutSpawnEntity(entity, 68));
		createSpawnPacketMethods.setKnownPath(EntityPotion.class, entity -> new PacketPlayOutSpawnEntity(entity, 73));
		createSpawnPacketMethods.setKnownPath(EntityThrownExpBottle.class, entity -> new PacketPlayOutSpawnEntity(entity, 75));
		createSpawnPacketMethods.setKnownPath(EntityEnderPearl.class, entity -> new PacketPlayOutSpawnEntity(entity, 65));
		createSpawnPacketMethods.setKnownPath(EntityEnderSignal.class, entity -> new PacketPlayOutSpawnEntity(entity, 72));
		createSpawnPacketMethods.setKnownPath(EntityFireworks.class, entity -> new PacketPlayOutSpawnEntity(entity, 76));
		createSpawnPacketMethods.setKnownPath(EntityFireball.class, entity -> {
			EntityFireball entityfireball = (EntityFireball) entity;
			byte objectTypeId = 63;
			if (entityfireball instanceof EntitySmallFireball) {
				objectTypeId = 64;
			} else if (entityfireball instanceof EntityDragonFireball) {
				objectTypeId = 93;
			} else if (entityfireball instanceof EntityWitherSkull) {
				objectTypeId = 66;
			}
			PacketPlayOutSpawnEntity packet = null;
			if (entityfireball.shooter != null) {
				packet = new PacketPlayOutSpawnEntity(entityfireball, objectTypeId, entityfireball.shooter.getId());
			} else {
				packet = new PacketPlayOutSpawnEntity(entityfireball, objectTypeId, 0);
			}
			packet.a((int) (entityfireball.dirX * 8000.0));
			packet.b((int) (entityfireball.dirY * 8000.0));
			packet.c((int) (entityfireball.dirZ * 8000.0));
			return packet;
		});
		createSpawnPacketMethods.setKnownPath(EntityShulkerBullet.class, entity -> {
			PacketPlayOutSpawnEntity packet = new PacketPlayOutSpawnEntity(entity, 67, 0);
			packet.a((int) (entity.motX * 8000.0));
			packet.b((int) (entity.motY * 8000.0));
			packet.c((int) (entity.motZ * 8000.0));
			return packet;
		});
		createSpawnPacketMethods.setKnownPath(EntityEgg.class, entity -> new PacketPlayOutSpawnEntity(entity, 62));
		createSpawnPacketMethods.setKnownPath(EntityEvokerFangs.class, entity -> new PacketPlayOutSpawnEntity(entity, 79));
		createSpawnPacketMethods.setKnownPath(EntityTNTPrimed.class, entity -> new PacketPlayOutSpawnEntity(entity, 50));
		createSpawnPacketMethods.setKnownPath(EntityEnderCrystal.class, entity -> new PacketPlayOutSpawnEntity(entity, 51));
		createSpawnPacketMethods.setKnownPath(EntityFallingBlock.class, entity -> {
			EntityFallingBlock entityfallingblock = (EntityFallingBlock) entity;
			return new PacketPlayOutSpawnEntity(entity, 70, Block.getCombinedId(entityfallingblock.getBlock()));
		});
		createSpawnPacketMethods.setKnownPath(EntityArmorStand.class, entity -> new PacketPlayOutSpawnEntity(entity, 78));
		createSpawnPacketMethods.setKnownPath(EntityItemFrame.class, entity -> {
			EntityItemFrame entityitemframe = (EntityItemFrame) entity;
			return new PacketPlayOutSpawnEntity(entity, 71, entityitemframe.direction.get2DRotationValue(), entityitemframe.getBlockPosition());
		});
		createSpawnPacketMethods.setKnownPath(EntityLeash.class, entity -> {
			EntityLeash entityleash = (EntityLeash) entity;
			return new PacketPlayOutSpawnEntity(entity, 77, 0, entityleash.getBlockPosition());
		});
		createSpawnPacketMethods.setKnownPath(EntityAreaEffectCloud.class, entity -> new PacketPlayOutSpawnEntity(entity, 3));
		EntityTypes.b.iterator().forEachRemaining(createSpawnPacketMethods::selectPath);
	}

	protected Packet<?> createSpawnPacket() {
		if (entity.dead) {
			return null;
		}
		Function<Entity, Packet<?>> createSpawnPacketMethod = createSpawnPacketMethods.selectPath(entity.getClass());
		if (createSpawnPacketMethod == null) {
			throw new IllegalArgumentException("Don't know how to add " + entity.getClass() + "!");
		}
		return createSpawnPacketMethod.apply(entity);
	}

	@Override
	public void a(int i) {
		viewDistance = i;
	}

	@Override
	public void c() {
	}

}