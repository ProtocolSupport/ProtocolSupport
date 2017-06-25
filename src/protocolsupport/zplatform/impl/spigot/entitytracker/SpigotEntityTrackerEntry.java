package protocolsupport.zplatform.impl.spigot.entitytracker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import net.minecraft.server.v1_12_R1.EntityTracker;
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
import protocolsupport.api.ProtocolType;
import protocolsupport.protocol.ConnectionImpl;

public class SpigotEntityTrackerEntry extends EntityTrackerEntry {

	private final Entity tracker;
	private final int trackRange;
	private int viewDistance;
	private final int updateInterval;
	private long xLoc;
	private long yLoc;
	private long zLoc;
	private int yRot;
	private int xRot;
	private int headYaw;
	private double lastSentMotX;
	private double lastSentMotY;
	private double lastSentMotZ;
	private double lastScanLocX;
	private double lastScanLocY;
	private double lastScanLocZ;
	private boolean isMoving;
	private final boolean updateVelocity;
	private int moveUpdateTicks;
	private List<Entity> passengers = Collections.emptyList();
	private boolean isPassenger;
	private boolean onGround;

	private final Set<EntityPlayer> trackedDefaultPlayers = new HashSet<>();
	private final Set<EntityPlayer> trackedPEPlayers = new HashSet<>();

	public SpigotEntityTrackerEntry(Entity entity, int trackRange, int viewDistance, int updateInterval, boolean updateVelocity) {
		super(entity, trackRange, viewDistance, updateInterval, updateVelocity);
		this.tracker = entity;
		this.trackRange = trackRange;
		this.viewDistance = viewDistance;
		this.updateInterval = updateInterval;
		this.updateVelocity = updateVelocity;
		this.xLoc = EntityTracker.a(entity.locX);
		this.yLoc = EntityTracker.a(entity.locY);
		this.zLoc = EntityTracker.a(entity.locZ);
		this.yRot = MathHelper.d((entity.yaw * 256.0f) / 360.0f);
		this.xRot = MathHelper.d((entity.pitch * 256.0f) / 360.0f);
		this.headYaw = MathHelper.d((entity.getHeadRotation() * 256.0f) / 360.0f);
		this.onGround = entity.onGround;
	}

	@Override
	public boolean equals(final Object object) {
		return (object instanceof SpigotEntityTrackerEntry) && (((SpigotEntityTrackerEntry) object).tracker.getId() == this.tracker.getId());
	}

	@Override
	public int hashCode() {
		return this.tracker.getId();
	}

