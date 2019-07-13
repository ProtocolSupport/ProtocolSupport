package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldBorder;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class WorldBorder extends MiddleWorldBorder {

	public WorldBorder(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_WORLD_BORDER_ID);
		VarNumberSerializer.writeVarInt(serializer, action.ordinal());
		switch (action) {
			case SET_SIZE: {
				serializer.writeDouble(radius);
				break;
			}
			case LERP_SIZE: {
				serializer.writeDouble(oldRadius);
				serializer.writeDouble(newRadius);
				VarNumberSerializer.writeVarLong(serializer, speed);
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
				VarNumberSerializer.writeVarLong(serializer, speed);
				VarNumberSerializer.writeVarInt(serializer, teleportBound);
				VarNumberSerializer.writeVarInt(serializer, warnTime);
				VarNumberSerializer.writeVarInt(serializer, warnBlocks);
				break;
			}
			case SET_WARN_TIME: {
				VarNumberSerializer.writeVarInt(serializer, warnTime);
				break;
			}
			case SET_WARN_BLOCKS: {
				VarNumberSerializer.writeVarInt(serializer, warnBlocks);
				break;
			}
		}
		return RecyclableSingletonList.create(serializer);
	}

}
