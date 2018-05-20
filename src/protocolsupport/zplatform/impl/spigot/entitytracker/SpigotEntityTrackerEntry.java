package protocolsupport.zplatform.impl.spigot.entitytracker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.util.Vector;
import org.spigotmc.AsyncCatcher;

import net.minecraft.server.v1_12_R1.AttributeInstance;
import net.minecraft.server.v1_12_R1.AttributeMapServer;
import net.minecraft.server.v1_12_R1.Block;
import net.minecraft.server.v1_12_R1.BlockPosition;
import net.minecraft.server.v1_12_R1.DataWatcher;
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

public class SpigotEntityTrackerEntry extends EntityTrackerEntry {

	private final Entity entity;
	private final int trackRange;
	private final int updateInterval;
	private final boolean updateVelocity;

	private int viewDistance;

	private int lastHeadYaw;
	private double lastSentMotX;
	private double lastSentMotY;
	private double lastSentMotZ;
	private List<Entity> lastPassengers = Collections.emptyList();

	public SpigotEntityTrackerEntry(Entity entity, int trackRange, int viewDistance, int updateInterval, boolean updateVelocity) {
		super(entity, trackRange, viewDistance, updateInterval, updateVelocity);
		this.entity = entity;
		this.trackRange = trackRange;
		this.viewDistance = viewDistance;
		this.updateInterval = updateInterval;
		this.updateVelocity = updateVelocity;
		this.lastHeadYaw = MathHelper.d((entity.getHeadRotation() * 256.0f) / 360.0f);
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

	private boolean shouldScanForce = true;
	private double lastScanLocX;
	private double lastScanLocY;
	private double lastScanLocZ;

	@Override
	public void track(List<EntityHuman> worldPlayers) {
		b = false;
		if (shouldScanForce || (entity.d(lastScanLocX, lastScanLocY, lastScanLocZ) > 16.0)) {
			shouldScanForce = true;
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
		if (entity instanceof EntityItemFrame) {
			EntityItemFrame frame = (EntityItemFrame) this.entity;
			ItemStack itemstack = frame.getItem();
			if (((a % 10) == 0) && (itemstack.getItem() instanceof ItemWorldMap)) {
				WorldMap worldmap = Items.FILLED_MAP.getSavedMap(itemstack, entity.world);
				for (EntityHuman entityhuman : trackedPlayers) {
					EntityPlayer entityplayer = (EntityPlayer) entityhuman;
					worldmap.a(entityplayer, itemstack);
					Packet<?> packet = Items.FILLED_MAP.a(itemstack, this.entity.world, entityplayer);
					if (packet != null) {
						entityplayer.playerConnection.sendPacket(packet);
					}
				}
			}
			this.updateMetadataAndAttributes();
		}
		if (((a % this.updateInterval) == 0) || entity.impulse || entity.getDataWatcher().a()) {
			if (entity.isPassenger()) {
				broadcast(new PacketPlayOutEntity.PacketPlayOutEntityLook(
					entity.getId(),
					(byte) MathHelper.d((entity.yaw * 256.0f) / 360.0f),
					(byte) MathHelper.d((entity.pitch * 256.0f) / 360.0f),
					this.entity.onGround)
				);
			} else {
				if ((a > 0) || (entity instanceof EntityArrow)) {
					if (entity instanceof EntityPlayer) {
						this.scanPlayers(new ArrayList<EntityHuman>(this.trackedPlayers));
					}
					this.c();
					broadcast(new PacketPlayOutEntityTeleport(this.entity));
				}
				boolean lUpdateVelocity = updateVelocity;
				if ((entity instanceof EntityLiving) && ((EntityLiving) entity).cP()) {
					lUpdateVelocity = true;
				}
				if ((a > 0) && lUpdateVelocity) {
					double diffMotX = entity.motX - lastSentMotX;
					double diffMotY = entity.motY - lastSentMotY;
					double diffMotZ = entity.motZ - lastSentMotZ;
					double diffMot = (diffMotX * diffMotX) + (diffMotY * diffMotY) + (diffMotZ * diffMotZ);
					if ((diffMot > 4.0E-4) || ((diffMot > 0.0) && (entity.motX == 0.0) && (entity.motY == 0.0) && (entity.motZ == 0.0))) {
						lastSentMotX = entity.motX;
						lastSentMotY = entity.motY;
						lastSentMotZ = entity.motZ;
						broadcast(new PacketPlayOutEntityVelocity(entity.getId(), lastSentMotX, lastSentMotY, lastSentMotZ));
					}
				}
			}
			updateMetadataAndAttributes();
			int currentHeadYaw = MathHelper.d((this.entity.getHeadRotation() * 256.0f) / 360.0f);
			if (Math.abs(currentHeadYaw - lastHeadYaw) >= 1) {
				lastHeadYaw = currentHeadYaw;
				broadcast(new PacketPlayOutEntityHeadRotation(entity, (byte) currentHeadYaw));
			}
			entity.impulse = false;
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
				this.broadcastIncludingSelf(new PacketPlayOutEntityVelocity(this.entity));
			}
			entity.velocityChanged = false;
		}
	}

	private void updateMetadataAndAttributes() {
		DataWatcher datawatcher = this.entity.getDataWatcher();
		if (datawatcher.a()) {
			broadcastIncludingSelf(new PacketPlayOutEntityMetadata(entity.getId(), datawatcher, false));
		}
		if (entity instanceof EntityLiving) {
			Set<AttributeInstance> updateAttrs = ((AttributeMapServer) ((EntityLiving) entity).getAttributeMap()).getAttributes();
			if (!updateAttrs.isEmpty()) {
				if (this.entity instanceof EntityPlayer) {
					((EntityPlayer) this.entity).getBukkitEntity().injectScaledMaxHealth(updateAttrs, false);
				}
				this.broadcastIncludingSelf(new PacketPlayOutUpdateAttributes(this.entity.getId(), updateAttrs));
			}
			updateAttrs.clear();
		}
	}

	@Override
	public void updatePlayer(EntityPlayer entityplayer) {
		AsyncCatcher.catchOp("player tracker update");
		if (entityplayer != this.entity) {
			if (c(entityplayer)) {
				if (!trackedPlayers.contains(entityplayer) && (canPlayerSeeTrackerChunk(entityplayer) || entity.attachedToPlayer)) {
					if (entity instanceof EntityPlayer) {
						Player player = ((EntityPlayer) entity).getBukkitEntity();
						if (!entityplayer.getBukkitEntity().canSee(player)) {
							return;
						}
					}
					entityplayer.d(entity);
					trackedPlayers.add(entityplayer);
					Packet<?> spawnPacket = e();
					entityplayer.playerConnection.sendPacket(spawnPacket);
					if (!entity.getDataWatcher().d()) {
						entityplayer.playerConnection.sendPacket(new PacketPlayOutEntityMetadata(entity.getId(), entity.getDataWatcher(), true));
					}
					boolean lUpdateVelocity = updateVelocity;
					if (entity instanceof EntityLiving) {
						Collection<AttributeInstance> updateAttrs = ((AttributeMapServer) ((EntityLiving) entity).getAttributeMap()).c();
						if (entity.getId() == entityplayer.getId()) {
							((EntityPlayer) entity).getBukkitEntity().injectScaledMaxHealth(updateAttrs, false);
						}
						if (!updateAttrs.isEmpty()) {
							entityplayer.playerConnection.sendPacket(new PacketPlayOutUpdateAttributes(entity.getId(), updateAttrs));
						}
						if (((EntityLiving) entity).cP()) {
							lUpdateVelocity = true;
						}
					}
					lastSentMotX = entity.motX;
					lastSentMotY = entity.motY;
					lastSentMotZ = entity.motZ;
					if (lUpdateVelocity && !(spawnPacket instanceof PacketPlayOutSpawnEntityLiving)) {
						entityplayer.playerConnection.sendPacket(new PacketPlayOutEntityVelocity(entity.getId(), entity.motX, entity.motY, entity.motZ));
					}
					if (entity instanceof EntityLiving) {
						for (EnumItemSlot enumitemslot : EnumItemSlot.values()) {
							ItemStack itemstack = ((EntityLiving) entity).getEquipment(enumitemslot);
							if (!itemstack.isEmpty()) {
								entityplayer.playerConnection.sendPacket(new PacketPlayOutEntityEquipment(entity.getId(), enumitemslot, itemstack));
							}
						}
					}
					if (entity instanceof EntityHuman) {
						EntityHuman entityhuman = (EntityHuman) entity;
						if (entityhuman.isSleeping()) {
							entityplayer.playerConnection.sendPacket(new PacketPlayOutBed(entityhuman, new BlockPosition(entity)));
						}
					}
					lastHeadYaw = MathHelper.d((entity.getHeadRotation() * 256.0f) / 360.0f);
					broadcast(new PacketPlayOutEntityHeadRotation(entity, (byte) lastHeadYaw));
					if (entity instanceof EntityLiving) {
						EntityLiving entityliving = (EntityLiving) entity;
						for (MobEffect mobeffect : entityliving.getEffects()) {
							entityplayer.playerConnection.sendPacket(new PacketPlayOutEntityEffect(entity.getId(), mobeffect));
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
			} else if (trackedPlayers.contains(entityplayer)) {
				trackedPlayers.remove(entityplayer);
				entity.c(entityplayer);
				entityplayer.c(entity);
			}
		}
	}

	@Override
	public boolean c(EntityPlayer entityplayer) {
		double diffX = entityplayer.locX - (entity.locX);
		double diffZ = entityplayer.locZ - (entity.locZ / 4096.0);
		int lTrackRange = Math.min(trackRange, viewDistance);
		return (diffX >= -lTrackRange) && (diffX <= lTrackRange) && (diffZ >= -lTrackRange) && (diffZ <= lTrackRange) && entity.a(entityplayer);
	}

	private boolean canPlayerSeeTrackerChunk(EntityPlayer entityplayer) {
		return entityplayer.x().getPlayerChunkMap().a(entityplayer, entity.ab, entity.ad);
	}

	private Packet<?> e() {
		if (entity.dead) {
			return null;
		}
		if (entity instanceof EntityPlayer) {
			return new PacketPlayOutNamedEntitySpawn((EntityHuman) entity);
		}
		if (entity instanceof IAnimal) {
			lastHeadYaw = MathHelper.d((entity.getHeadRotation() * 256.0f) / 360.0f);
			return new PacketPlayOutSpawnEntityLiving((EntityLiving) entity);
		}
		if (entity instanceof EntityPainting) {
			return new PacketPlayOutSpawnEntityPainting((EntityPainting) entity);
		}
		if (entity instanceof EntityItem) {
			return new PacketPlayOutSpawnEntity(entity, 2, 1);
		}
		if (entity instanceof EntityMinecartAbstract) {
			EntityMinecartAbstract entityminecartabstract = (EntityMinecartAbstract) entity;
			return new PacketPlayOutSpawnEntity(entity, 10, entityminecartabstract.v().a());
		}
		if (entity instanceof EntityBoat) {
			return new PacketPlayOutSpawnEntity(entity, 1);
		}
		if (entity instanceof EntityExperienceOrb) {
			return new PacketPlayOutSpawnEntityExperienceOrb((EntityExperienceOrb) entity);
		}
		if (entity instanceof EntityFishingHook) {
			EntityHuman entityhuman = ((EntityFishingHook) entity).l();
			return new PacketPlayOutSpawnEntity(entity, 90, (entityhuman == null) ? entity.getId() : entityhuman.getId());
		}
		if (entity instanceof EntitySpectralArrow) {
			Entity shooter = ((EntitySpectralArrow) entity).shooter;
			return new PacketPlayOutSpawnEntity(entity, 91, 1 + ((shooter == null) ? entity.getId() : shooter.getId()));
		}
		if (entity instanceof EntityTippedArrow) {
			Entity shooter = ((EntityArrow) entity).shooter;
			return new PacketPlayOutSpawnEntity(entity, 60, 1 + ((shooter == null) ? entity.getId() : shooter.getId()));
		}
		if (entity instanceof EntitySnowball) {
			return new PacketPlayOutSpawnEntity(entity, 61);
		}
		if (entity instanceof EntityLlamaSpit) {
			return new PacketPlayOutSpawnEntity(entity, 68);
		}
		if (entity instanceof EntityPotion) {
			return new PacketPlayOutSpawnEntity(entity, 73);
		}
		if (entity instanceof EntityThrownExpBottle) {
			return new PacketPlayOutSpawnEntity(entity, 75);
		}
		if (entity instanceof EntityEnderPearl) {
			return new PacketPlayOutSpawnEntity(entity, 65);
		}
		if (entity instanceof EntityEnderSignal) {
			return new PacketPlayOutSpawnEntity(entity, 72);
		}
		if (entity instanceof EntityFireworks) {
			return new PacketPlayOutSpawnEntity(entity, 76);
		}
		if (entity instanceof EntityFireball) {
			EntityFireball entityfireball = (EntityFireball) entity;
			byte objectTypeId = 63;
			if (entity instanceof EntitySmallFireball) {
				objectTypeId = 64;
			} else if (entity instanceof EntityDragonFireball) {
				objectTypeId = 93;
			} else if (entity instanceof EntityWitherSkull) {
				objectTypeId = 66;
			}
			PacketPlayOutSpawnEntity packetplayoutspawnentity = null;
			if (entityfireball.shooter != null) {
				packetplayoutspawnentity = new PacketPlayOutSpawnEntity(entity, objectTypeId, ((EntityFireball) entity).shooter.getId());
			} else {
				packetplayoutspawnentity = new PacketPlayOutSpawnEntity(entity, objectTypeId, 0);
			}
			packetplayoutspawnentity.a((int) (entityfireball.dirX * 8000.0));
			packetplayoutspawnentity.b((int) (entityfireball.dirY * 8000.0));
			packetplayoutspawnentity.c((int) (entityfireball.dirZ * 8000.0));
			return packetplayoutspawnentity;
		}
		if (entity instanceof EntityShulkerBullet) {
			PacketPlayOutSpawnEntity packetplayoutspawnentity2 = new PacketPlayOutSpawnEntity(entity, 67, 0);
			packetplayoutspawnentity2.a((int) (entity.motX * 8000.0));
			packetplayoutspawnentity2.b((int) (entity.motY * 8000.0));
			packetplayoutspawnentity2.c((int) (entity.motZ * 8000.0));
			return packetplayoutspawnentity2;
		}
		if (entity instanceof EntityEgg) {
			return new PacketPlayOutSpawnEntity(entity, 62);
		}
		if (entity instanceof EntityEvokerFangs) {
			return new PacketPlayOutSpawnEntity(entity, 79);
		}
		if (entity instanceof EntityTNTPrimed) {
			return new PacketPlayOutSpawnEntity(entity, 50);
		}
		if (entity instanceof EntityEnderCrystal) {
			return new PacketPlayOutSpawnEntity(entity, 51);
		}
		if (entity instanceof EntityFallingBlock) {
			EntityFallingBlock entityfallingblock = (EntityFallingBlock) entity;
			return new PacketPlayOutSpawnEntity(entity, 70, Block.getCombinedId(entityfallingblock.getBlock()));
		}
		if (entity instanceof EntityArmorStand) {
			return new PacketPlayOutSpawnEntity(entity, 78);
		}
		if (entity instanceof EntityItemFrame) {
			EntityItemFrame entityitemframe = (EntityItemFrame) entity;
			return new PacketPlayOutSpawnEntity(entity, 71, entityitemframe.direction.get2DRotationValue(), entityitemframe.getBlockPosition());
		}
		if (entity instanceof EntityLeash) {
			EntityLeash entityleash = (EntityLeash) entity;
			return new PacketPlayOutSpawnEntity(entity, 77, 0, entityleash.getBlockPosition());
		}
		if (entity instanceof EntityAreaEffectCloud) {
			return new PacketPlayOutSpawnEntity(entity, 3);
		}
		throw new IllegalArgumentException("Don't know how to add " + entity.getClass() + "!");
	}

	@Override
	public void a(int i) {
		viewDistance = i;
	}

	@Override
	public void c() {
		shouldScanForce = true;
	}

}