	@Override
	public void track(final List<EntityHuman> list) {
		this.b = false;
		if (!this.isMoving || (this.tracker.d(this.lastScanLocX, this.lastScanLocY, this.lastScanLocZ) > 16.0)) {
			this.lastScanLocX = this.tracker.locX;
			this.lastScanLocY = this.tracker.locY;
			this.lastScanLocZ = this.tracker.locZ;
			this.isMoving = true;
			this.b = true;
			this.scanPlayers(list);
		}
		final List<Entity> passengers = this.tracker.bF();
		if (!passengers.equals(this.passengers)) {
			this.passengers = passengers;
			this.broadcastIncludingSelf(new PacketPlayOutMount(this.tracker));
		}
		if (this.tracker instanceof EntityItemFrame) {
			final EntityItemFrame entityitemframe = (EntityItemFrame) this.tracker;
			final ItemStack itemstack = entityitemframe.getItem();
			if (((this.a % 10) == 0) && (itemstack.getItem() instanceof ItemWorldMap)) {
				final WorldMap worldmap = Items.FILLED_MAP.getSavedMap(itemstack, this.tracker.world);
				for (final EntityHuman entityhuman : this.trackedPlayers) {
					final EntityPlayer entityplayer = (EntityPlayer) entityhuman;
					worldmap.a(entityplayer, itemstack);
					final Packet<?> packet = Items.FILLED_MAP.a(itemstack, this.tracker.world, entityplayer);
					if (packet != null) {
						entityplayer.playerConnection.sendPacket(packet);
					}
				}
			}
			this.updateMetadataAndAttributes();
		}
		if (((this.a % this.updateInterval) == 0) || this.tracker.impulse || this.tracker.getDataWatcher().a()) {
			if (this.tracker.isPassenger()) {
				final int i = MathHelper.d((this.tracker.yaw * 256.0f) / 360.0f);
				final int j = MathHelper.d((this.tracker.pitch * 256.0f) / 360.0f);
				final boolean flag = (Math.abs(i - this.yRot) >= 1) || (Math.abs(j - this.xRot) >= 1);
				if (flag) {
					this.broadcast(new PacketPlayOutEntity.PacketPlayOutEntityLook(this.tracker.getId(), (byte) i, (byte) j, this.tracker.onGround));
					this.yRot = i;
					this.xRot = j;
				}
				this.xLoc = EntityTracker.a(this.tracker.locX);
				this.yLoc = EntityTracker.a(this.tracker.locY);
				this.zLoc = EntityTracker.a(this.tracker.locZ);
				this.updateMetadataAndAttributes();
				this.isPassenger = true;
			} else {
				++this.moveUpdateTicks;
				final long k = EntityTracker.a(this.tracker.locX);
				final long l = EntityTracker.a(this.tracker.locY);
				final long i2 = EntityTracker.a(this.tracker.locZ);
				final int j2 = MathHelper.d((this.tracker.yaw * 256.0f) / 360.0f);
				final int k2 = MathHelper.d((this.tracker.pitch * 256.0f) / 360.0f);
				final long l2 = k - this.xLoc;
				final long i3 = l - this.yLoc;
				final long j3 = i2 - this.zLoc;
				Packet<?> defaultpacket = null;
				Packet<?> pepacket = null;
				final boolean flag2 = (((l2 * l2) + (i3 * i3) + (j3 * j3)) >= 128L) || ((this.a % 60) == 0);
				final boolean flag3 = (Math.abs(j2 - this.yRot) >= 1) || (Math.abs(k2 - this.xRot) >= 1);
				if (flag2) {
					this.xLoc = k;
					this.yLoc = l;
					this.zLoc = i2;
				}
				if (flag3) {
					this.yRot = j2;
					this.xRot = k2;
				}
				if ((this.a > 0) || (this.tracker instanceof EntityArrow)) {
					if ((l2 >= -32768L) && (l2 < 32768L) && (i3 >= -32768L) && (i3 < 32768L) && (j3 >= -32768L) && (j3 < 32768L) && (this.moveUpdateTicks <= 400) && !this.isPassenger && (this.onGround == this.tracker.onGround)) {
						if ((!flag2 || !flag3) && !(this.tracker instanceof EntityArrow)) {
							if (flag2) {
								defaultpacket = new PacketPlayOutEntity.PacketPlayOutRelEntityMove(this.tracker.getId(), l2, i3, j3, this.tracker.onGround);
							} else if (flag3) {
								defaultpacket = new PacketPlayOutEntity.PacketPlayOutEntityLook(this.tracker.getId(), (byte) j2, (byte) k2, this.tracker.onGround);
							}
						} else {
							defaultpacket = new PacketPlayOutEntity.PacketPlayOutRelEntityMoveLook(this.tracker.getId(), l2, i3, j3, (byte) j2, (byte) k2, this.tracker.onGround);
						}
						//pepacket = new PacketPlayOutEntityTeleport(this.tracker); Maybe the entire injector is not needed now?
					} else {
						this.onGround = this.tracker.onGround;
						this.moveUpdateTicks = 0;
						if (this.tracker instanceof EntityPlayer) {
							this.scanPlayers(new ArrayList<EntityHuman>(this.trackedPlayers));
						}
						this.c();
						defaultpacket = new PacketPlayOutEntityTeleport(this.tracker);
					}
				}
				boolean updateVelocity = this.updateVelocity;
				if ((this.tracker instanceof EntityLiving) && ((EntityLiving) this.tracker).cP()) {
					updateVelocity = true;
				}
				if (updateVelocity && (this.a > 0)) {
					final double d0 = this.tracker.motX - this.lastSentMotX;
					final double d2 = this.tracker.motY - this.lastSentMotY;
					final double d3 = this.tracker.motZ - this.lastSentMotZ;
					final double d4 = (d0 * d0) + (d2 * d2) + (d3 * d3);
					if ((d4 > 4.0E-4) || ((d4 > 0.0) && (this.tracker.motX == 0.0) && (this.tracker.motY == 0.0) && (this.tracker.motZ == 0.0))) {
						this.lastSentMotX = this.tracker.motX;
						this.lastSentMotY = this.tracker.motY;
						this.lastSentMotZ = this.tracker.motZ;
						this.broadcast(new PacketPlayOutEntityVelocity(this.tracker.getId(), this.lastSentMotX, this.lastSentMotY, this.lastSentMotZ));
					}
				}
				if (defaultpacket != null) {
					this.broadcast(defaultpacket, pepacket);
				}
				this.updateMetadataAndAttributes();
				this.isPassenger = false;
			}
			final int currentHeadYaw = MathHelper.d((this.tracker.getHeadRotation() * 256.0f) / 360.0f);
			if (Math.abs(currentHeadYaw - this.headYaw) >= 1) {
				this.broadcast(new PacketPlayOutEntityHeadRotation(this.tracker, (byte) currentHeadYaw));
				this.headYaw = currentHeadYaw;
			}
			this.tracker.impulse = false;
		}
		++this.a;
		if (this.tracker.velocityChanged) {
			boolean cancelled = false;
			if (this.tracker instanceof EntityPlayer) {
				final Player player = (Player) this.tracker.getBukkitEntity();
				final Vector velocity = player.getVelocity();
				final PlayerVelocityEvent event = new PlayerVelocityEvent(player, velocity.clone());
				this.tracker.world.getServer().getPluginManager().callEvent(event);
				if (event.isCancelled()) {
					cancelled = true;
				} else if (!velocity.equals(event.getVelocity())) {
					player.setVelocity(event.getVelocity());
				}
			}
			if (!cancelled) {
				this.broadcastIncludingSelf(new PacketPlayOutEntityVelocity(this.tracker));
			}
			this.tracker.velocityChanged = false;
		}
	}

