package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldBorder;
import protocolsupport.protocol.packet.middleimpl.PacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class WorldBorder extends MiddleWorldBorder<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) throws IOException {
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_WORLD_BORDER_ID, version);
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
