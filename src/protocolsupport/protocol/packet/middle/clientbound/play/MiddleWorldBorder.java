package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleWorldBorder extends ClientBoundMiddlePacket {

	protected Action action;
	protected double radius;
	protected double oldRadius;
	protected double newRadius;
	protected long speed;
	protected double x;
	protected double z;
	protected int teleportBound;
	protected int warnTime;
	protected int warnBlocks;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		action = Action.values()[VarNumberSerializer.readVarInt(serverdata)];
		switch (action) {
			case SET_SIZE: {
				radius = serverdata.readDouble();
				break;
			}
			case LERP_SIZE: {
				oldRadius = serverdata.readDouble();
				newRadius = serverdata.readDouble();
				speed = VarNumberSerializer.readVarLong(serverdata);
				break;
			}
			case SET_CENTER: {
				x = serverdata.readDouble();
				z = serverdata.readDouble();
				break;
			}
			case INIT: {
				x = serverdata.readDouble();
				z = serverdata.readDouble();
				oldRadius = serverdata.readDouble();
				newRadius = serverdata.readDouble();
				speed = VarNumberSerializer.readVarLong(serverdata);
				teleportBound = VarNumberSerializer.readVarInt(serverdata);
				warnTime = VarNumberSerializer.readVarInt(serverdata);
				warnBlocks = VarNumberSerializer.readVarInt(serverdata);
				break;
			}
			case SET_WARN_TIME: {
				warnTime = VarNumberSerializer.readVarInt(serverdata);
				break;
			}
			case SET_WARN_BLOCKS: {
				warnBlocks = VarNumberSerializer.readVarInt(serverdata);
				break;
			}
		}
	}

	protected static enum Action {
		SET_SIZE, LERP_SIZE, SET_CENTER, INIT, SET_WARN_TIME, SET_WARN_BLOCKS
	}

}
