package protocolsupport.protocol.typeremapper.entity;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.utils.RemappingRegistry.IdRemappingRegistry;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.protocol.utils.MappingsData;
import protocolsupport.protocol.utils.networkentity.NetworkEntityType;
import protocolsupport.utils.JsonUtils;
import protocolsupport.utils.ResourceUtils;
import protocolsupportbuildprocessor.Preload;

@Preload
public class FlatteningEntityId {

	//TODO: convert to networkentitytype -> id
	public static final IdRemappingRegistry<ArrayBasedIdRemappingTable> REGISTRY = new IdRemappingRegistry<ArrayBasedIdRemappingTable>() {
		@Override
		protected ArrayBasedIdRemappingTable createTable() {
			return new ArrayBasedIdRemappingTable(256);
		}
	};

	protected static void load(ProtocolVersion version) {
		JsonObject itemsData = ResourceUtils.getAsJson(MappingsData.getFlatteningResoucePath(version, "entityl.json"));
		if (itemsData != null) {
			ArrayBasedIdRemappingTable table = REGISTRY.getTable(version);
			for (Entry<String, JsonElement> entry : itemsData.entrySet()) {
				int modernId = NetworkEntityType.getByRegistrySTypeId(entry.getKey()).getNetworkTypeId();
				if (modernId == -1) {
					throw new IllegalStateException(MessageFormat.format("Entity type {0} doesn''t exist", entry.getKey()));
				}
				int legacyId = JsonUtils.getInt(entry.getValue().getAsJsonObject(), "protocol_id");
				table.setRemap(modernId, legacyId);
			}
		}
	}

	static {
		Arrays.stream(ProtocolVersion.getAllSupported()).forEach(FlatteningEntityId::load);
	}

}
