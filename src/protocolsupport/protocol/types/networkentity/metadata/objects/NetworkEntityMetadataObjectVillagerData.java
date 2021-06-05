package protocolsupport.protocol.types.networkentity.metadata.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.types.VillagerData;
import protocolsupport.protocol.types.VillagerProfession;
import protocolsupport.protocol.types.networkentity.metadata.ReadableNetworkEntityMetadataObject;

public class NetworkEntityMetadataObjectVillagerData extends ReadableNetworkEntityMetadataObject<VillagerData> {

	public NetworkEntityMetadataObjectVillagerData() {
	}

	public NetworkEntityMetadataObjectVillagerData(VillagerData vdata) {
		this.value = vdata;
	}

	@Override
	public void readFromStream(ByteBuf from) {
		value = new VillagerData(
			VarNumberSerializer.readVarInt(from),
			VillagerProfession.CONSTANT_LOOKUP.getByOrdinalOrDefault(VarNumberSerializer.readVarInt(from), VillagerProfession.NONE),
			VarNumberSerializer.readVarInt(from)
		);
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		VarNumberSerializer.writeVarInt(to, value.getType());
		VarNumberSerializer.writeVarInt(to, value.getProfession().ordinal());
		VarNumberSerializer.writeVarInt(to, value.getLevel());
	}

}
