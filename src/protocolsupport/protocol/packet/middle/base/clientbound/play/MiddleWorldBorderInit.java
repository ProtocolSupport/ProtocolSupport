package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;

public abstract class MiddleWorldBorderInit extends ClientBoundMiddlePacket {

	protected MiddleWorldBorderInit(IMiddlePacketInit init) {
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
		speed = VarNumberCodec.readVarLong(serverdata);
		teleportBound = VarNumberCodec.readVarInt(serverdata);
		warnDistance = VarNumberCodec.readVarInt(serverdata);
		warnDelay = VarNumberCodec.readVarInt(serverdata);
	}

}
