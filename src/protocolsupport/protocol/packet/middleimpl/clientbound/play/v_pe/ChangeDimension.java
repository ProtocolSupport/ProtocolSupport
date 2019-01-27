package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import java.text.MessageFormat;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChangeDimension;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.login.v_pe.LoginSuccess;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.utils.networkentity.NetworkEntity;
import protocolsupport.protocol.utils.types.ChunkCoord;
import protocolsupport.protocol.utils.types.Environment;
import protocolsupport.protocol.utils.types.WindowType;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class ChangeDimension extends MiddleChangeDimension {

	public ChangeDimension(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public boolean postFromServerRead() {
		cache.getPEChunkMapCache().clear();
		return super.postFromServerRead();
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		if (cache.getWindowCache().getOpenedWindow() != WindowType.PLAYER) {
			packets.add(InventoryClose.create(connection.getVersion(), cache.getWindowCache().getOpenedWindowId()));
			cache.getWindowCache().closeWindow();
		}
		addChangeDimension(packets, cache.getWatchedEntityCache().getSelfPlayer(), dimension);
		return packets;
	}

	public static ClientBoundPacketData createRaw(float x, float y, float z, int dimension) {
		ClientBoundPacketData changedim = ClientBoundPacketData.create(PEPacketIDs.CHANGE_DIMENSION);
		VarNumberSerializer.writeSVarInt(changedim, dimension);
		changedim.writeFloatLE(x); //x
		changedim.writeFloatLE(y); //y
		changedim.writeFloatLE(z); //z
		changedim.writeBoolean(true); //respawn
		return changedim;
	}

	public static void addChangeDimension(RecyclableCollection<ClientBoundPacketData> packets, NetworkEntity player, Environment dimension) {
		packets.add(createRaw(8, 18, 8, getPeDimensionId(dimension)));
		packets.add(Chunk.createChunkPublisherUpdate(0, 0, 0));
		Chunk.addFakeChunks(packets, new ChunkCoord(0, 0));
		packets.add(SetPosition.create(player, 0, 18, 0, 0, 0, 0, SetPosition.ANIMATION_MODE_ALL));
		packets.add(LoginSuccess.createPlayStatus(LoginSuccess.PLAYER_SPAWN));
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
