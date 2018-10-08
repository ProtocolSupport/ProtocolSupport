package remappingregistry;

import java.text.MessageFormat;
import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.EntityRemappersRegistry;
import protocolsupport.protocol.typeremapper.entity.EntityRemappersRegistry.EntityRemappingTable;
import protocolsupport.protocol.utils.networkentity.NetworkEntityType;
import protocolsupport.zplatform.ServerPlatform;

public class EntityRemappersRegistryTests {

	@BeforeAll
	protected static void setUp() {
		ServerPlatform.detect();
	}

	@Test
	public void testCompleted() {
		Arrays.stream(ProtocolVersion.getAllSupported())
		.forEach(version -> {
			EntityRemappingTable table = EntityRemappersRegistry.REGISTRY.getTable(version);
			Arrays.stream(NetworkEntityType.values())
			.filter(NetworkEntityType::isReal)
			.forEach(type -> Assertions.assertNotNull(table.getRemap(type), MessageFormat.format("Missing remap for version {0} entity type {1}", version, type)));
		});
	}

}
