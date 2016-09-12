package protocolsupport.protocol.typeremapper.watchedentity.remapper.value;

import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectBlockState;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectBoolean;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectByte;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectChat;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectDirection;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectFloat;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectItemStack;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectOptionalPosition;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectOptionalUUID;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectPosition;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectString;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectVarInt;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectVector3f;

public abstract class ValueRemapperNoOp<T extends DataWatcherObject<?>> extends ValueRemapper<T> {

	public static final ValueRemapperNoOp<DataWatcherObjectByte> BYTE = new ValueRemapperNoOp<DataWatcherObjectByte>(){};
	public static final ValueRemapperNoOp<DataWatcherObjectVarInt> VARINT = new ValueRemapperNoOp<DataWatcherObjectVarInt>(){};
	public static final ValueRemapperNoOp<DataWatcherObjectFloat> FLOAT  = new ValueRemapperNoOp<DataWatcherObjectFloat>(){};
	public static final ValueRemapperNoOp<DataWatcherObjectString> STRING = new ValueRemapperNoOp<DataWatcherObjectString>(){};
	public static final ValueRemapperNoOp<DataWatcherObjectChat> CHAT = new ValueRemapperNoOp<DataWatcherObjectChat>(){};
	public static final ValueRemapperNoOp<DataWatcherObjectItemStack> ITEMSTACK = new ValueRemapperNoOp<DataWatcherObjectItemStack>(){};
	public static final ValueRemapperNoOp<DataWatcherObjectBoolean> BOOLEAN = new ValueRemapperNoOp<DataWatcherObjectBoolean>(){};
	public static final ValueRemapperNoOp<DataWatcherObjectVector3f> VECTOR3F = new ValueRemapperNoOp<DataWatcherObjectVector3f>(){};
	public static final ValueRemapperNoOp<DataWatcherObjectPosition> POSITION = new ValueRemapperNoOp<DataWatcherObjectPosition>(){};
	public static final ValueRemapperNoOp<DataWatcherObjectOptionalPosition> OPTIONAL_POSITION = new ValueRemapperNoOp<DataWatcherObjectOptionalPosition>(){};
	public static final ValueRemapperNoOp<DataWatcherObjectDirection> DIRECTION = new ValueRemapperNoOp<DataWatcherObjectDirection>(){};
	public static final ValueRemapperNoOp<DataWatcherObjectOptionalUUID> OPTIONAL_UUID = new ValueRemapperNoOp<DataWatcherObjectOptionalUUID>(){};
	public static final ValueRemapperNoOp<DataWatcherObjectBlockState> BLOCKSTATE = new ValueRemapperNoOp<DataWatcherObjectBlockState>(){};

	private ValueRemapperNoOp() {
	}

	@Override
	public DataWatcherObject<?> remap(T object) {
		return object;
	}

}
