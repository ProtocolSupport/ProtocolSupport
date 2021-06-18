package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.types.Position;

public abstract class MiddleExplosion extends ClientBoundMiddlePacket {

	protected MiddleExplosion(MiddlePacketInit init) {
		super(init);
	}

	protected float x;
	protected float y;
	protected float z;
	protected float radius;
	protected Position[] blocks;
	protected float pMotX;
	protected float pMotY;
	protected float pMotZ;

	@Override
	protected void decode(ByteBuf serverdata) {
		x = serverdata.readFloat();
		y = serverdata.readFloat();
		z = serverdata.readFloat();
		radius = serverdata.readFloat();
		blocks = ArrayCodec.readVarIntTArray(serverdata, Position.class, posFrom -> new Position(posFrom.readByte(), posFrom.readByte(), posFrom.readByte()));
		pMotX = serverdata.readFloat();
		pMotY = serverdata.readFloat();
		pMotZ = serverdata.readFloat();
	}

}
