package protocolsupport.zplatform.impl.spigot.entitytracker;

import java.lang.reflect.Field;
import java.util.Set;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.spigotmc.AsyncCatcher;
import org.spigotmc.TrackingRange;

import net.minecraft.server.v1_12_R1.CrashReport;
import net.minecraft.server.v1_12_R1.CrashReportSystemDetails;
import net.minecraft.server.v1_12_R1.Entity;
import net.minecraft.server.v1_12_R1.EntityTracker;
import net.minecraft.server.v1_12_R1.EntityTrackerEntry;
import net.minecraft.server.v1_12_R1.ReportedException;
import net.minecraft.server.v1_12_R1.WorldServer;
import protocolsupport.utils.ReflectionUtils;

public class SpigotEntityTracker extends EntityTracker {

	private static final Field trackerEntriesField = ReflectionUtils.getField(EntityTracker.class, "c");

	private final WorldServer world;
	private final Set<EntityTrackerEntry> trackerEntries;
	private final int viewDistance;

	@SuppressWarnings("unchecked")
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

}
