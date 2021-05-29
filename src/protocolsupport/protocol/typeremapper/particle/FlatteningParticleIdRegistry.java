package protocolsupport.protocol.typeremapper.particle;

import com.google.gson.JsonObject;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.utils.MappingRegistry.IntMappingRegistry;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;
import protocolsupport.protocol.utils.MappingsData;
import protocolsupport.utils.JsonUtils;
import protocolsupport.utils.ResourceUtils;
import protocolsupportbuildprocessor.Preload;

@Preload
public class FlatteningParticleIdRegistry extends IntMappingRegistry<ArrayBasedIntMappingTable> {

	public static final FlatteningParticleIdRegistry INSTANCE = new FlatteningParticleIdRegistry();

	protected FlatteningParticleIdRegistry() {
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
	protected ArrayBasedIntMappingTable createTable() {
		return new ArrayBasedIntMappingTable(128);
	}

}
