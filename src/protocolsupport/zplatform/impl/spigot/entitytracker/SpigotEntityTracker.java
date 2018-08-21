package protocolsupport.zplatform.impl.spigot.entitytracker;

import java.lang.reflect.Field;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.spigotmc.AsyncCatcher;
import org.spigotmc.TrackingRange;

import net.minecraft.server.v1_13_R1.CrashReport;
import net.minecraft.server.v1_13_R1.CrashReportSystemDetails;
import net.minecraft.server.v1_13_R1.Entity;
import net.minecraft.server.v1_13_R1.EntityAreaEffectCloud;
import net.minecraft.server.v1_13_R1.EntityArmorStand;
import net.minecraft.server.v1_13_R1.EntityArrow;
import net.minecraft.server.v1_13_R1.EntityBat;
import net.minecraft.server.v1_13_R1.EntityBoat;
import net.minecraft.server.v1_13_R1.EntityEgg;
import net.minecraft.server.v1_13_R1.EntityEnderCrystal;
import net.minecraft.server.v1_13_R1.EntityEnderDragon;
import net.minecraft.server.v1_13_R1.EntityEnderPearl;
import net.minecraft.server.v1_13_R1.EntityEnderSignal;
import net.minecraft.server.v1_13_R1.EntityEvokerFangs;
import net.minecraft.server.v1_13_R1.EntityExperienceOrb;
import net.minecraft.server.v1_13_R1.EntityFallingBlock;
import net.minecraft.server.v1_13_R1.EntityFireball;
import net.minecraft.server.v1_13_R1.EntityFireworks;
import net.minecraft.server.v1_13_R1.EntityFishingHook;
import net.minecraft.server.v1_13_R1.EntityHanging;
import net.minecraft.server.v1_13_R1.EntityItem;
import net.minecraft.server.v1_13_R1.EntityLlamaSpit;
import net.minecraft.server.v1_13_R1.EntityMinecartAbstract;
import net.minecraft.server.v1_13_R1.EntityPlayer;
import net.minecraft.server.v1_13_R1.EntityPotion;
import net.minecraft.server.v1_13_R1.EntityShulkerBullet;
import net.minecraft.server.v1_13_R1.EntitySmallFireball;
import net.minecraft.server.v1_13_R1.EntitySnowball;
import net.minecraft.server.v1_13_R1.EntitySquid;
import net.minecraft.server.v1_13_R1.EntityTNTPrimed;
import net.minecraft.server.v1_13_R1.EntityThrownExpBottle;
import net.minecraft.server.v1_13_R1.EntityTracker;
import net.minecraft.server.v1_13_R1.EntityTrackerEntry;
import net.minecraft.server.v1_13_R1.EntityTypes;
import net.minecraft.server.v1_13_R1.EntityWither;
import net.minecraft.server.v1_13_R1.IAnimal;
import net.minecraft.server.v1_13_R1.ReportedException;
import net.minecraft.server.v1_13_R1.WorldServer;
import protocolsupport.utils.CachedInstanceOfChain;
import protocolsupport.utils.ReflectionUtils;

@SuppressWarnings("unchecked")
public class SpigotEntityTracker extends EntityTracker {

	protected static final Field trackerEntriesField = ReflectionUtils.getField(EntityTracker.class, "c");

	protected final WorldServer world;
	protected final Set<EntityTrackerEntry> trackerEntries;
	protected final int viewDistance;

	public SpigotEntityTracker(WorldServer worldserver) {
		super(worldserver);
		world = worldserver;
		viewDistance = worldserver.getMinecraftServer().getPlayerList().d();
		try {
			trackerEntries = (Set<EntityTrackerEntry>) trackerEntriesField.get(this);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException("Unable to get needed field", e);
		}
	}

	@Override
	public void addEntity(Entity entity, int trackRange, int updateInterval, boolean updateVelocity) {
		AsyncCatcher.catchOp("entity track");
		trackRange = TrackingRange.getEntityTrackingRange(entity, trackRange);
		try {
			if (trackedEntities.b(entity.getId())) {
				throw new IllegalStateException("Entity is already tracked!");
			}
			EntityTrackerEntry entitytrackerentry = createTrackerEntry(entity, trackRange, updateInterval, updateVelocity);
			trackerEntries.add(entitytrackerentry);
			trackedEntities.a(entity.getId(), entitytrackerentry);
			entitytrackerentry.scanPlayers(world.players);
		} catch (Throwable throwable) {
			CrashReport crashreport = CrashReport.a(throwable, "Adding entity to track");
			CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Entity To Track");
			crashreportsystemdetails.a("Tracking range", String.valueOf(trackRange) + " blocks");
			int finalTrackTrange = trackRange;
			crashreportsystemdetails.a("Update interval", () -> {
				String s = "Once per " + finalTrackTrange + " ticks";
				if (finalTrackTrange == Integer.MAX_VALUE) {
					s = "Maximum (" + s + ")";
				}
				return s;
			});
			entity.appendEntityCrashDetails(crashreportsystemdetails);
			trackedEntities.get(entity.getId()).b().appendEntityCrashDetails(crashreport.a("Entity That Is Already Tracked"));
			try {
				throw new ReportedException(crashreport);
			} catch (ReportedException reportedexception) {
				Bukkit.getLogger().log(Level.SEVERE, "\"Silently\" catching entity tracking error.", reportedexception);
			}
		}
	}

	protected EntityTrackerEntry createTrackerEntry(final Entity entity, int trackRange, int updateInterval, final boolean flag) {
		return new SpigotEntityTrackerEntry(entity, trackRange, viewDistance, updateInterval, flag);
	}

