package protocolsupport.protocol.typeremapper.particle;

import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.utils.MappingRegistry.IntMappingRegistry;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ThrowingArrayBasedIntTable;
import protocolsupport.protocol.utils.MappingsData;
import protocolsupport.utils.ResourceUtils;
import protocolsupportbuildprocessor.Preload;

@Preload
public class FlatteningNetworkParticleIdRegistry extends IntMappingRegistry<ThrowingArrayBasedIntTable> {

	public static final FlatteningNetworkParticleIdRegistry INSTANCE = new FlatteningNetworkParticleIdRegistry();

	protected FlatteningNetworkParticleIdRegistry() {
		JsonObject rootObject = ResourceUtils.getAsJsonObject(MappingsData.getResourcePath("flatteningparticles.json"));
		for (Entry<String, JsonElement> rootEntry : rootObject.entrySet()) {
			ArrayBasedIntMappingTable table = getTable(ProtocolVersion.valueOf(rootEntry.getKey()));
			for (Entry<String, JsonElement> particleidEntry : rootEntry.getValue().getAsJsonObject().entrySet()) {
				table.set(Integer.parseInt(particleidEntry.getKey()), particleidEntry.getValue().getAsInt());
			}
		}
	}

	@Override
	protected ThrowingArrayBasedIntTable createTable() {
		return new ThrowingArrayBasedIntTable(128);
	}

}
