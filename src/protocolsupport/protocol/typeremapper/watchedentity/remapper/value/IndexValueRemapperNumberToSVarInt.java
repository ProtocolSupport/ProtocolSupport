package protocolsupport.protocol.typeremapper.watchedentity.remapper.value;

import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectSVarInt;

//Since Minecraft PE has so many values just shifted one to the left I remapped it this way.
public final class IndexValueRemapperNumberToSVarInt extends IndexValueRemapper<DataWatcherObject<?>> {

	public IndexValueRemapperNumberToSVarInt(int fromIndex, int toIndex) {
		super(fromIndex, toIndex);
	}

	@Override
	public DataWatcherObject<?> remapValue(DataWatcherObject<?> object) {
		Number number = (Number) object.getValue();
		return new DataWatcherObjectSVarInt(number.intValue());
	}

}
