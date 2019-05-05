package protocolsupport.protocol.types.networkentity.metadata.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.types.VillagerData;
import protocolsupport.protocol.types.networkentity.metadata.ReadableNetworkEntityMetadataObject;

public class NetworkEntityMetadataObjectVillagerData extends ReadableNetworkEntityMetadataObject<VillagerData> {

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
