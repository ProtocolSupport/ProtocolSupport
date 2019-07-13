package protocolsupport.protocol.typeremapper.particle;

import com.google.gson.JsonObject;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.utils.RemappingRegistry.IdRemappingRegistry;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.protocol.utils.MappingsData;
import protocolsupport.utils.JsonUtils;
import protocolsupport.utils.ResourceUtils;
import protocolsupportbuildprocessor.Preload;

@Preload
public class FlatteningParticleId {

	public static final IdRemappingRegistry<ArrayBasedIdRemappingTable> REGISTRY = new IdRemappingRegistry<ArrayBasedIdRemappingTable>() {
		@Override
		protected ArrayBasedIdRemappingTable createTable() {
			return new ArrayBasedIdRemappingTable(128);
		}
	};

	static {
		JsonObject rootObject = ResourceUtils.getAsJson(MappingsData.getResourcePath("flatteningparticles.json"));
		for (String versionString : rootObject.keySet()) {
			JsonObject entriesObject = rootObject.get(versionString).getAsJsonObject();
			ArrayBasedIdRemappingTable table = REGISTRY.getTable(ProtocolVersion.valueOf(versionString));
			for (String particleidString : entriesObject.keySet()) {
				table.setRemap(Integer.parseInt(particleidString), JsonUtils.getInt(entriesObject, particleidString));
			}
		}
	}

}
