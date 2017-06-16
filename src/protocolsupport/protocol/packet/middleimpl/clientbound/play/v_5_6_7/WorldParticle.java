package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_5_6_7;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldParticle;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackRemapper;
import protocolsupport.protocol.utils.types.Particle;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class WorldParticle extends MiddleWorldParticle {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_WORLD_PARTICLES_ID, version);
		Particle particle = Particle.getById(type);
		String name = particle.getName();
		switch (particle) {
			case ITEM_CRACK: {
				name += ItemStackRemapper.ITEM_ID_REMAPPING_REGISTRY.getTable(version).getRemap(adddata.get(0));
				break;
			}
			case BLOCK_CRACK:
			case BLOCK_DUST: {
				int blockstateId = adddata.get(0);
				name += "_" + (IdRemapper.BLOCK.getTable(version).getRemap((blockstateId & 4095) << 4) >> 4) + "_" + ((blockstateId >> 12) & 0xF);
				break;
			}
			default: {
				break;
			}
		}
		StringSerializer.writeString(serializer, version, name);
		serializer.writeFloat(x);
		serializer.writeFloat(y);
		serializer.writeFloat(z);
		serializer.writeFloat(offX);
		serializer.writeFloat(offY);
		serializer.writeFloat(offZ);
		serializer.writeFloat(speed);
		serializer.writeInt(count);
		return RecyclableSingletonList.create(serializer);
	}

}
