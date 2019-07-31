package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_9r2_10_11_12r1_12r2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13.AbstractChunkLight;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.chunk.ChunkWriterVariesWithLight;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.protocol.types.TileEntity;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class ChunkLight extends AbstractChunkLight {

	protected final ArrayBasedIdRemappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);

	public ChunkLight(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<? extends IPacketData> toData() {
		if (preChunk) {
			return RecyclableEmptyList.get();
		} else {
			int blockMask = ((setSkyLightMask | setBlockLightMask | emptySkyLightMask | emptyBlockLightMask) >> 1) & 0xFFFF;
			boolean hasSkyLight = cache.getAttributesCache().hasSkyLightInCurrentDimension();
			List<Collection<TileEntity>> resendTiles = new ArrayList<>();

			ClientBoundPacketData serializer = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_CHUNK_SINGLE);
			PositionSerializer.writeIntChunkCoord(serializer, coord);
			serializer.writeBoolean(false); //full
			VarNumberSerializer.writeVarInt(serializer, blockMask);
			ArraySerializer.writeVarIntByteArray(serializer, to -> {
				ChunkWriterVariesWithLight.writeSectionsPreFlattening(
					to, blockMask, 13,
					blockDataRemappingTable,
					cachedChunk, hasSkyLight,
					sectionNumber -> resendTiles.add(cachedChunk.getTiles(sectionNumber).values())
				);
			});
			ArraySerializer.writeVarIntTArray(serializer, lTo -> {
				int count = 0;
				for (Collection<TileEntity> sectionTiles : resendTiles) {
					for (TileEntity tile : sectionTiles) {
						ItemStackSerializer.writeTag(lTo, version, tile.getNBT());
					}
					count += sectionTiles.size();
				}
				return count;
			});

			return RecyclableSingletonList.create(serializer);
		}
	}

}
