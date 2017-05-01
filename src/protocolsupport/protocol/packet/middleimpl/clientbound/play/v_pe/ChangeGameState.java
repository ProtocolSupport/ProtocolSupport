package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.legacyremapper.pe.PELevelEvent;
import protocolsupport.protocol.legacyremapper.pe.PEPacketIDs;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleGameStateChange;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

//TODO: implement other events and functions
public class ChangeGameState extends MiddleGameStateChange {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		switch (type) {
			case 1: {
				packets.add(PELevelEvent.createPacket(PELevelEvent.STOP_RAIN));
				break;
			}
			case 2: {
				packets.add(PELevelEvent.createPacket(PELevelEvent.START_RAIN, 60000));
				break;
			}
			case 3: {
				//TODO: Emulate adventure gamemode via AdventureSettingsPacket
				ClientBoundPacketData changeGameType = ClientBoundPacketData.create(PEPacketIDs.CHANGE_PLAYER_GAMETYPE, version);
				VarNumberSerializer.writeSVarInt(changeGameType, (int) value);
				packets.add(changeGameType);
				break;
			}
		}
		return packets;
	}

}