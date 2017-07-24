package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockBreakAnimation;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.typeremapper.pe.PELevelEvent;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class BlockBreakAnimation extends MiddleBlockBreakAnimation {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		if (stage >= 0 && stage <= 9) {
			return RecyclableSingletonList.create(PELevelEvent.createPacket(PELevelEvent.BLOCK_START_BREAK, position, stage));
		} else {
			return RecyclableSingletonList.create(PELevelEvent.createPacket(PELevelEvent.BLOCK_STOP_BREAK, position));
		}
	}

}
