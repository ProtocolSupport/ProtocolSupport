package protocolsupport.zplatform.impl.paper.entitytracker;

import net.minecraft.server.v1_12_R1.Entity;
import net.minecraft.server.v1_12_R1.EntityTrackerEntry;
import net.minecraft.server.v1_12_R1.WorldServer;
import protocolsupport.zplatform.impl.spigot.entitytracker.SpigotEntityTracker;

public class PaperEntityTracker extends SpigotEntityTracker {

	public PaperEntityTracker(WorldServer worldserver) {
		super(worldserver);
	}

	@Override
	public EntityTrackerEntry createTrackerEntry(final Entity entity, int i, final int j, final boolean flag) {
		return new PaperEntityTrackerEntry(entity, i, this.getViewDistance(), j, flag);
	}
}
