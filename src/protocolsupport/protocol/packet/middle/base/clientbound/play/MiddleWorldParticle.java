package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;
import protocolsupport.protocol.types.particle.NetworkParticle;
import protocolsupport.protocol.types.particle.NetworkParticleRegistry;

public abstract class MiddleWorldParticle extends ClientBoundMiddlePacket {

	protected MiddleWorldParticle(IMiddlePacketInit init) {
		super(init);
	}

	protected NetworkParticle particle;
	protected boolean longdist;
	protected double x;
	protected double y;
	protected double z;

	@Override
	protected void decode(ByteBuf serverdata) {
		particle = NetworkParticleRegistry.fromId(serverdata.readInt());
		longdist = serverdata.readBoolean();
		x = serverdata.readDouble();
		y = serverdata.readDouble();
		z = serverdata.readDouble();
		particle.read(serverdata);
	}

}
