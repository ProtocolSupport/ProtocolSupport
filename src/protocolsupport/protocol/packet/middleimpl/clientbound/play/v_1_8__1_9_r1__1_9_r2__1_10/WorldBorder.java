package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8__1_9_r1__1_9_r2__1_10;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldBorder;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class WorldBorder extends MiddleWorldBorder<RecyclableCollection<ClientBoundPacketData>> {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_WORLD_BORDER_ID, version);
		serializer.writeVarInt(action.ordinal());
		switch (action) {
			case SET_SIZE: {
				serializer.writeDouble(radius);
				break;
			}
			case LERP_SIZE: {
				serializer.writeDouble(oldRadius);
				serializer.writeDouble(newRadius);
				serializer.writeVarLong(speed);
				break;
			}
			case SET_CENTER: {
				serializer.writeDouble(x);
				serializer.writeDouble(z);
				break;
			}
			case INIT: {
				serializer.writeDouble(x);
				serializer.writeDouble(z);
				serializer.writeDouble(oldRadius);
				serializer.writeDouble(newRadius);
				serializer.writeVarLong(speed);
				serializer.writeVarInt(teleportBound);
				serializer.writeVarInt(warnTime);
				serializer.writeVarInt(warnBlocks);
				break;
			}
			case SET_WARN_TIME: {
				serializer.writeVarInt(warnTime);
				break;
			}
			case SET_WARN_BLOCKS: {
				serializer.writeVarInt(warnBlocks);
				break;
			}
		}
		return RecyclableSingletonList.create(serializer);
	}

}
