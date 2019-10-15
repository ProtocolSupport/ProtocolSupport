package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_5_6_7;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldParticle;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.typeremapper.legacy.LegacyParticle;
import protocolsupport.protocol.typeremapper.particle.ParticleRemapper;
import protocolsupport.protocol.typeremapper.particle.ParticleRemapper.ParticleRemappingTable;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class WorldParticle extends MiddleWorldParticle {

	protected final ParticleRemappingTable remapper = ParticleRemapper.REGISTRY.getTable(version);

	public WorldParticle(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<? extends IPacketData> toData() {
		particle = remapper.getRemap(particle.getClass()).apply(particle);
		if (particle == null) {
			return RecyclableEmptyList.get();
		}
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_WORLD_PARTICLES);
		int count = particle.getCount();
		if (version.isBeforeOrEq(ProtocolVersion.MINECRAFT_1_6_4) && (count == 0)) {
			count = 1;
		}
		StringSerializer.writeString(serializer, version, LegacyParticle.StringId.getIdData(particle));
		serializer.writeFloat(x);
		serializer.writeFloat(y);
		serializer.writeFloat(z);
		serializer.writeFloat(particle.getOffsetX());
		serializer.writeFloat(particle.getOffsetY());
		serializer.writeFloat(particle.getOffsetZ());
		serializer.writeFloat(particle.getData());
		serializer.writeInt(count);
		return RecyclableSingletonList.create(serializer);
	}

}