	private void updateMetadataAndAttributes() {
		final DataWatcher datawatcher = this.tracker.getDataWatcher();
		if (datawatcher.a()) {
			this.broadcastIncludingSelf(new PacketPlayOutEntityMetadata(this.tracker.getId(), datawatcher, false));
		}
		if (this.tracker instanceof EntityLiving) {
			final AttributeMapServer attributemapserver = (AttributeMapServer) ((EntityLiving) this.tracker).getAttributeMap();
			final Set<AttributeInstance> set = attributemapserver.getAttributes();
			if (!set.isEmpty()) {
				if (this.tracker instanceof EntityPlayer) {
					((EntityPlayer) this.tracker).getBukkitEntity().injectScaledMaxHealth(set, false);
				}
				this.broadcastIncludingSelf(new PacketPlayOutUpdateAttributes(this.tracker.getId(), set));
			}
			set.clear();
		}
	}

	private void broadcast(Packet<?> defaultpacket, Packet<?> pepacket) {
		if (pepacket == null) {
			pepacket = defaultpacket;
		}
		for (EntityPlayer player : trackedDefaultPlayers) {
			player.playerConnection.sendPacket(defaultpacket);
		}
		for (EntityPlayer player : trackedPEPlayers) {
			player.playerConnection.sendPacket(pepacket);
		}
	}

	@Override
	public void broadcast(final Packet<?> packet) {
		for (final EntityPlayer entityplayer : this.trackedPlayers) {
			entityplayer.playerConnection.sendPacket(packet);
		}
	}

	@Override
	public void broadcastIncludingSelf(final Packet<?> packet) {
		this.broadcast(packet);
		if (this.tracker instanceof EntityPlayer) {
			((EntityPlayer) this.tracker).playerConnection.sendPacket(packet);
		}
	}

	@Override
	public void a() {
		for (final EntityPlayer entityplayer : this.trackedPlayers) {
			this.tracker.c(entityplayer);
			entityplayer.c(this.tracker);
		}
	}

	@Override
	public void a(final EntityPlayer entityplayer) {
		if (this.trackedPlayers.contains(entityplayer)) {
			this.tracker.c(entityplayer);
			entityplayer.c(this.tracker);
			removeTrackedPlayer(entityplayer);
		}
	}

