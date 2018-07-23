package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.typeremapper.particle.ParticleRemapper;
import protocolsupport.protocol.utils.types.particle.Particle;

public abstract class MiddleWorldParticle extends ClientBoundMiddlePacket {

	protected Particle particle;
	protected boolean longdist;
	protected float x;
	protected float y;
	protected float z;
	protected float offX;
	protected float offY;
	protected float offZ;
	protected float speed;
	protected int count;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		particle = Particle.fromId(serverdata.readInt());
		longdist = serverdata.readBoolean();
		x = serverdata.readFloat();
		y = serverdata.readFloat();
		z = serverdata.readFloat();
		offX = serverdata.readFloat();
		offY = serverdata.readFloat();
		offZ = serverdata.readFloat();
		speed = serverdata.readFloat();
		count = serverdata.readInt();
		particle.readData(serverdata);
		particle = ParticleRemapper.remap(connection.getVersion(), particle);
		particle.remap(connection.getVersion(), cache.getAttributesCache().getLocale());
	}

	@Override
	public boolean postFromServerRead() {
		return particle.getId() != Particle.SKIP;
	}

}
