package protocolsupport.protocol.types.networkentity.metadata.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.types.VillagerData;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;

public class NetworkEntityMetadataObjectVillagerData extends NetworkEntityMetadataObject<VillagerData> {

	public NetworkEntityMetadataObjectVillagerData(VillagerData vdata) {
		this.value = vdata;
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		VarNumberCodec.writeVarInt(to, value.getType());
		VarNumberCodec.writeVarInt(to, value.getProfession().ordinal());
		VarNumberCodec.writeVarInt(to, value.getLevel());
	}

}
