package protocolsupport.protocol.typeremapper.watchedentity.remapper.value;

import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectFloatLe;

public class IndexValueRemapperNumberToFloatLe extends IndexValueRemapper<DataWatcherObject<?>> {

	public IndexValueRemapperNumberToFloatLe(int fromIndex, int toIndex) {
		super(fromIndex, toIndex);
	}

	@Override
	public DataWatcherObject<?> remapValue(DataWatcherObject<?> object) {
		Number number = (Number) object.getValue();
		return new DataWatcherObjectFloatLe(number.floatValue());
	}

}