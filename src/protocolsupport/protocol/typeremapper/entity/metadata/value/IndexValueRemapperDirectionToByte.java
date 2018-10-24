package protocolsupport.protocol.typeremapper.entity.metadata.value;

import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectByte;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectDirection;

public final class IndexValueRemapperDirectionToByte extends IndexValueRemapper<DataWatcherObjectDirection> {

	public IndexValueRemapperDirectionToByte(DataWatcherObjectIndex<DataWatcherObjectDirection> fromIndex, int toIndex) {
		super(fromIndex, toIndex);
	}

	@Override
	public DataWatcherObject<?> remapValue(DataWatcherObjectDirection object) {
		return new DataWatcherObjectByte((byte) object.getValue().ordinal());
	}

}
