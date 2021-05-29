package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13_14r1_14r2_15_16r1_16r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleDeclareTags;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.block.BlockDataLegacyDataRegistry;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockDataRegistry;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockDataRegistry.FlatteningBlockDataEntry;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockDataRegistry.FlatteningBlockDataTable;
import protocolsupport.protocol.typeremapper.itemstack.FlatteningItemId;
import protocolsupport.protocol.typeremapper.itemstack.legacy.ItemStackLegacyData;
import protocolsupport.protocol.typeremapper.itemstack.legacy.ItemStackLegacyData.ItemStackLegacyDataTable;
import protocolsupport.protocol.typeremapper.utils.MappingTable.IdMappingTable;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.utils.BlockBlockDataLookup;

//TODO: implement special handling for entity tags
public abstract class AbstractDeclareTags extends MiddleDeclareTags {

	protected AbstractDeclareTags(MiddlePacketInit init) {
		super(init);
	}

	protected void writeBlocksTags(ByteBuf to, Tag[] tags) {
		IdMappingTable blockDataRemappingTable = BlockDataLegacyDataRegistry.INSTANCE.getTable(version);
		FlatteningBlockDataTable flatteningBlockDataTable = FlatteningBlockDataRegistry.INSTANCE.getTable(version);
		ArraySerializer.writeVarIntTArray(to, tags, (tagsTo, tag) -> {
			StringSerializer.writeVarIntUTF8String(tagsTo, tag.getTagId());
			MiscSerializer.writeVarIntCountPrefixedType(tagsTo, tag.getTaggedIds(), (taggedIdsTo, blocksIds) -> {
				int count = blocksIds.length;
				for (int blockId : blocksIds) {
					int blockData = BlockBlockDataLookup.getBlockDataId(blockId);
					if (blockDataRemappingTable.get(blockData) == blockData) {
						FlatteningBlockDataEntry fEntry = flatteningBlockDataTable.get(blockData);
						VarNumberSerializer.writeVarInt(taggedIdsTo, fEntry != null ? fEntry.getBlockId() : blockId);
					} else {
						count--;
					}
				}
				return count;
			});
		});
	}

	protected void writeItemsTags(ByteBuf to, Tag[] tags) {
		ItemStackLegacyDataTable legacyitemdataTable = ItemStackLegacyData.REGISTRY.getTable(version);
		IdMappingTable flatteningItemTypeTable = FlatteningItemId.REGISTRY_TO_CLIENT.getTable(version);
		ArraySerializer.writeVarIntTArray(to, tags, (tagsTo, tag) -> {
			StringSerializer.writeVarIntUTF8String(tagsTo, tag.getTagId());
			MiscSerializer.writeVarIntCountPrefixedType(tagsTo, tag.getTaggedIds(), (taggedIdsTo, itemsIds) -> {
				int count = itemsIds.length;
				for (int itemId : itemsIds) {
					if (itemId == legacyitemdataTable.apply(new NetworkItemStack(itemId)).getTypeId()) {
						VarNumberSerializer.writeVarInt(taggedIdsTo, flatteningItemTypeTable.get(itemId));
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
