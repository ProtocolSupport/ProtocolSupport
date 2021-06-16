package protocolsupport.protocol.typeremapper.particle;

import com.google.gson.JsonObject;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.utils.MappingRegistry.IntMappingRegistry;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ThrowingArrayBasedIntTable;
import protocolsupport.protocol.utils.MappingsData;
import protocolsupport.utils.JsonUtils;
import protocolsupport.utils.ResourceUtils;
import protocolsupportbuildprocessor.Preload;

@Preload
public class FlatteningNetworkParticleIdRegistry extends IntMappingRegistry<ThrowingArrayBasedIntTable> {

	public static final FlatteningNetworkParticleIdRegistry INSTANCE = new FlatteningNetworkParticleIdRegistry();

	protected FlatteningNetworkParticleIdRegistry() {
		JsonObject rootObject = ResourceUtils.getAsJsonObject(MappingsData.getResourcePath("flatteningparticles.json"));
		for (String versionString : rootObject.keySet()) {
			JsonObject entriesObject = rootObject.get(versionString).getAsJsonObject();
			ArrayBasedIntMappingTable table = getTable(ProtocolVersion.valueOf(versionString));
			for (String particleidString : entriesObject.keySet()) {
				table.set(Integer.parseInt(particleidString), JsonUtils.getInt(entriesObject, particleidString));
			}
		}
	}

	@Override
	protected ThrowingArrayBasedIntTable createTable() {
		return new ThrowingArrayBasedIntTable(128);
	}

}
