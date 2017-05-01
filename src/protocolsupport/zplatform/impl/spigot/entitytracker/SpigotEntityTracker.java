package protocolsupport.zplatform.impl.spigot.entitytracker;

import java.lang.reflect.Field;
import java.util.Set;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.spigotmc.AsyncCatcher;
import org.spigotmc.TrackingRange;

import net.minecraft.server.v1_11_R1.CrashReport;
import net.minecraft.server.v1_11_R1.CrashReportSystemDetails;
import net.minecraft.server.v1_11_R1.Entity;
import net.minecraft.server.v1_11_R1.EntityTracker;
import net.minecraft.server.v1_11_R1.EntityTrackerEntry;
import net.minecraft.server.v1_11_R1.ReportedException;
import net.minecraft.server.v1_11_R1.WorldServer;
import protocolsupport.utils.ReflectionUtils;

public class SpigotEntityTracker extends EntityTracker {

	private static final Field trackerEntriesField = ReflectionUtils.getField(EntityTracker.class, "c");

	private final WorldServer world;
	private final Set<EntityTrackerEntry> trackerEntries;
	private final int viewDistance;

	@SuppressWarnings("unchecked")
	public SpigotEntityTracker(WorldServer worldserver) {
		super(worldserver);
		this.world = worldserver;
		this.viewDistance = worldserver.getMinecraftServer().getPlayerList().d();
		try {
			this.trackerEntries = (Set<EntityTrackerEntry>) trackerEntriesField.get(this);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException("Unable to get needed field", e);
		}
	}

	@Override
	public void addEntity(final Entity entity, int i, final int j, final boolean flag) {
		AsyncCatcher.catchOp("entity track");
		i = TrackingRange.getEntityTrackingRange(entity, i);
		try {
			if (this.trackedEntities.b(entity.getId())) {
				throw new IllegalStateException("Entity is already tracked!");
			}
			final EntityTrackerEntry entitytrackerentry = new SpigotEntityTrackerEntry(entity, i, this.viewDistance, j, flag);
			this.trackerEntries.add(entitytrackerentry);
			this.trackedEntities.a(entity.getId(), entitytrackerentry);
			entitytrackerentry.scanPlayers(this.world.players);
		} catch (Throwable throwable) {
			final CrashReport crashreport = CrashReport.a(throwable, "Adding entity to track");
			final CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Entity To Track");
			crashreportsystemdetails.a("Tracking range", String.valueOf(i) + " blocks");
			final int finalI = i;
			crashreportsystemdetails.a("Update interval", () -> {
				String s = "Once per " + finalI + " ticks";
				if (finalI == Integer.MAX_VALUE) {
					s = "Maximum (" + s + ")";
				}
				return s;
			});
			entity.appendEntityCrashDetails(crashreportsystemdetails);
			this.trackedEntities.get(entity.getId()).b().appendEntityCrashDetails(crashreport.a("Entity That Is Already Tracked"));
			try {
				throw new ReportedException(crashreport);
			} catch (ReportedException reportedexception) {
				Bukkit.getLogger().log(Level.SEVERE, "\"Silently\" catching entity tracking error.", reportedexception);
			}
		}
	}

}
