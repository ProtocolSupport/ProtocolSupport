package protocolsupport.protocol.typeremapper.entity.metadata.types.living.horse;

import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityMetadata.PeMetaBase;
import protocolsupport.protocol.typeremapper.entity.metadata.DataWatcherObjectRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.AgeableEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNumberToInt;
import protocolsupport.protocol.typeremapper.entity.metadata.value.PeFlagRemapper;
import protocolsupport.protocol.typeremapper.pe.PEDataValues;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectByte;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectSVarInt;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectVarInt;
import protocolsupport.protocol.utils.networkentity.NetworkEntity;
import protocolsupport.protocol.utils.types.WindowType;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public abstract class BaseHorseEntityMetadataRemapper extends AgeableEntityMetadataRemapper {

	public BaseHorseEntityMetadataRemapper() {
		addRemap(new PeFlagRemapper(DataWatcherObjectIndex.BaseHorse.FLAGS,
				new int[] {2, 3, 3, 3, 4, /*5,*/ 6, 7},
				new int[] {PeMetaBase.FLAG_TAMED, PeMetaBase.FLAG_SADDLED, PeMetaBase.FLAG_WASD_CONTROLLED, PeMetaBase.FLAG_CAN_POWER_JUMP, PeMetaBase.FLAG_IN_LOVE, /*PeMetaBase.FLAG_USING_ITEM,*/ PeMetaBase.FLAG_REARING, PeMetaBase.FLAG_BREATHING}
			), ProtocolVersionsHelper.ALL_PE);
		addRemap(new DataWatcherObjectRemapper(){
			@Override
			public void remap(NetworkEntity entity, ArrayMap<DataWatcherObject<?>> original, ArrayMap<DataWatcherObject<?>> remapped) {
				DataWatcherObjectIndex.BaseHorse.FLAGS.getValue(original).ifPresent(byteWatcher -> {
					remapped.put(PeMetaBase.EATING_HAYSTACK, new DataWatcherObjectVarInt(((byteWatcher.getValue() & (1 << (6-1))) != 0) ? 0b100000 : 0));
					if ((byteWatcher.getValue() & (1 << (2-1))) != 0) {
						//When tamed set these weird properties to make the inventory work. FFS Mojang.
						remapped.put(PeMetaBase.HORSE_CONTAINER_TYPE, new DataWatcherObjectByte((byte) PEDataValues.WINDOWTYPE.getTable(ProtocolVersionsHelper.LATEST_PE).getRemap(WindowType.HORSE.toLegacyId()))); //Inventory Type
						remapped.put(PeMetaBase.HORSE_ANIMAL_SLOTS, new DataWatcherObjectSVarInt(2)); //Animal slots (left side of the image)
					}
				});
			}
		}, ProtocolVersionsHelper.ALL_PE);

		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.BaseHorse.FLAGS, 13), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.BaseHorse.FLAGS, 12), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.BaseHorse.FLAGS, 16), ProtocolVersionsHelper.BEFORE_1_9);
	}

}
