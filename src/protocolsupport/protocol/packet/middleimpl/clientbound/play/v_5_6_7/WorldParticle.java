package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_5_6_7;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldParticle;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.typeremapper.particle.NetworkParticleLegacyData;
import protocolsupport.protocol.typeremapper.particle.NetworkParticleLegacyData.NetworkParticleLegacyDataTable;
import protocolsupport.protocol.typeremapper.particle.PreFlatteningNetworkParticleStringIdRegistryDataSerializer;

public class WorldParticle extends MiddleWorldParticle {

	protected final NetworkParticleLegacyDataTable legacyParticleTable = NetworkParticleLegacyData.REGISTRY.getTable(version);

	public WorldParticle(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		particle = legacyParticleTable.get(particle.getClass()).apply(particle);
		if (particle != null) {
			ClientBoundPacketData worldparticle = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_WORLD_PARTICLES);
			int count = particle.getCount();
			if (version.isBeforeOrEq(ProtocolVersion.MINECRAFT_1_6_4) && (count == 0)) {
				count = 1;
			}
			StringSerializer.writeString(worldparticle, version, PreFlatteningNetworkParticleStringIdRegistryDataSerializer.getIdData(version, particle));
			worldparticle.writeFloat((float) x);
			worldparticle.writeFloat((float) y);
			worldparticle.writeFloat((float) z);
			worldparticle.writeFloat(particle.getOffsetX());
			worldparticle.writeFloat(particle.getOffsetY());
			worldparticle.writeFloat(particle.getOffsetZ());
			worldparticle.writeFloat(particle.getData());
			worldparticle.writeInt(count);
			codec.writeClientbound(worldparticle);
		}
	}

}
