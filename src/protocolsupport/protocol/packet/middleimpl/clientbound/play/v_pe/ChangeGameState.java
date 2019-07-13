package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleGameStateChange;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEAdventureSettings;
import protocolsupport.protocol.typeremapper.pe.PELevelEvent;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.types.GameMode;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

//TODO: implement other events and functions
public class ChangeGameState extends MiddleGameStateChange {

	public ChangeGameState(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
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
				int gamemode = (int) value;
				cache.getAttributesCache().setPEGameMode(GameMode.getById(gamemode));
				ClientBoundPacketData changeGameType = ClientBoundPacketData.create(PEPacketIDs.CHANGE_PLAYER_GAMETYPE);
				VarNumberSerializer.writeSVarInt(changeGameType, gamemode);
				packets.add(changeGameType);
				packets.add(PEAdventureSettings.createPacket(cache));
				break;
			}
		}
		return packets;
	}

}