package protocolsupport.protocol.typeremapper.watchedentity.remapper.value;

import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectByte;
import protocolsupport.protocol.utils.datawatcher.objects.ReadableDataWatcherObjectNumber;

public final class IndexValueRemapperNumberToByte extends IndexValueRemapper<ReadableDataWatcherObjectNumber<?>> {

	public IndexValueRemapperNumberToByte(int fromIndex, int toIndex) {
		super(fromIndex, toIndex);
	}

	@Override
	public DataWatcherObject<?> remapValue(ReadableDataWatcherObjectNumber<?> object) {
		return new DataWatcherObjectByte(object.getValue().byteValue());
	}

}
