package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;
import protocolsupport.protocol.types.Position;

public abstract class MiddleExplosion extends ClientBoundMiddlePacket {

	protected MiddleExplosion(IMiddlePacketInit init) {
		super(init);
	}

	protected double x;
	protected double y;
	protected double z;
	protected float radius;
	protected Position[] blocks;
	protected float pMotX;
	protected float pMotY;
	protected float pMotZ;

	@Override
	protected void decode(ByteBuf serverdata) {
		x = serverdata.readDouble();
		y = serverdata.readDouble();
		z = serverdata.readDouble();
		radius = serverdata.readFloat();
		blocks = ArrayCodec.readVarIntTArray(serverdata, Position.class, posFrom -> new Position(posFrom.readByte(), posFrom.readByte(), posFrom.readByte()));
		pMotX = serverdata.readFloat();
		pMotY = serverdata.readFloat();
		pMotZ = serverdata.readFloat();
	}

}
