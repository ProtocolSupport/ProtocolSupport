package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_8__12r2;

import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleWorldParticle;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV10;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV11;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV12r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV12r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV8;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV9r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV9r2;
import protocolsupport.protocol.typeremapper.particle.NetworkParticleLegacyData;
import protocolsupport.protocol.typeremapper.particle.NetworkParticleLegacyData.NetworkParticleLegacyDataTable;
import protocolsupport.protocol.typeremapper.particle.PreFlatteningNetworkParticleIntIdRegistryDataSerializer;

public class WorldParticle extends MiddleWorldParticle  implements
IClientboundMiddlePacketV8,
IClientboundMiddlePacketV9r1,
IClientboundMiddlePacketV9r2,
IClientboundMiddlePacketV10,
IClientboundMiddlePacketV11,
IClientboundMiddlePacketV12r1,
IClientboundMiddlePacketV12r2 {

	protected final NetworkParticleLegacyDataTable legacyParticleTable = NetworkParticleLegacyData.REGISTRY.getTable(version);

	public WorldParticle(IMiddlePacketInit init) {
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
			io.writeClientbound(spawnparticle);
		}
	}

}
