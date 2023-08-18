package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_20;

import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleWorldParticle;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV20;
import protocolsupport.protocol.typeremapper.particle.FlatteningNetworkParticleDataSerializer;
import protocolsupport.protocol.typeremapper.particle.FlatteningNetworkParticleIdRegistry;
import protocolsupport.protocol.typeremapper.particle.NetworkParticleLegacyData;
import protocolsupport.protocol.typeremapper.particle.NetworkParticleLegacyData.NetworkParticleLegacyDataTable;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;
import protocolsupport.protocol.types.particle.NetworkParticle;
import protocolsupport.protocol.types.particle.NetworkParticleRegistry;
import protocolsupport.protocol.utils.TypeSerializer;

public class WorldParticle extends MiddleWorldParticle implements
IClientboundMiddlePacketV20 {

	protected final NetworkParticleLegacyDataTable remapper = NetworkParticleLegacyData.REGISTRY.getTable(version);
	protected final ArrayBasedIntMappingTable flatteningIdTable = FlatteningNetworkParticleIdRegistry.INSTANCE.getTable(version);
	protected final TypeSerializer.Entry<NetworkParticle> dataSerializer = FlatteningNetworkParticleDataSerializer.INSTANCE.get(version);

	public WorldParticle(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		particle = remapper.get(particle.getClass()).apply(particle);
		if (particle != null) {
			ClientBoundPacketData particlePacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_WORLD_PARTICLES);
			VarNumberCodec.writeVarInt(particlePacket, flatteningIdTable.get(NetworkParticleRegistry.getId(particle)));
			particlePacket.writeBoolean(longdist);
			particlePacket.writeDouble(x);
			particlePacket.writeDouble(y);
			particlePacket.writeDouble(z);
			particlePacket.writeFloat(particle.getOffsetX());
			particlePacket.writeFloat(particle.getOffsetY());
			particlePacket.writeFloat(particle.getOffsetZ());
			particlePacket.writeFloat(particle.getData());
			particlePacket.writeInt(particle.getCount());
			dataSerializer.write(particlePacket, particle);
			io.writeClientbound(particlePacket);
		}
	}

}
