package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldParticle;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.legacy.LegacyParticle;
import protocolsupport.protocol.typeremapper.particle.ParticleRemapper;
import protocolsupport.protocol.typeremapper.particle.ParticleRemapper.ParticleRemappingTable;

public class WorldParticle extends MiddleWorldParticle {

	protected final ParticleRemappingTable remapper = ParticleRemapper.REGISTRY.getTable(version);

	public WorldParticle(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeToClient() {
		particle = remapper.getRemap(particle.getClass()).apply(particle);
		if (particle != null) {
			ClientBoundPacketData spawnparticle = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_WORLD_PARTICLES);
			spawnparticle.writeInt(LegacyParticle.IntId.getId(particle));
			spawnparticle.writeBoolean(longdist);
			spawnparticle.writeFloat((float) x);
			spawnparticle.writeFloat((float) y);
			spawnparticle.writeFloat((float) z);
			spawnparticle.writeFloat(particle.getOffsetX());
			spawnparticle.writeFloat(particle.getOffsetY());
			spawnparticle.writeFloat(particle.getOffsetZ());
			spawnparticle.writeFloat(particle.getData());
			spawnparticle.writeInt(particle.getCount());
			for (int data : LegacyParticle.IntId.getData(particle)) {
				VarNumberSerializer.writeVarInt(spawnparticle, data);
			}
			codec.write(spawnparticle);
		}
	}

}
