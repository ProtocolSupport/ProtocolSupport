package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import java.text.MessageFormat;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleRespawn;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.login.v_pe.LoginSuccess;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.utils.types.Environment;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class Respawn extends MiddleRespawn {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		create(connection.getVersion(), dimension, cache.getSelfPlayerEntityId(), cache.getFakeSetPositionY(), packets);
		return packets;
	}

	public static void create(ProtocolVersion version, Environment dimension, int playerEntityId, double posY, RecyclableCollection<ClientBoundPacketData> packets) {
		ClientBoundPacketData changedim = ClientBoundPacketData.create(PEPacketIDs.CHANGE_DIMENSION, version);
		VarNumberSerializer.writeSVarInt(changedim, getPeDimensionId(dimension));
		changedim.writeFloatLE(0); //x
		changedim.writeFloatLE(0); //y
		changedim.writeFloatLE(0); //z
		changedim.writeBoolean(true); //respawn
		packets.add(changedim);
		packets.add(LoginSuccess.createPlayStatus(version, 3));
		addFakeChunksAndPos(version, playerEntityId, posY, packets);
	}

	public static void addFakeChunksAndPos(ProtocolVersion version, int playerEntityId, double posY, RecyclableCollection<ClientBoundPacketData> packets) {
		for (int x = -2; x <= 2; x++) {
			for (int z = -2; z <= 2; z++) {
				packets.add(Chunk.createEmptyChunk(version, x, z));
			}
		}
		packets.add(Position.create(version, playerEntityId, 0, posY, 0, 0, 0, Position.ANIMATION_MODE_TELEPORT));
	}

	public static int getPeDimensionId(Environment dimId) {
		switch (dimId) {
			case NETHER: {
				return 1;
			}
			case THE_END: {
				return 2;
			}
			case OVERWORLD: {
				return 0;
			}
			default: {
				throw new IllegalArgumentException(MessageFormat.format("Uknown dim id {0}", dimId));
			}
		}
	}

}