	@Override
	public void updatePlayer(final EntityPlayer entityplayer) {
		AsyncCatcher.catchOp("player tracker update");
		if (entityplayer != this.tracker) {
			if (this.c(entityplayer)) {
				if (!this.trackedPlayers.contains(entityplayer) && (this.canPlayerSeeTrackerChunk(entityplayer) || this.tracker.attachedToPlayer)) {
					if (this.tracker instanceof EntityPlayer) {
						final Player player = ((EntityPlayer) this.tracker).getBukkitEntity();
						if (!entityplayer.getBukkitEntity().canSee(player)) {
							return;
						}
					}
					entityplayer.d(this.tracker);
					addTrackedPlayer(entityplayer);
					final Packet<?> packet = this.e();
					entityplayer.playerConnection.sendPacket(packet);
					if (!this.tracker.getDataWatcher().d()) {
						entityplayer.playerConnection.sendPacket(new PacketPlayOutEntityMetadata(this.tracker.getId(), this.tracker.getDataWatcher(), true));
					}
					boolean flag = this.updateVelocity;
					if (this.tracker instanceof EntityLiving) {
						final AttributeMapServer attributemapserver = (AttributeMapServer) ((EntityLiving) this.tracker).getAttributeMap();
						final Collection<AttributeInstance> collection = attributemapserver.c();
						if (this.tracker.getId() == entityplayer.getId()) {
							((EntityPlayer) this.tracker).getBukkitEntity().injectScaledMaxHealth(collection, false);
						}
						if (!collection.isEmpty()) {
							entityplayer.playerConnection.sendPacket(new PacketPlayOutUpdateAttributes(this.tracker.getId(), collection));
						}
						if (((EntityLiving) this.tracker).cP()) {
							flag = true;
						}
					}
					this.lastSentMotX = this.tracker.motX;
					this.lastSentMotY = this.tracker.motY;
					this.lastSentMotZ = this.tracker.motZ;
					if (flag && !(packet instanceof PacketPlayOutSpawnEntityLiving)) {
						entityplayer.playerConnection.sendPacket(new PacketPlayOutEntityVelocity(this.tracker.getId(), this.tracker.motX, this.tracker.motY, this.tracker.motZ));
					}
					if (this.tracker instanceof EntityLiving) {
						for (final EnumItemSlot enumitemslot : EnumItemSlot.values()) {
							final ItemStack itemstack = ((EntityLiving) this.tracker).getEquipment(enumitemslot);
							if (!itemstack.isEmpty()) {
								entityplayer.playerConnection.sendPacket(new PacketPlayOutEntityEquipment(this.tracker.getId(), enumitemslot, itemstack));
							}
						}
					}
					if (this.tracker instanceof EntityHuman) {
						final EntityHuman entityhuman = (EntityHuman) this.tracker;
						if (entityhuman.isSleeping()) {
							entityplayer.playerConnection.sendPacket(new PacketPlayOutBed(entityhuman, new BlockPosition(this.tracker)));
						}
					}
					this.headYaw = MathHelper.d((this.tracker.getHeadRotation() * 256.0f) / 360.0f);
					this.broadcast(new PacketPlayOutEntityHeadRotation(this.tracker, (byte) this.headYaw));
					if (this.tracker instanceof EntityLiving) {
						final EntityLiving entityliving = (EntityLiving) this.tracker;
						for (final MobEffect mobeffect : entityliving.getEffects()) {
							entityplayer.playerConnection.sendPacket(new PacketPlayOutEntityEffect(this.tracker.getId(), mobeffect));
						}
					}
					if (!this.tracker.bF().isEmpty()) {
						entityplayer.playerConnection.sendPacket(new PacketPlayOutMount(this.tracker));
					}
					if (this.tracker.isPassenger()) {
						entityplayer.playerConnection.sendPacket(new PacketPlayOutMount(this.tracker.bJ()));
					}
					this.tracker.b(entityplayer);
					entityplayer.d(this.tracker);
				}
			} else if (this.trackedPlayers.contains(entityplayer)) {
				removeTrackedPlayer(entityplayer);
				this.tracker.c(entityplayer);
				entityplayer.c(this.tracker);
			}
		}
	}

	@Override
	public boolean c(final EntityPlayer entityplayer) {
		final double d0 = entityplayer.locX - (this.xLoc / 4096.0);
		final double d2 = entityplayer.locZ - (this.zLoc / 4096.0);
		final int i = Math.min(this.trackRange, this.viewDistance);
		return (d0 >= -i) && (d0 <= i) && (d2 >= -i) && (d2 <= i) && this.tracker.a(entityplayer);
	}

