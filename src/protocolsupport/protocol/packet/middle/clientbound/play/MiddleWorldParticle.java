package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.types.particle.Particle;
import protocolsupport.protocol.types.particle.ParticleRegistry;

public abstract class MiddleWorldParticle extends ClientBoundMiddlePacket {

	public MiddleWorldParticle(ConnectionImpl connection) {
		super(connection);
	}

	protected Particle particle;
	protected boolean longdist;
	protected float x;
	protected float y;
	protected float z;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		particle = ParticleRegistry.fromId(serverdata.readInt());
		longdist = serverdata.readBoolean();
		x = serverdata.readFloat();
		y = serverdata.readFloat();
		z = serverdata.readFloat();
		particle.read(serverdata);
	}

}
