package protocolsupport.protocol.typeremapper.watchedentity.remapper.value;

import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectSVarLong;

public class IndexValueRemapperNumberToSVarLong extends IndexValueRemapper<DataWatcherObject<?>> {

	public IndexValueRemapperNumberToSVarLong(int fromIndex, int toIndex) {
		super(fromIndex, toIndex);
	}

	@Override
	public DataWatcherObject<?> remapValue(DataWatcherObject<?> object) {
		Number number = (Number) object.getValue();
		return new DataWatcherObjectSVarLong(number.longValue());
	}

}