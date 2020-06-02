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
	protected double x;
	protected double y;
	protected double z;

	@Override
	protected void readServerData(ByteBuf serverdata) {
		particle = ParticleRegistry.fromId(serverdata.readInt());
		longdist = serverdata.readBoolean();
		x = serverdata.readDouble();
		y = serverdata.readDouble();
		z = serverdata.readDouble();
		particle.read(serverdata);
	}

}
