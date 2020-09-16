package entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.LegacyNetworkEntityRegistry;
import protocolsupport.protocol.typeremapper.entity.LegacyNetworkEntityRegistry.LegacyNetworkEntityTable;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;

public class NetworkEntityTypeTransformTests {

	@Test
	public void testLegacyRegistryFilled() {
		for (ProtocolVersion version : ProtocolVersion.getAllSupported()) {
			LegacyNetworkEntityTable table = LegacyNetworkEntityRegistry.INSTANCE.getTable(version);
			for (NetworkEntityType type : NetworkEntityType.values()) {
				if (type != NetworkEntityType.NONE) {
					Assertions.assertNotNull(table.get(type), "LegacyNetworkEntity for ProtocolVersion " + version + " and NetworkEntityType " + type + " ");
				}
			}
		}
	}

}
