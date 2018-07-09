package protocolsupport.protocol.typeremapper.pe;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import com.google.common.primitives.Doubles;
import com.google.common.primitives.Floats;
import net.minecraft.server.v1_12_R1.ChatMessage;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.EnumGamemode;
import net.minecraft.server.v1_12_R1.EnumMoveType;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.MinecraftServer;
import net.minecraft.server.v1_12_R1.MobEffects;
import net.minecraft.server.v1_12_R1.NetworkManager;
import net.minecraft.server.v1_12_R1.PacketPlayInFlying;
import net.minecraft.server.v1_12_R1.PacketPlayOutPosition;
import net.minecraft.server.v1_12_R1.PlayerConnection;
import net.minecraft.server.v1_12_R1.PlayerConnectionUtils;
import net.minecraft.server.v1_12_R1.Vec3D;
import net.minecraft.server.v1_12_R1.WorldServer;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.spigotmc.SpigotConfig;
import protocolsupport.utils.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Set;

public class FixedPlayerConnection extends PlayerConnection {
	// private static final Logger LOGGER = LogManager.getLogger();
	public final NetworkManager networkManager;
	private final MinecraftServer minecraftServer;
	public EntityPlayer player;
	private int e;
	private double l;
	private double m;
	private double n;
	private double o;
	private double p;
	private double q;
	private Vec3D teleportPos;
	private int teleportAwait;
	private int A;
	private boolean B;
	private int receivedMovePackets;
	private int processedMovePackets;
	private final CraftServer server;
	private int lastTick;
	private int allowedPlayerTicks;
	private double lastPosX;
	private double lastPosY;
	private double lastPosZ;
	private float lastPitch;
	private float lastYaw;
	private boolean justTeleported;

	public FixedPlayerConnection(MinecraftServer minecraftserver, NetworkManager networkmanager, EntityPlayer entityplayer) {
		super(minecraftserver, networkmanager, entityplayer);

		this.minecraftServer = minecraftserver;
		this.networkManager = networkmanager;
		this.server = minecraftServer.server;
		this.player = entityplayer;

		System.out.println("Using fixed player connection!!!");
	}

	private boolean isFrozen(EntityPlayer entityPlayer) {
		return entityPlayer.getHealth() <= 0.0F || entityPlayer.isSleeping() || entityPlayer.playerConnection != null && entityPlayer.playerConnection.isDisconnected();
	}

