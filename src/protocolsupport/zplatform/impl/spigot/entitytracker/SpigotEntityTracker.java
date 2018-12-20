package protocolsupport.zplatform.impl.spigot.entitytracker;

import java.lang.reflect.Field;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.spigotmc.AsyncCatcher;
import org.spigotmc.TrackingRange;

import net.minecraft.server.v1_13_R2.CrashReport;
import net.minecraft.server.v1_13_R2.CrashReportSystemDetails;
import net.minecraft.server.v1_13_R2.Entity;
import net.minecraft.server.v1_13_R2.EntityAreaEffectCloud;
import net.minecraft.server.v1_13_R2.EntityArmorStand;
import net.minecraft.server.v1_13_R2.EntityArrow;
import net.minecraft.server.v1_13_R2.EntityBat;
import net.minecraft.server.v1_13_R2.EntityBoat;
import net.minecraft.server.v1_13_R2.EntityEgg;
import net.minecraft.server.v1_13_R2.EntityEnderCrystal;
import net.minecraft.server.v1_13_R2.EntityEnderDragon;
import net.minecraft.server.v1_13_R2.EntityEnderPearl;
import net.minecraft.server.v1_13_R2.EntityEnderSignal;
import net.minecraft.server.v1_13_R2.EntityEvokerFangs;
import net.minecraft.server.v1_13_R2.EntityExperienceOrb;
import net.minecraft.server.v1_13_R2.EntityFallingBlock;
import net.minecraft.server.v1_13_R2.EntityFireball;
import net.minecraft.server.v1_13_R2.EntityFireworks;
import net.minecraft.server.v1_13_R2.EntityFishingHook;
import net.minecraft.server.v1_13_R2.EntityHanging;
import net.minecraft.server.v1_13_R2.EntityItem;
import net.minecraft.server.v1_13_R2.EntityLlamaSpit;
import net.minecraft.server.v1_13_R2.EntityMinecartAbstract;
import net.minecraft.server.v1_13_R2.EntityPlayer;
import net.minecraft.server.v1_13_R2.EntityPotion;
import net.minecraft.server.v1_13_R2.EntityShulkerBullet;
import net.minecraft.server.v1_13_R2.EntitySmallFireball;
import net.minecraft.server.v1_13_R2.EntitySnowball;
import net.minecraft.server.v1_13_R2.EntitySquid;
import net.minecraft.server.v1_13_R2.EntityTNTPrimed;
import net.minecraft.server.v1_13_R2.EntityThrownExpBottle;
import net.minecraft.server.v1_13_R2.EntityTracker;
import net.minecraft.server.v1_13_R2.EntityTrackerEntry;
import net.minecraft.server.v1_13_R2.EntityWither;
import net.minecraft.server.v1_13_R2.IAnimal;
import net.minecraft.server.v1_13_R2.IRegistry;
import net.minecraft.server.v1_13_R2.PlayerChunkMap;
import net.minecraft.server.v1_13_R2.ReportedException;
import net.minecraft.server.v1_13_R2.WorldServer;
import protocolsupport.utils.CachedInstanceOfChain;
import protocolsupport.utils.ReflectionUtils;

@SuppressWarnings("unchecked")
public class SpigotEntityTracker extends EntityTracker {

	protected static final Field trackerEntriesField = ReflectionUtils.getField(EntityTracker.class, "c");

	protected final WorldServer world;
	protected final Set<EntityTrackerEntry> trackerEntries;
	protected final int maxTrackRange;

	public SpigotEntityTracker(WorldServer worldserver) {
		super(worldserver);
		world = worldserver;
		maxTrackRange = PlayerChunkMap.getFurthestViewableBlock(worldserver.spigotConfig.viewDistance);
		try {
			trackerEntries = (Set<EntityTrackerEntry>) trackerEntriesField.get(this);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException("Unable to get needed field", e);
		}
	}

	@Override
	public void addEntity(Entity entity, int defaultTrackRange, int updateInterval, boolean updateVelocity) {
		AsyncCatcher.catchOp("entity track");
		defaultTrackRange = TrackingRange.getEntityTrackingRange(entity, defaultTrackRange);
		try {
			if (trackedEntities.b(entity.getId())) {
				throw new IllegalStateException("Entity is already tracked!");
			}
			EntityTrackerEntry entitytrackerentry = createTrackerEntry(entity, defaultTrackRange, updateInterval, updateVelocity);
			trackerEntries.add(entitytrackerentry);
			trackedEntities.a(entity.getId(), entitytrackerentry);
			entitytrackerentry.scanPlayers(world.players);
		} catch (Throwable throwable) {
			CrashReport crashreport = CrashReport.a(throwable, "Adding entity to track");
			CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Entity To Track");
			crashreportsystemdetails.a("Tracking range", String.valueOf(defaultTrackRange) + " blocks");
			int finalTrackRange = defaultTrackRange;
			crashreportsystemdetails.a("Update interval", () -> {
				String s = "Once per " + finalTrackRange + " ticks";
				if (finalTrackRange == Integer.MAX_VALUE) {
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

	protected EntityTrackerEntry createTrackerEntry(final Entity entity, int defaultTrackRange, int updateInterval, final boolean flag) {
		return new SpigotEntityTrackerEntry(entity, defaultTrackRange, maxTrackRange, updateInterval, flag);
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
		IRegistry.ENTITY_TYPE.f().forEach(et -> entityTrackMethods.selectPath(et.c()));
	}

	@Override
	public void track(Entity entity) {
		BiConsumer<SpigotEntityTracker, Entity> entityTrackMethod = entityTrackMethods.selectPath(entity.getClass());
		if (entityTrackMethod != null) {
			entityTrackMethod.accept(this, entity);
		}
	}

}
