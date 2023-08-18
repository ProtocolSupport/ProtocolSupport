package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_5__7;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleWorldParticle;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV6;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV7;
import protocolsupport.protocol.typeremapper.particle.NetworkParticleLegacyData;
import protocolsupport.protocol.typeremapper.particle.NetworkParticleLegacyData.NetworkParticleLegacyDataTable;
import protocolsupport.protocol.typeremapper.particle.PreFlatteningNetworkParticleStringIdRegistryDataSerializer;

public class WorldParticle extends MiddleWorldParticle implements
IClientboundMiddlePacketV5,
IClientboundMiddlePacketV6,
IClientboundMiddlePacketV7 {

	protected final NetworkParticleLegacyDataTable legacyParticleTable = NetworkParticleLegacyData.REGISTRY.getTable(version);

	public WorldParticle(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		particle = legacyParticleTable.get(particle.getClass()).apply(particle);
		if (particle != null) {
			ClientBoundPacketData worldparticle = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_WORLD_PARTICLES);
			int count = particle.getCount();
			if (version.isBeforeOrEq(ProtocolVersion.MINECRAFT_1_6_4) && (count == 0)) {
				count = 1;
			}
			StringCodec.writeString(worldparticle, version, PreFlatteningNetworkParticleStringIdRegistryDataSerializer.getIdData(version, particle));
			worldparticle.writeFloat((float) x);
			worldparticle.writeFloat((float) y);
			worldparticle.writeFloat((float) z);
			worldparticle.writeFloat(particle.getOffsetX());
			worldparticle.writeFloat(particle.getOffsetY());
			worldparticle.writeFloat(particle.getOffsetZ());
			worldparticle.writeFloat(particle.getData());
			worldparticle.writeInt(count);
			io.writeClientbound(worldparticle);
		}
	}

}
