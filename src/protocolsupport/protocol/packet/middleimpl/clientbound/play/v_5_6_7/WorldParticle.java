package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_5_6_7;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldParticle;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class WorldParticle extends MiddleWorldParticle {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_WORLD_PARTICLES_ID);
// TODO: implement the special cases in ParticleRemapper.
//		String name = type.getName();
//		switch (type) {
//			case ITEM_CRACK: {
//				//TODO:
//				IntTuple iddata = ItemStackRemapper.REGISTRY.getTable(version).getRemap(adddata.get(0), adddata.get(1));
//				if (iddata != null) {
//					name += "_" + iddata.getI1() + "_" + (iddata.getI2() != -1 ? iddata.getI2() : adddata.get(1));
//				} else {
//					name += "_" + adddata.get(0) + "_" +  adddata.get(1);
//				}
//				break;
//			}
//			case BLOCK_CRACK:
//			case BLOCK_DUST: {
//				//TODO:
//				int blockstate = adddata.get(0);
//				blockstate = IdRemapper.BLOCK.getTable(version).getRemap(MinecraftData.getBlockStateFromIdAndData(blockstate & 4095, blockstate >> 12));
//				name += "_" + MinecraftData.getBlockIdFromState(blockstate) + "_" + MinecraftData.getBlockDataFromState(blockstate);
//				break;
//			}
//			default: {
//				break;
//			}
//		}
		if (version.isBeforeOrEq(ProtocolVersion.MINECRAFT_1_6_4) && (count == 0)) {
			count = 1;
		}
		StringSerializer.writeString(serializer, version, particle.getName());
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