	private boolean canPlayerSeeTrackerChunk(final EntityPlayer entityplayer) {
		return entityplayer.x().getPlayerChunkMap().a(entityplayer, this.tracker.ab, this.tracker.ad);
	}

	@Override
	public void scanPlayers(final List<EntityHuman> list) {
		for (int i = 0; i < list.size(); ++i) {
			this.updatePlayer((EntityPlayer) list.get(i));
		}
	}

	private Packet<?> e() {
		if (this.tracker.dead) {
			return null;
		}
		if (this.tracker instanceof EntityPlayer) {
			return new PacketPlayOutNamedEntitySpawn((EntityHuman) this.tracker);
		}
		if (this.tracker instanceof IAnimal) {
			this.headYaw = MathHelper.d((this.tracker.getHeadRotation() * 256.0f) / 360.0f);
			return new PacketPlayOutSpawnEntityLiving((EntityLiving) this.tracker);
		}
		if (this.tracker instanceof EntityPainting) {
			return new PacketPlayOutSpawnEntityPainting((EntityPainting) this.tracker);
		}
		if (this.tracker instanceof EntityItem) {
			return new PacketPlayOutSpawnEntity(this.tracker, 2, 1);
		}
		if (this.tracker instanceof EntityMinecartAbstract) {
			final EntityMinecartAbstract entityminecartabstract = (EntityMinecartAbstract) this.tracker;
			return new PacketPlayOutSpawnEntity(this.tracker, 10, entityminecartabstract.v().a());
		}
		if (this.tracker instanceof EntityBoat) {
			return new PacketPlayOutSpawnEntity(this.tracker, 1);
		}
		if (this.tracker instanceof EntityExperienceOrb) {
			return new PacketPlayOutSpawnEntityExperienceOrb((EntityExperienceOrb) this.tracker);
		}
		if (this.tracker instanceof EntityFishingHook) {
			final EntityHuman entityhuman = ((EntityFishingHook) this.tracker).l();
			return new PacketPlayOutSpawnEntity(this.tracker, 90, (entityhuman == null) ? this.tracker.getId() : entityhuman.getId());
		}
		if (this.tracker instanceof EntitySpectralArrow) {
			final Entity entity = ((EntitySpectralArrow) this.tracker).shooter;
			return new PacketPlayOutSpawnEntity(this.tracker, 91, 1 + ((entity == null) ? this.tracker.getId() : entity.getId()));
		}
		if (this.tracker instanceof EntityTippedArrow) {
			final Entity entity = ((EntityArrow) this.tracker).shooter;
			return new PacketPlayOutSpawnEntity(this.tracker, 60, 1 + ((entity == null) ? this.tracker.getId() : entity.getId()));
		}
		if (this.tracker instanceof EntitySnowball) {
			return new PacketPlayOutSpawnEntity(this.tracker, 61);
		}
		if (this.tracker instanceof EntityLlamaSpit) {
			return new PacketPlayOutSpawnEntity(this.tracker, 68);
		}
		if (this.tracker instanceof EntityPotion) {
			return new PacketPlayOutSpawnEntity(this.tracker, 73);
		}
		if (this.tracker instanceof EntityThrownExpBottle) {
			return new PacketPlayOutSpawnEntity(this.tracker, 75);
		}
		if (this.tracker instanceof EntityEnderPearl) {
			return new PacketPlayOutSpawnEntity(this.tracker, 65);
		}
		if (this.tracker instanceof EntityEnderSignal) {
			return new PacketPlayOutSpawnEntity(this.tracker, 72);
		}
		if (this.tracker instanceof EntityFireworks) {
			return new PacketPlayOutSpawnEntity(this.tracker, 76);
		}
		if (this.tracker instanceof EntityFireball) {
			final EntityFireball entityfireball = (EntityFireball) this.tracker;
			PacketPlayOutSpawnEntity packetplayoutspawnentity = null;
			byte b0 = 63;
			if (this.tracker instanceof EntitySmallFireball) {
				b0 = 64;
			} else if (this.tracker instanceof EntityDragonFireball) {
				b0 = 93;
			} else if (this.tracker instanceof EntityWitherSkull) {
				b0 = 66;
			}
			if (entityfireball.shooter != null) {
				packetplayoutspawnentity = new PacketPlayOutSpawnEntity(this.tracker, b0, ((EntityFireball) this.tracker).shooter.getId());
			} else {
				packetplayoutspawnentity = new PacketPlayOutSpawnEntity(this.tracker, b0, 0);
			}
			packetplayoutspawnentity.a((int) (entityfireball.dirX * 8000.0));
			packetplayoutspawnentity.b((int) (entityfireball.dirY * 8000.0));
			packetplayoutspawnentity.c((int) (entityfireball.dirZ * 8000.0));
			return packetplayoutspawnentity;
		}
		if (this.tracker instanceof EntityShulkerBullet) {
			final PacketPlayOutSpawnEntity packetplayoutspawnentity2 = new PacketPlayOutSpawnEntity(this.tracker, 67, 0);
			packetplayoutspawnentity2.a((int) (this.tracker.motX * 8000.0));
			packetplayoutspawnentity2.b((int) (this.tracker.motY * 8000.0));
			packetplayoutspawnentity2.c((int) (this.tracker.motZ * 8000.0));
			return packetplayoutspawnentity2;
		}
		if (this.tracker instanceof EntityEgg) {
			return new PacketPlayOutSpawnEntity(this.tracker, 62);
		}
		if (this.tracker instanceof EntityEvokerFangs) {
			return new PacketPlayOutSpawnEntity(this.tracker, 79);
		}
		if (this.tracker instanceof EntityTNTPrimed) {
			return new PacketPlayOutSpawnEntity(this.tracker, 50);
		}
		if (this.tracker instanceof EntityEnderCrystal) {
			return new PacketPlayOutSpawnEntity(this.tracker, 51);
		}
		if (this.tracker instanceof EntityFallingBlock) {
			final EntityFallingBlock entityfallingblock = (EntityFallingBlock) this.tracker;
			return new PacketPlayOutSpawnEntity(this.tracker, 70, Block.getCombinedId(entityfallingblock.getBlock()));
		}
		if (this.tracker instanceof EntityArmorStand) {
			return new PacketPlayOutSpawnEntity(this.tracker, 78);
		}
		if (this.tracker instanceof EntityItemFrame) {
			final EntityItemFrame entityitemframe = (EntityItemFrame) this.tracker;
			return new PacketPlayOutSpawnEntity(this.tracker, 71, entityitemframe.direction.get2DRotationValue(), entityitemframe.getBlockPosition());
		}
		if (this.tracker instanceof EntityLeash) {
			final EntityLeash entityleash = (EntityLeash) this.tracker;
			return new PacketPlayOutSpawnEntity(this.tracker, 77, 0, entityleash.getBlockPosition());
		}
		if (this.tracker instanceof EntityAreaEffectCloud) {
			return new PacketPlayOutSpawnEntity(this.tracker, 3);
		}
		throw new IllegalArgumentException("Don't know how to add " + this.tracker.getClass() + "!");
	}

	@Override
	public void clear(final EntityPlayer entityplayer) {
		AsyncCatcher.catchOp("player tracker clear");
		if (this.trackedPlayers.contains(entityplayer)) {
			removeTrackedPlayer(entityplayer);
			this.tracker.c(entityplayer);
			entityplayer.c(this.tracker);
		}
	}

	private void addTrackedPlayer(EntityPlayer player) {
		this.trackedPlayers.add(player);
		ConnectionImpl connection = ConnectionImpl.getFromChannel(player.playerConnection.networkManager.channel);
		if ((connection != null) && (connection.getVersion().getProtocolType() == ProtocolType.PE)) {
			this.trackedPEPlayers.add(player);
		} else {
			this.trackedDefaultPlayers.add(player);
		}
	}

	private void removeTrackedPlayer(EntityPlayer player) {
		this.trackedPlayers.remove(player);
		this.trackedDefaultPlayers.remove(player);
		this.trackedPEPlayers.remove(player);
	}

	@Override
	public Entity b() {
		return this.tracker;
	}

	@Override
	public void a(final int i) {
		this.viewDistance = i;
	}

	@Override
	public void c() {
		this.isMoving = false;
	}

}
