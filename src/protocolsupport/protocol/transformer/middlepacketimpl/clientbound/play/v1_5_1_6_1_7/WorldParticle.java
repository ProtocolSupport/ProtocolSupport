package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v1_5_1_6_1_7;

import java.util.Collection;
import java.util.Collections;

import net.minecraft.server.v1_8_R3.EnumParticle;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleWorldParticle;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;
import protocolsupport.protocol.typeremapper.id.IdRemapper;

public class WorldParticle extends MiddleWorldParticle<Collection<PacketData>> {

	@Override
	public Collection<PacketData> toData(ProtocolVersion version) {
		PacketDataSerializer serializer = PacketDataSerializer.createNew(version);
		EnumParticle particle = EnumParticle.values()[type];
		String name = particle.b();
		switch (particle) {
			case ITEM_CRACK: {
				name += IdRemapper.ITEM.getTable(version).getRemap(adddata.get(0));
				break;
			}
			case BLOCK_CRACK:
			case BLOCK_DUST: {
				int blockstateId = adddata.get(0);
				name += IdRemapper.BLOCK.getTable(version).getRemap((blockstateId & 4095)) + "_" + ((blockstateId >> 12) & 0xF);
				break;
			}
			default: {
				break;
			}
		}
		serializer.writeString(name);
		serializer.writeFloat(x);
		serializer.writeFloat(y);
		serializer.writeFloat(z);
		serializer.writeFloat(offX);
		serializer.writeFloat(offY);
		serializer.writeFloat(offZ);
		serializer.writeFloat(speed);
		serializer.writeInt(count);
		return Collections.singletonList(new PacketData(ClientBoundPacket.PLAY_WORLD_PARTICLES_ID, serializer));
	}

}
