package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleScoreboardObjective;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.utils.Utils;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class ScoreboardObjective extends MiddleScoreboardObjective {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_SCOREBOARD_OBJECTIVE_ID);
		StringSerializer.writeString(serializer, version, name);
		serializer.writeByte(mode.ordinal());
		if (mode != Mode.REMOVE) {
			StringSerializer.writeString(serializer, version, Utils.clampString(value.toLegacyText(cache.getAttributesCache().getLocale()), 32));
			StringSerializer.writeString(serializer, version, getTypeString(type));
		}
		return RecyclableSingletonList.create(serializer);
	}

	protected static String getTypeString(Type type) {
		switch (type) {
			case HEARTS: {
				return "hearts";
			}
			case INTEGER:
			default: {
				return "integer";
			}
		}
	}

}
