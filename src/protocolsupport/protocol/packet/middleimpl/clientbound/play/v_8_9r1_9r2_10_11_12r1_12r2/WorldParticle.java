package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2;

import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldParticle;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.typeremapper.particle.NetworkParticleLegacyData;
import protocolsupport.protocol.typeremapper.particle.NetworkParticleLegacyData.NetworkParticleLegacyDataTable;
import protocolsupport.protocol.typeremapper.particle.PreFlatteningNetworkParticleIntIdRegistryDataSerializer;

public class WorldParticle extends MiddleWorldParticle {

	protected final NetworkParticleLegacyDataTable legacyParticleTable = NetworkParticleLegacyData.REGISTRY.getTable(version);

	public WorldParticle(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		particle = legacyParticleTable.get(particle.getClass()).apply(particle);
		if (particle != null) {
			ClientBoundPacketData spawnparticle = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_WORLD_PARTICLES);
			spawnparticle.writeInt(PreFlatteningNetworkParticleIntIdRegistryDataSerializer.getId(particle));
			spawnparticle.writeBoolean(longdist);
			spawnparticle.writeFloat((float) x);
			spawnparticle.writeFloat((float) y);
			spawnparticle.writeFloat((float) z);
			spawnparticle.writeFloat(particle.getOffsetX());
			spawnparticle.writeFloat(particle.getOffsetY());
			spawnparticle.writeFloat(particle.getOffsetZ());
			spawnparticle.writeFloat(particle.getData());
			spawnparticle.writeInt(particle.getCount());
			for (int data : PreFlatteningNetworkParticleIntIdRegistryDataSerializer.getData(version, particle)) {
				VarNumberCodec.writeVarInt(spawnparticle, data);
			}
			codec.writeClientbound(spawnparticle);
		}
	}

}
