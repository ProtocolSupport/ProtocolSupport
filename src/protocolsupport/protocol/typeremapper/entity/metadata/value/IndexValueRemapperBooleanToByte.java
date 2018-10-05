package protocolsupport.protocol.typeremapper.entity.metadata.value;

import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectBoolean;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectByte;

public class IndexValueRemapperBooleanToByte extends IndexValueRemapper<DataWatcherObjectBoolean> {

	public IndexValueRemapperBooleanToByte(DataWatcherObjectIndex<DataWatcherObjectBoolean> fromIndex, int toIndex) {
		super(fromIndex, toIndex);
	}

	@Override
	public DataWatcherObject<?> remapValue(DataWatcherObjectBoolean object) {
		return new DataWatcherObjectByte((byte) (object.getValue() ? 1 : 0));
	}

}
