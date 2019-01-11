package protocolsupport.protocol.typeremapper.entity.metadata.types.object.minecart;

import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityMetadata.PeMetaBase;
import protocolsupport.protocol.typeremapper.entity.metadata.DataWatcherObjectRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperBooleanToByte;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectByte;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectSVarInt;
import protocolsupport.protocol.utils.networkentity.NetworkEntity;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class MinecartFurnaceEntityMetadataRemapper extends MinecartEntityMetadataRemapper {

	public static final MinecartFurnaceEntityMetadataRemapper INSTANCE = new MinecartFurnaceEntityMetadataRemapper();

	public MinecartFurnaceEntityMetadataRemapper() {
		addRemap(new DataWatcherObjectRemapper() {
			@Override
			public void remap(NetworkEntity entity, ArrayMap<DataWatcherObject<?>> original, ArrayMap<DataWatcherObject<?>> remapped) {
				//Simulate furnaceMinecart in Pocket.
				//TODO Fix id shizzle.
				remapped.put(PeMetaBase.MINECART_BLOCK, new DataWatcherObjectSVarInt(61));
				DataWatcherObjectIndex.MinecartFurnace.POWERED.getValue(original).ifPresent(boolWatcher -> {if(boolWatcher.getValue()) {
					remapped.put(PeMetaBase.MINECART_BLOCK, new DataWatcherObjectSVarInt(62));
				}});
				remapped.put(PeMetaBase.MINECART_OFFSET, new DataWatcherObjectSVarInt(6));
				remapped.put(PeMetaBase.MINECART_DISPLAY, new DataWatcherObjectByte((byte) 1));
			}
		}, ProtocolVersionsHelper.ALL_PE);

		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.MinecartFurnace.POWERED, 12), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.MinecartFurnace.POWERED, 11), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.MinecartFurnace.POWERED, 16), ProtocolVersionsHelper.BEFORE_1_9);
	}

}
