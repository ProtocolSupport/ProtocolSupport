package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import gnu.trove.map.hash.TIntObjectHashMap;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockBreakAnimation;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.typeremapper.pe.PELevelEvent;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class BlockBreakAnimation extends MiddleBlockBreakAnimation {

	private final TIntObjectHashMap<TimePle> animation = new TIntObjectHashMap<>();
	
	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		if (stage >= 1 && stage <= 9) {
			if (!animation.containsKey(entityId)) {
				animation.put(entityId, new TimePle(false, System.nanoTime(), stage));
			} else {
				if (!animation.get(entityId).getStarted() && stage > animation.get(entityId).getStartStage()) {
					animation.get(entityId).setStarted(true);
					return RecyclableSingletonList.create(PELevelEvent.createPacket(PELevelEvent.BLOCK_START_BREAK, position, (int) (
							65535 / Math.ceil(((((System.nanoTime() - animation.get(entityId).getStartTime()) / (stage - animation.get(entityId).getStartStage())) * (9 - animation.get(entityId).getStartStage())) / 1000000000) * 20 + 12.5)
						)));	
				}
			}
		} else {
			if (animation.containsKey(entityId)) {
				animation.remove(entityId);
			}
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
