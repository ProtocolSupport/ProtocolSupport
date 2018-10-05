package remappingregistry;

import java.text.MessageFormat;
import java.util.Arrays;

import junit.framework.TestCase;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.EntityRemappersRegistry;
import protocolsupport.protocol.typeremapper.entity.EntityRemappersRegistry.EntityRemappingTable;
import protocolsupport.protocol.utils.networkentity.NetworkEntityType;
import protocolsupport.zplatform.ServerPlatform;

public class EntityRemappersRegistryTests extends TestCase {

	@Override
	protected void setUp() throws Exception {
		ServerPlatform.detect();
	}

	public void testCompleted() {
		Arrays.stream(ProtocolVersion.getAllSupported())
		.forEach(version -> {
			EntityRemappingTable table = EntityRemappersRegistry.REGISTRY.getTable(version);
			Arrays.stream(NetworkEntityType.values())
			.filter(NetworkEntityType::isReal)
			.forEach(type -> assertNotNull(MessageFormat.format("Missing remap for version {0} entity type {1}", version, type), table.getRemap(type)));
		});
	}

}
