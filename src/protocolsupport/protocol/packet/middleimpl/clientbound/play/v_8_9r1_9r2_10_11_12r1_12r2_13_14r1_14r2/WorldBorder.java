package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldBorder;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class WorldBorder extends MiddleWorldBorder {

	public WorldBorder(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData worldborder = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_WORLD_BORDER);
		VarNumberSerializer.writeVarInt(worldborder, action.ordinal());
		switch (action) {
			case SET_SIZE: {
				worldborder.writeDouble(radius);
				break;
			}
			case LERP_SIZE: {
				worldborder.writeDouble(oldRadius);
				worldborder.writeDouble(newRadius);
				VarNumberSerializer.writeVarLong(worldborder, speed);
				break;
			}
			case SET_CENTER: {
				worldborder.writeDouble(x);
				worldborder.writeDouble(z);
				break;
			}
			case INIT: {
				worldborder.writeDouble(x);
				worldborder.writeDouble(z);
				worldborder.writeDouble(oldRadius);
				worldborder.writeDouble(newRadius);
				VarNumberSerializer.writeVarLong(worldborder, speed);
				VarNumberSerializer.writeVarInt(worldborder, teleportBound);
				VarNumberSerializer.writeVarInt(worldborder, warnTime);
				VarNumberSerializer.writeVarInt(worldborder, warnBlocks);
				break;
			}
			case SET_WARN_TIME: {
				VarNumberSerializer.writeVarInt(worldborder, warnTime);
				break;
			}
			case SET_WARN_BLOCKS: {
				VarNumberSerializer.writeVarInt(worldborder, warnBlocks);
				break;
			}
		}
		codec.write(worldborder);
	}

}
