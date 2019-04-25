package protocolsupport.protocol.utils.datawatcher.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.datawatcher.ReadableDataWatcherObject;
import protocolsupport.protocol.utils.types.VillagerData;

public class DataWatcherObjectVillagerData extends ReadableDataWatcherObject<VillagerData> {

	@Override
	public void readFromStream(ByteBuf from) {
		value = new VillagerData(VarNumberSerializer.readVarInt(from), VarNumberSerializer.readVarInt(from), VarNumberSerializer.readVarInt(from));
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		VarNumberSerializer.writeVarInt(to, value.getType());
		VarNumberSerializer.writeVarInt(to, value.getProfession());
		VarNumberSerializer.writeVarInt(to, value.getLevel());
	}

}
