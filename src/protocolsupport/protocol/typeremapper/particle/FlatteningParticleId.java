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
public class FlatteningParticleId {

	public static final IntMappingRegistry<ArrayBasedIntMappingTable> REGISTRY = new IntMappingRegistry<ArrayBasedIntMappingTable>() {
		@Override
		protected ArrayBasedIntMappingTable createTable() {
			return new ArrayBasedIntMappingTable(128);
		}
	};

	static {
		JsonObject rootObject = ResourceUtils.getAsJsonObject(MappingsData.getResourcePath("flatteningparticles.json"));
		for (String versionString : rootObject.keySet()) {
			JsonObject entriesObject = rootObject.get(versionString).getAsJsonObject();
			ArrayBasedIntMappingTable table = REGISTRY.getTable(ProtocolVersion.valueOf(versionString));
			for (String particleidString : entriesObject.keySet()) {
				table.set(Integer.parseInt(particleidString), JsonUtils.getInt(entriesObject, particleidString));
			}
		}
	}

}
