package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13_14r1_14r2_15_16r1_16r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleDeclareTags;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockData;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockData.FlatteningBlockDataEntry;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockData.FlatteningBlockDataTable;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.itemstack.FlatteningItemId;
import protocolsupport.protocol.typeremapper.itemstack.LegacyItemType;
import protocolsupport.protocol.typeremapper.utils.MappingTable.IdMappingTable;
import protocolsupport.protocol.utils.BlockBlockDataLookup;

//TODO: implement special handling for entity tags
public abstract class AbstractDeclareTags extends MiddleDeclareTags {

	public AbstractDeclareTags(ConnectionImpl connection) {
		super(connection);
	}

	protected void writeBlocksTags(ByteBuf to, Tag[] tags) {
		IdMappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);
		FlatteningBlockDataTable flatteningBlockDataTable = FlatteningBlockData.REGISTRY.getTable(version);
		ArraySerializer.writeVarIntTArray(to, tags, (lTo, tag) -> {
			StringSerializer.writeVarIntUTF8String(lTo, tag.getTagId());
			ArraySerializer.writeVarIntTArray(lTo, llTo -> {
				int[] blocksIds = tag.getTaggedIds();
				int count = blocksIds.length;
				for (int blockId : blocksIds) {
					int blockData = BlockBlockDataLookup.getBlockDataId(blockId);
					if (blockDataRemappingTable.get(blockData) == blockData) {
						FlatteningBlockDataEntry fEntry = flatteningBlockDataTable.getRemap(blockData);
						VarNumberSerializer.writeVarInt(llTo, fEntry != null ? fEntry.getBlockId() : blockId);
					} else {
						count--;
					}
				}
				return count;
			});
		});
	}

	protected void writeItemsTags(ByteBuf to, Tag[] tags) {
		IdMappingTable itemTypeRemappingTable = LegacyItemType.REGISTRY.getTable(version);
		IdMappingTable flatteningItemTypeTable = FlatteningItemId.REGISTRY_TO_CLIENT.getTable(version);
		ArraySerializer.writeVarIntTArray(to, tags, (lTo, tag) -> {
			StringSerializer.writeVarIntUTF8String(lTo, tag.getTagId());
			ArraySerializer.writeVarIntTArray(lTo, llTo -> {
				int[] itemsIds = tag.getTaggedIds();
				int count = itemsIds.length;
				for (int itemId : itemsIds) {
					if (itemTypeRemappingTable.get(itemId) == itemId) {
						VarNumberSerializer.writeVarInt(llTo, flatteningItemTypeTable.get(itemId));
					} else {
						count--;
					}
				}
				return count;
			});
		});
	}

	protected static void writeTags(ByteBuf to, Tag[] tags) {
		ArraySerializer.writeVarIntTArray(to, tags, (lTo, tag) -> {
			StringSerializer.writeVarIntUTF8String(lTo, tag.getTagId());
			ArraySerializer.writeVarIntVarIntArray(lTo, tag.getTaggedIds());
		});
	}

}
