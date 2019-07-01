package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_14;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockTileUpdate;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.typeremapper.tile.TileEntityRemapper;
import protocolsupport.protocol.types.TileEntity;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class BlockTileUpdate extends MiddleBlockTileUpdate {

	protected final TileEntityRemapper tileRemapper = TileEntityRemapper.getRemapper(version);

	public BlockTileUpdate(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		return RecyclableSingletonList.create(create(version, tileRemapper.remap(tile)));
	}

	public static ClientBoundPacketData create(ProtocolVersion version, TileEntity tile) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_UPDATE_TILE_ID);
		PositionSerializer.writePosition(serializer, tile.getPosition());
		serializer.writeByte(tile.getType().getNetworkId());
		ItemStackSerializer.writeTag(serializer, version, tile.getNBT());
		return serializer;
	}

}
