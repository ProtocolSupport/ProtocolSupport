package protocolsupport.protocol.typeremapper.watchedentity.remapper.value;

import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectInt;

public final class IndexValueRemapperNumberToInt extends IndexValueRemapper<DataWatcherObject<?>> {

	public IndexValueRemapperNumberToInt(int fromIndex, int toIndex) {
		super(fromIndex, toIndex);
	}

	@Override
	public DataWatcherObject<?> remapValue(DataWatcherObject<?> object) {
		Number number = (Number) object.getValue();
		return new DataWatcherObjectInt(number.intValue());
	}

}
