package protocolsupport.protocol.typeremapper.entity.metadata.value;

import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectVarInt;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectVillagerData;

public class IndexValueRemapperVillagerDataToVarInt extends IndexValueRemapper<DataWatcherObjectVillagerData> {

	public IndexValueRemapperVillagerDataToVarInt(DataWatcherObjectIndex<DataWatcherObjectVillagerData> fromIndex, int toIndex) {
		super(fromIndex, toIndex);
	}

	@Override
	public DataWatcherObject<?> remapValue(DataWatcherObjectVillagerData object) {
		return new DataWatcherObjectVarInt(object.getValue().getProfession());
	}

}
