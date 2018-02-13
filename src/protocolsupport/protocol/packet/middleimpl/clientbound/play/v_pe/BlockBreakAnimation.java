package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import gnu.trove.map.hash.TIntObjectHashMap;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockBreakAnimation;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.typeremapper.pe.PELevelEvent;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class BlockBreakAnimation extends MiddleBlockBreakAnimation {

	private final TIntObjectHashMap<TimePle> animations = new TIntObjectHashMap<>();

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		if ((stage >= 1) && (stage <= 9)) {
			TimePle animation = animations.get(entityId);
			if (animation == null) {
				animations.put(entityId, new TimePle(false, System.nanoTime(), stage));
			} else {
				if (!animation.getStarted() && (stage > animation.getStartStage())) {
					animation.setStarted(true);
					double breakMils = Math.ceil(((((System.nanoTime() - animation.getStartTime()) / (stage - animation.getStartStage())) * (9 - animation.getStartStage())) + 0.1) / 1000000);
					int ticksToBreak = (int) (Math.ceil(((breakMils / 1000) + 0.25) * 20));
					return RecyclableSingletonList.create(PELevelEvent.createPacket(PELevelEvent.BLOCK_START_BREAK, position, 65535 / ticksToBreak));
				}
			}
		} else {
			animations.remove(entityId);
			return RecyclableSingletonList.create(PELevelEvent.createPacket(PELevelEvent.BLOCK_STOP_BREAK, position));
		}
		return RecyclableEmptyList.get();
	}

	//Tuple for AnimationTime.
	private class TimePle {

		private boolean started;
		private final long starttime;
		private final int startstage;

		public TimePle(boolean started, long starttime, int startstage) {
			this.started = started;
			this.starttime = starttime;
			this.startstage = startstage;
		}

		public boolean getStarted() {
			return started;
		}

		public void setStarted(boolean started) {
			this.started = started;
		}

		public long getStartTime() {
			return starttime;
		}

		public int getStartStage() {
			return startstage;
		}

	}
}
