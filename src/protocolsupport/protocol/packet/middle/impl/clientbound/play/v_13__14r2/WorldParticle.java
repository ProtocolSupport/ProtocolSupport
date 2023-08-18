package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_13__14r2;

import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleWorldParticle;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV13;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV14r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV14r2;
import protocolsupport.protocol.typeremapper.particle.FlatteningNetworkParticleDataSerializer;
import protocolsupport.protocol.typeremapper.particle.FlatteningNetworkParticleIdRegistry;
import protocolsupport.protocol.typeremapper.particle.NetworkParticleLegacyData;
import protocolsupport.protocol.typeremapper.particle.NetworkParticleLegacyData.NetworkParticleLegacyDataTable;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;
import protocolsupport.protocol.types.particle.NetworkParticle;
import protocolsupport.protocol.types.particle.NetworkParticleRegistry;
import protocolsupport.protocol.utils.TypeSerializer;

public class WorldParticle extends MiddleWorldParticle implements
IClientboundMiddlePacketV13,
IClientboundMiddlePacketV14r1,
IClientboundMiddlePacketV14r2 {

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
			ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_WORLD_PARTICLES);
			serializer.writeInt(flatteningIdTable.get(NetworkParticleRegistry.getId(particle)));
			serializer.writeBoolean(longdist);
			serializer.writeFloat((float) x);
			serializer.writeFloat((float) y);
			serializer.writeFloat((float) z);
			serializer.writeFloat(particle.getOffsetX());
			serializer.writeFloat(particle.getOffsetY());
			serializer.writeFloat(particle.getOffsetZ());
			serializer.writeFloat(particle.getData());
			serializer.writeInt(particle.getCount());
			dataSerializer.write(serializer, particle);
			io.writeClientbound(serializer);
		}
	}

}
