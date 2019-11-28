package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_5_6_7;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldParticle;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.typeremapper.legacy.LegacyParticle;
import protocolsupport.protocol.typeremapper.particle.ParticleRemapper;
import protocolsupport.protocol.typeremapper.particle.ParticleRemapper.ParticleRemappingTable;

public class WorldParticle extends MiddleWorldParticle {

	protected final ParticleRemappingTable remapper = ParticleRemapper.REGISTRY.getTable(version);

	public WorldParticle(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		particle = remapper.getRemap(particle.getClass()).apply(particle);
		if (particle != null) {
			ClientBoundPacketData worldparticle = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_WORLD_PARTICLES);
			int count = particle.getCount();
			if (version.isBeforeOrEq(ProtocolVersion.MINECRAFT_1_6_4) && (count == 0)) {
				count = 1;
			}
			StringSerializer.writeString(worldparticle, version, LegacyParticle.StringId.getIdData(particle));
			worldparticle.writeFloat(x);
			worldparticle.writeFloat(y);
			worldparticle.writeFloat(z);
			worldparticle.writeFloat(particle.getOffsetX());
			worldparticle.writeFloat(particle.getOffsetY());
			worldparticle.writeFloat(particle.getOffsetZ());
			worldparticle.writeFloat(particle.getData());
			worldparticle.writeInt(count);
			codec.write(worldparticle);
		}
	}

}
