package protocolsupport.protocol.types.networkentity.metadata.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.types.VillagerData;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;

public class NetworkEntityMetadataObjectVillagerData extends NetworkEntityMetadataObject<VillagerData> {

	public NetworkEntityMetadataObjectVillagerData(VillagerData vdata) {
		this.value = vdata;
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		VarNumberSerializer.writeVarInt(to, value.getType());
		VarNumberSerializer.writeVarInt(to, value.getProfession().ordinal());
		VarNumberSerializer.writeVarInt(to, value.getLevel());
	}

}
