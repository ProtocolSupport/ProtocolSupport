package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.legacyremapper.pe.PELevelEvent;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleGameStateChange;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

/**
 * 
 * This one is a bit weird. Pocket uses some different events for this pc packet.
 * Currently only the weather changes are being updated.
 * TODO: Add the other functionality this packet provides.
 */
public class ChangeGameState extends MiddleGameStateChange {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		switch(type){
			case 1:
				packets.add(PELevelEvent.ClientLevelEvent(PELevelEvent.EVENT_STOP_RAIN));
			break;
			case 2:
				packets.add(PELevelEvent.ClientLevelEvent(PELevelEvent.EVENT_START_RAIN, 60000)); //Give it the max time. Usually the server cuts it off earlier, but we still need to wind up.
			break;
		}
		return packets;
	}
	
}