	private boolean getBoolean(Field field, Object object) {
		try {
			return field.getBoolean(object);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private double getDouble(Field field, Object object) {
		try {
			return field.getDouble(object);
		} catch (Exception e) {
			e.printStackTrace();
			return 0d;
		}
	}

	private float getFloat(Field field, Object object) {
		try {
			return field.getFloat(object);
		} catch (Exception e) {
			e.printStackTrace();
			return 0f;
		}
	}

	private static boolean b(PacketPlayInFlying packetplayinflying) {
		return Doubles.isFinite(packetplayinflying.a(0.0D)) && Doubles.isFinite(packetplayinflying.b(0.0D)) && Doubles.isFinite(packetplayinflying.c(0.0D)) && Floats.isFinite(packetplayinflying.b(0.0F)) && Floats.isFinite(packetplayinflying.a(0.0F)) ? Math.abs(packetplayinflying.a(0.0D)) > 3.0E7D || Math.abs(packetplayinflying.b(0.0D)) > 3.0E7D || Math.abs(packetplayinflying.c(0.0D)) > 3.0E7D : true;
	}

	@Override
	public void a(PacketPlayInFlying packetplayinflying) {
		PlayerConnectionUtils.ensureMainThread(packetplayinflying, this, this.player.x());
		if (b(packetplayinflying)) {
			this.disconnect((IChatBaseComponent)(new ChatMessage("multiplayer.disconnect.invalid_player_movement", new Object[0])));
		} else {
			WorldServer worldserver = this.minecraftServer.getWorldServer(this.player.dimension);
			if (!this.player.viewingCredits && !isFrozen(this.player)) {
				if (this.e == 0) {
					this.syncPosition();
				}

				if (this.teleportPos != null) {
					if (this.e - this.A > 20) {
						this.A = this.e;
						this.a(this.teleportPos.x, this.teleportPos.y, this.teleportPos.z, this.player.yaw, this.player.pitch);
					}

					this.allowedPlayerTicks = 20;
				} else {
					this.A = this.e;
					if (this.player.isPassenger()) {
						this.player.setLocation(this.player.locX, this.player.locY, this.player.locZ, packetplayinflying.a(this.player.yaw), packetplayinflying.b(this.player.pitch));
						this.minecraftServer.getPlayerList().d(this.player);
						this.allowedPlayerTicks = 20;
					} else {
						double prevX = this.player.locX;
						double prevY = this.player.locY;
						double prevZ = this.player.locZ;
						float prevYaw = this.player.yaw;
						float prevPitch = this.player.pitch;
						double d0 = this.player.locX;
						double d1 = this.player.locY;
						double d2 = this.player.locZ;
						double d3 = this.player.locY;
						double d4 = packetplayinflying.a(this.player.locX);
						double d5 = packetplayinflying.b(this.player.locY);
						double d6 = packetplayinflying.c(this.player.locZ);
						float f = packetplayinflying.a(this.player.yaw);
						float f1 = packetplayinflying.b(this.player.pitch);
						double d7 = d4 - this.l;
						double d8 = d5 - this.m;
						double d9 = d6 - this.n;
						double d10 = this.player.motX * this.player.motX + this.player.motY * this.player.motY + this.player.motZ * this.player.motZ;
						double d11 = d7 * d7 + d8 * d8 + d9 * d9;
						if (this.player.isSleeping()) {
							if (d11 > 1.0D) {
								this.a(this.player.locX, this.player.locY, this.player.locZ, packetplayinflying.a(this.player.yaw), packetplayinflying.b(this.player.pitch));
							}
						} else {
							++this.receivedMovePackets;
							int i = this.receivedMovePackets - this.processedMovePackets;
							this.allowedPlayerTicks = (int)((long)this.allowedPlayerTicks + (System.currentTimeMillis() / 50L - (long)this.lastTick));
							this.allowedPlayerTicks = Math.max(this.allowedPlayerTicks, 1);
							this.lastTick = (int)(System.currentTimeMillis() / 50L);
							if (i > Math.max(this.allowedPlayerTicks, 5)) {
								// LOGGER.debug("{} is sending move packets too frequently ({} packets since last tick)", this.player.getName(), i);
								i = 1;
							}

							Field hasLookField = ReflectionUtils.getField(PacketPlayInFlying.class, "hasLook");
							Field hasPosField = ReflectionUtils.getField(PacketPlayInFlying.class, "hasPos");
							Field xField = ReflectionUtils.getField(PacketPlayInFlying.class, "x");
							Field yField = ReflectionUtils.getField(PacketPlayInFlying.class, "y");
							Field zField = ReflectionUtils.getField(PacketPlayInFlying.class, "z");
							Field yawField = ReflectionUtils.getField(PacketPlayInFlying.class, "yaw");
							Field pitchField = ReflectionUtils.getField(PacketPlayInFlying.class, "pitch");

							boolean hasLook = getBoolean(hasLookField, packetplayinflying);
							boolean hasPos = getBoolean(hasPosField, packetplayinflying);
							double x = getDouble(xField, packetplayinflying);
							double y = getDouble(yField, packetplayinflying);
							double z = getDouble(zField, packetplayinflying);
							float yaw = getFloat(yawField, packetplayinflying);
							float pitch = getFloat(pitchField, packetplayinflying);

							if (!hasLook && d11 <= 0.0D) {
								this.allowedPlayerTicks = 20;
							} else {
								--this.allowedPlayerTicks;
							}

							float speed;
							if (this.player.abilities.isFlying) {
								speed = this.player.abilities.flySpeed * 20.0F;
							} else {
								speed = this.player.abilities.walkSpeed * 10.0F;
							}

							if (!this.player.L() && (!this.player.x().getGameRules().getBoolean("disableElytraMovementCheck") || !this.player.cP())) {
								float f2 = this.player.cP() ? 300.0F : 100.0F;
								if (d11 - d10 > Math.max((double)f2, Math.pow(SpigotConfig.movedTooQuicklyMultiplier * (double)((float)i) * (double)speed, 2.0D)) && (!this.minecraftServer.R() || !this.minecraftServer.Q().equals(this.player.getName()))) {
									System.out.println("player moved too quickly check!");
									// LOGGER.warn("{} moved too quickly! {},{},{}", this.player.getName(), d7, d8, d9);
									this.a(this.player.locX, this.player.locY, this.player.locZ, this.player.yaw, this.player.pitch);
									return;
								}
							}

							boolean flag = worldserver.getCubes(this.player, this.player.getBoundingBox().shrink(0.0625D)).isEmpty();
							d7 = d4 - this.o;
							d8 = d5 - this.p;
							d9 = d6 - this.q;
							if (this.player.onGround && !packetplayinflying.a() && d8 > 0.0D) {
								Player player = this.getPlayer();
								Location from = new Location(player.getWorld(), this.lastPosX, this.lastPosY, this.lastPosZ, this.lastYaw, this.lastPitch);
								Location to = player.getLocation().clone();
								if (hasPos) {
									to.setX(x);
									to.setY(y);
									to.setZ(z);
								}

								if (hasLook) {
									to.setYaw(yaw);
									to.setPitch(pitch);
								}

								PlayerJumpEvent event = new PlayerJumpEvent(player, from, to);
								if (!event.callEvent()) {
									from = event.getFrom();
									this.internalTeleport(from.getX(), from.getY(), from.getZ(), from.getYaw(), from.getPitch(), Collections.emptySet());
									return;
								}

								this.player.jump();
							}

							this.player.move(EnumMoveType.PLAYER, d7, d8, d9);
							this.player.onGround = packetplayinflying.a();
							double d12 = d8;
							d7 = d4 - this.player.locX;
							d8 = d5 - this.player.locY;
							if (d8 > -0.5D || d8 < 0.5D) {
								d8 = 0.0D;
							}

							d9 = d6 - this.player.locZ;
							d11 = d7 * d7 + d8 * d8 + d9 * d9;
							boolean flag1 = false;
							if (!this.player.L() && d11 > SpigotConfig.movedWronglyThreshold && !this.player.isSleeping() && !this.player.playerInteractManager.isCreative() && this.player.playerInteractManager.getGameMode() != EnumGamemode.SPECTATOR) {
								flag1 = true;
								System.out.println("player moved wrongly check!");
								// LOGGER.warn("{} moved wrongly!", this.player.getName());
							}

							this.player.setLocation(d4, d5, d6, f, f1);
							this.player.checkMovement(this.player.locX - d0, this.player.locY - d1, this.player.locZ - d2);
							if (!this.player.noclip && !this.player.isSleeping()) {
								boolean flag2 = worldserver.getCubes(this.player, this.player.getBoundingBox().shrink(0.0625D)).isEmpty();
								if (flag && (flag1 || !flag2)) {
									System.out.println("Ignoring #1...");
									// this.a(d0, d1, d2, f, f1);
									// return;
								}
							}

							this.player.setLocation(prevX, prevY, prevZ, prevYaw, prevPitch);
							Player player = this.getPlayer();
							Location from = new Location(player.getWorld(), this.lastPosX, this.lastPosY, this.lastPosZ, this.lastYaw, this.lastPitch);
							Location to = player.getLocation().clone();
							if (hasPos) {
								to.setX(x);
								to.setY(y);
								to.setZ(z);
							}

							if (hasLook) {
								to.setYaw(yaw);
								to.setPitch(pitch);
							}

							double delta = Math.pow(this.lastPosX - to.getX(), 2.0D) + Math.pow(this.lastPosY - to.getY(), 2.0D) + Math.pow(this.lastPosZ - to.getZ(), 2.0D);
							float deltaAngle = Math.abs(this.lastYaw - to.getYaw()) + Math.abs(this.lastPitch - to.getPitch());
							if ((delta > 0.00390625D || deltaAngle > 10.0F) && !isFrozen(this.player)) {
								this.lastPosX = to.getX();
								this.lastPosY = to.getY();
								this.lastPosZ = to.getZ();
								this.lastYaw = to.getYaw();
								this.lastPitch = to.getPitch();
								if (from.getX() != 1.7976931348623157E308D) {
									Location oldTo = to.clone();
									PlayerMoveEvent event = new PlayerMoveEvent(player, from, to);
									this.server.getPluginManager().callEvent(event);
									if (event.isCancelled()) {
										this.teleport(from);
										return;
									}

									if (!oldTo.equals(event.getTo()) && !event.isCancelled()) {
										this.player.getBukkitEntity().teleport(event.getTo(), PlayerTeleportEvent.TeleportCause.PLUGIN);
										return;
									}

									if (!from.equals(this.getPlayer().getLocation()) && this.justTeleported) {
										this.justTeleported = false;
										return;
									}
								}
							}

							this.player.setLocation(d4, d5, d6, f, f1);
							this.B = d12 >= -0.03125D;
							this.B &= !this.minecraftServer.getAllowFlight() && !this.player.abilities.canFly;
							this.B &= !this.player.hasEffect(MobEffects.LEVITATION) && !this.player.cP() && !worldserver.c(this.player.getBoundingBox().g(0.0625D).b(0.0D, -0.55D, 0.0D));
							this.player.onGround = packetplayinflying.a();
							this.minecraftServer.getPlayerList().d(this.player);
							this.player.a(this.player.locY - d3, packetplayinflying.a());
							this.o = this.player.locX;
							this.p = this.player.locY;
							this.q = this.player.locZ;
						}
					}
				}
			}
		}

	}

	private void internalTeleport(double d0, double d1, double d2, float f, float f1, Set<PacketPlayOutPosition.EnumPlayerTeleportFlags> set) {
		if (Float.isNaN(f)) {
			f = 0.0F;
		}

		if (Float.isNaN(f1)) {
			f1 = 0.0F;
		}

		this.justTeleported = true;
		double d3 = set.contains(PacketPlayOutPosition.EnumPlayerTeleportFlags.X) ? this.player.locX : 0.0D;
		double d4 = set.contains(PacketPlayOutPosition.EnumPlayerTeleportFlags.Y) ? this.player.locY : 0.0D;
		double d5 = set.contains(PacketPlayOutPosition.EnumPlayerTeleportFlags.Z) ? this.player.locZ : 0.0D;
		this.teleportPos = new Vec3D(d0 + d3, d1 + d4, d2 + d5);
		float f2 = f;
		float f3 = f1;
		if (set.contains(PacketPlayOutPosition.EnumPlayerTeleportFlags.Y_ROT)) {
			f2 = f + this.player.yaw;
		}

		if (set.contains(PacketPlayOutPosition.EnumPlayerTeleportFlags.X_ROT)) {
			f3 = f1 + this.player.pitch;
		}

		this.lastPosX = this.teleportPos.x;
		this.lastPosY = this.teleportPos.y;
		this.lastPosZ = this.teleportPos.z;
		this.lastYaw = f2;
		this.lastPitch = f3;
		if (++this.teleportAwait == 2147483647) {
			this.teleportAwait = 0;
		}

		this.A = this.e;
		this.player.setLocation(this.teleportPos.x, this.teleportPos.y, this.teleportPos.z, f2, f3);
		this.player.playerConnection.sendPacket(new PacketPlayOutPosition(d0, d1, d2, f, f1, set, this.teleportAwait));
	}
}