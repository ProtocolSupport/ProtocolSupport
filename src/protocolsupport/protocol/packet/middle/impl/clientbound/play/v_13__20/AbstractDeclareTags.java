package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_13__20;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleDeclareTags;
import protocolsupport.protocol.typeremapper.basic.TagsTransformer;
import protocolsupport.protocol.typeremapper.block.BlockDataLegacyDataRegistry;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockDataRegistry;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockDataRegistry.FlatteningBlockDataEntry;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockDataRegistry.FlatteningBlockDataTable;
import protocolsupport.protocol.typeremapper.entity.FlatteningNetworkEntityIdRegistry;
import protocolsupport.protocol.typeremapper.entity.legacy.NetworkEntityLegacyDataRegistry;
import protocolsupport.protocol.typeremapper.entity.legacy.NetworkEntityLegacyDataRegistry.NetworkEntityLegacyDataTable;
import protocolsupport.protocol.typeremapper.itemstack.FlatteningItemId;
import protocolsupport.protocol.typeremapper.itemstack.legacy.ItemStackLegacyData;
import protocolsupport.protocol.typeremapper.itemstack.legacy.ItemStackLegacyData.ItemStackLegacyDataTable;
import protocolsupport.protocol.typeremapper.utils.MappingTable.GenericMappingTable;
import protocolsupport.protocol.typeremapper.utils.MappingTable.IntMappingTable;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ThrowingArrayBasedIntTable;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.protocol.utils.BlockBlockDataLookup;

public abstract class AbstractDeclareTags extends MiddleDeclareTags {

	protected AbstractDeclareTags(IMiddlePacketInit init) {
		super(init);
	}

	protected final GenericMappingTable<String> tagsBlockMappingTable = TagsTransformer.BLOCK.getTable(version);

	protected void writeBlocksTags(ByteBuf to, Tag[] tags) {
		IntMappingTable blockdataLegacyDataTable = BlockDataLegacyDataRegistry.INSTANCE.getTable(version);
		FlatteningBlockDataTable flatteningBlockDataTable = FlatteningBlockDataRegistry.INSTANCE.getTable(version);
		ArrayCodec.writeVarIntTArray(to, tags, (tagsTo, tag) -> {
			StringCodec.writeVarIntUTF8String(tagsTo, tagsBlockMappingTable.get(tag.getTagId()));
			MiscDataCodec.writeVarIntCountPrefixedType(tagsTo, tag.getTaggedIds(), (taggedIdsTo, blocksIds) -> {
				int count = blocksIds.length;
				for (int blockId : blocksIds) {
					int blockData = BlockBlockDataLookup.getBlockDataId(blockId);
					if (blockdataLegacyDataTable.get(blockData) == blockData) {
						FlatteningBlockDataEntry fEntry = flatteningBlockDataTable.get(blockData);
						VarNumberCodec.writeVarInt(taggedIdsTo, fEntry != null ? fEntry.getBlockId() : blockId);
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
		IntMappingTable flatteningItemTypeTable = FlatteningItemId.REGISTRY_TO_CLIENT.getTable(version);
		ArrayCodec.writeVarIntTArray(to, tags, (tagsTo, tag) -> {
			StringCodec.writeVarIntUTF8String(tagsTo, tag.getTagId());
			MiscDataCodec.writeVarIntCountPrefixedType(tagsTo, tag.getTaggedIds(), (taggedIdsTo, itemsIds) -> {
				int count = itemsIds.length;
				for (int itemId : itemsIds) {
					if (itemId == legacyitemdataTable.apply(new NetworkItemStack(itemId)).getTypeId()) {
						VarNumberCodec.writeVarInt(taggedIdsTo, flatteningItemTypeTable.get(itemId));
					} else {
						count--;
					}
				}
				return count;
			});
		});
	}

	protected void writeEntityTags(ByteBuf to, Tag[] tags) {
		NetworkEntityLegacyDataTable entityLegacyDataTable = NetworkEntityLegacyDataRegistry.INSTANCE.getTable(version);
		ThrowingArrayBasedIntTable flatteningEntityIdTable = FlatteningNetworkEntityIdRegistry.INSTANCE.getTable(version);
		ArrayCodec.writeVarIntTArray(to, tags, (tagsTo, tag) -> {
			StringCodec.writeVarIntUTF8String(tagsTo, tag.getTagId());
			MiscDataCodec.writeVarIntCountPrefixedType(tagsTo, tag.getTaggedIds(), (taggedIdsTo, entitiesIds) -> {
				int count = entitiesIds.length;
				for (int entityId : entitiesIds) {
					NetworkEntityType type = NetworkEntityType.getByNetworkTypeId(entityId);
					if ((type != NetworkEntityType.NONE) && (entityLegacyDataTable.get(type).getType().getNetworkTypeId() == entityId)) {
						VarNumberCodec.writeVarInt(taggedIdsTo, flatteningEntityIdTable.get(entityId));
					} else {
						count--;
					}
				}
				return count;
			});
		});
	}

	protected static void writeTags(ByteBuf to, Tag[] tags) {
		ArrayCodec.writeVarIntTArray(to, tags, (lTo, tag) -> {
			StringCodec.writeVarIntUTF8String(lTo, tag.getTagId());
			ArrayCodec.writeVarIntVarIntArray(lTo, tag.getTaggedIds());
		});
	}

}
