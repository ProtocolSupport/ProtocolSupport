package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.typeremapper.particle.ParticleRemapper;
import protocolsupport.protocol.utils.types.particle.Particle;
import protocolsupport.protocol.utils.types.particle.ParticleRegistry;

public abstract class MiddleWorldParticle extends ClientBoundMiddlePacket {

	public MiddleWorldParticle(ConnectionImpl connection) {
		super(connection);
	}

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
		particle = ParticleRegistry.fromId(serverdata.readInt());
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
	}

	@Override
	public boolean postFromServerRead() {
		return particle.getId() != ParticleRegistry.ID_SKIP;
	}

}