	protected static final CachedInstanceOfChain<BiConsumer<SpigotEntityTracker, Entity>> entityTrackMethods = new CachedInstanceOfChain<>();
	static {
		entityTrackMethods.setKnownPath(EntityPlayer.class, (tracker, entity) -> {
			tracker.addEntity(entity, 512, 2);
			final EntityPlayer entityplayer = (EntityPlayer) entity;
			for (final EntityTrackerEntry entitytrackerentry : tracker.trackerEntries) {
				if (entitytrackerentry.b() != entityplayer) {
					entitytrackerentry.updatePlayer(entityplayer);
				}
			}
		});
		entityTrackMethods.setKnownPath(EntityFishingHook.class, (tracker, entity) -> tracker.addEntity(entity, 64, 5, true));
		entityTrackMethods.setKnownPath(EntityArrow.class, (tracker, entity) -> tracker.addEntity(entity, 64, 20, false));
		entityTrackMethods.setKnownPath(EntitySmallFireball.class, (tracker, entity) -> tracker.addEntity(entity, 64, 10, false));
		entityTrackMethods.setKnownPath(EntityFireball.class, (tracker, entity) -> tracker.addEntity(entity, 64, 10, true));
		entityTrackMethods.setKnownPath(EntitySnowball.class, (tracker, entity) -> tracker.addEntity(entity, 64, 10, true));
		entityTrackMethods.setKnownPath(EntityLlamaSpit.class, (tracker, entity) -> tracker.addEntity(entity, 64, 10, false));
		entityTrackMethods.setKnownPath(EntityEnderPearl.class, (tracker, entity) -> tracker.addEntity(entity, 64, 10, true));
		entityTrackMethods.setKnownPath(EntityEnderSignal.class, (tracker, entity) -> tracker.addEntity(entity, 64, 4, true));
		entityTrackMethods.setKnownPath(EntityEgg.class, (tracker, entity) -> tracker.addEntity(entity, 64, 10, true));
		entityTrackMethods.setKnownPath(EntityPotion.class, (tracker, entity) -> tracker.addEntity(entity, 64, 10, true));
		entityTrackMethods.setKnownPath(EntityThrownExpBottle.class, (tracker, entity) -> tracker.addEntity(entity, 64, 10, true));
		entityTrackMethods.setKnownPath(EntityFireworks.class, (tracker, entity) -> tracker.addEntity(entity, 64, 10, true));
		entityTrackMethods.setKnownPath(EntityItem.class, (tracker, entity) -> tracker.addEntity(entity, 64, 20, true));
		entityTrackMethods.setKnownPath(EntityMinecartAbstract.class, (tracker, entity) -> tracker.addEntity(entity, 80, 3, true));
		entityTrackMethods.setKnownPath(EntityBoat.class, (tracker, entity) -> tracker.addEntity(entity, 80, 3, true));
		entityTrackMethods.setKnownPath(EntitySquid.class, (tracker, entity) -> tracker.addEntity(entity, 64, 3, true));
		entityTrackMethods.setKnownPath(EntityWither.class, (tracker, entity) -> tracker.addEntity(entity, 80, 3, false));
		entityTrackMethods.setKnownPath(EntityShulkerBullet.class, (tracker, entity) -> tracker.addEntity(entity, 80, 3, true));
		entityTrackMethods.setKnownPath(EntityBat.class, (tracker, entity) -> tracker.addEntity(entity, 80, 3, false));
		entityTrackMethods.setKnownPath(EntityEnderDragon.class, (tracker, entity) -> tracker.addEntity(entity, 160, 3, true));
		entityTrackMethods.setKnownPath(IAnimal.class, (tracker, entity) -> tracker.addEntity(entity, 80, 3, true));
		entityTrackMethods.setKnownPath(EntityTNTPrimed.class, (tracker, entity) -> tracker.addEntity(entity, 160, 10, true));
		entityTrackMethods.setKnownPath(EntityFallingBlock.class, (tracker, entity) -> tracker.addEntity(entity, 160, 20, true));
		entityTrackMethods.setKnownPath(EntityHanging.class, (tracker, entity) -> tracker.addEntity(entity, 160, Integer.MAX_VALUE, false));
		entityTrackMethods.setKnownPath(EntityArmorStand.class, (tracker, entity) -> tracker.addEntity(entity, 160, 3, true));
		entityTrackMethods.setKnownPath(EntityExperienceOrb.class, (tracker, entity) -> tracker.addEntity(entity, 160, 20, true));
		entityTrackMethods.setKnownPath(EntityAreaEffectCloud.class, (tracker, entity) -> tracker.addEntity(entity, 160, Integer.MAX_VALUE, true));
		entityTrackMethods.setKnownPath(EntityEnderCrystal.class, (tracker, entity) -> tracker.addEntity(entity, 256, Integer.MAX_VALUE, false));
		entityTrackMethods.setKnownPath(EntityEvokerFangs.class, (tracker, entity) -> tracker.addEntity(entity, 160, 2, false));
		EntityTypes.REGISTRY.iterator().forEachRemaining(t -> entityTrackMethods.selectPath(t.c()));
	}

	@Override
	public void track(Entity entity) {
		BiConsumer<SpigotEntityTracker, Entity> entityTrackMethod = entityTrackMethods.selectPath(entity.getClass());
		if (entityTrackMethod != null) {
			entityTrackMethod.accept(this, entity);
		}
	}

}