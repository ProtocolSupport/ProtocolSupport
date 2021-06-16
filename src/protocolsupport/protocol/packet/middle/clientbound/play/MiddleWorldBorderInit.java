package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleWorldBorderInit extends ClientBoundMiddlePacket {

	protected MiddleWorldBorderInit(MiddlePacketInit init) {
		super(init);
	}

	protected double x;
	protected double z;
	protected double oldSize;
	protected double newSize;
	protected long speed;
	protected int teleportBound;
	protected int warnDistance;
	protected int warnDelay;

	@Override
	protected void decode(ByteBuf serverdata) {
		x = serverdata.readDouble();
		z = serverdata.readDouble();
		oldSize = serverdata.readDouble();
		newSize = serverdata.readDouble();
		speed = VarNumberSerializer.readVarLong(serverdata);
		teleportBound = VarNumberSerializer.readVarInt(serverdata);
		warnDistance = VarNumberSerializer.readVarInt(serverdata);
		warnDelay = VarNumberSerializer.readVarInt(serverdata);
	}

}
