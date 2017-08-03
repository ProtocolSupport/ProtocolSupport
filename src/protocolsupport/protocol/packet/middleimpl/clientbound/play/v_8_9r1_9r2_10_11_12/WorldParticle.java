package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldParticle;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.id.IdSkipper;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class WorldParticle extends MiddleWorldParticle {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		if (IdSkipper.PARTICLE.getTable(version).shouldSkip(type)) {
			return RecyclableEmptyList.get();
		}
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_WORLD_PARTICLES_ID, version);
		serializer.writeInt(type.getId());
		serializer.writeBoolean(longdist);
		serializer.writeFloat(x);
		serializer.writeFloat(y);
		serializer.writeFloat(z);
		serializer.writeFloat(offX);
		serializer.writeFloat(offY);
		serializer.writeFloat(offZ);
		serializer.writeFloat(speed);
		serializer.writeInt(count);
		for (int additional : adddata) {
			VarNumberSerializer.writeVarInt(serializer, additional);
		}
		return RecyclableSingletonList.create(serializer);
	}

}
