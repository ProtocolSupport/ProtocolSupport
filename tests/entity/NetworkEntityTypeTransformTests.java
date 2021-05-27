package entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.format.NetworkEntityLegacyFormatRegistry;
import protocolsupport.protocol.typeremapper.entity.format.NetworkEntityLegacyFormatRegistry.NetworkEntityLegacyFormatEntry;
import protocolsupport.protocol.typeremapper.entity.format.NetworkEntityLegacyFormatRegistry.NetworkEntityLegacyFormatTable;
import protocolsupport.protocol.typeremapper.entity.legacy.NetworkEntityLegacyDataRegistry;
import protocolsupport.protocol.typeremapper.entity.legacy.NetworkEntityLegacyDataRegistry.NetworkEntityLegacyDataEntry;
import protocolsupport.protocol.typeremapper.entity.legacy.NetworkEntityLegacyDataRegistry.NetworkEntityLegacyDataTable;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import zinit.PlatformInit;

public class NetworkEntityTypeTransformTests extends PlatformInit {

	@Test
	public void testRegistriesFilled() {
		for (ProtocolVersion version : ProtocolVersionsHelper.ALL) {
			NetworkEntityLegacyDataTable dataTable = NetworkEntityLegacyDataRegistry.INSTANCE.getTable(version);
			NetworkEntityLegacyFormatTable formatTable = NetworkEntityLegacyFormatRegistry.INSTANCE.getTable(version);
			for (NetworkEntityType type : NetworkEntityType.values()) {
				if (type != NetworkEntityType.NONE) {
					NetworkEntityLegacyDataEntry dataEntry = dataTable.get(type);
					Assertions.assertNotNull(dataEntry, "NetworkEntityLegacyDataEntry for ProtocolVersion " + version + " and NetworkEntityType " + type);
					type = dataEntry.getType();
					if (type != NetworkEntityType.NONE) {
						NetworkEntityLegacyFormatEntry formatEntry = formatTable.get(type);
						Assertions.assertNotNull(formatEntry, "NetworkEntityLegacyFormatEntry for ProtocolVersion " + version + " and NetworkEntityType " + type);
						Assertions.assertNotEquals(NetworkEntityType.NONE, formatEntry.getType(), "NetworkEntityLegacyFormatEntry#getType for ProtocolVersion " + version + " and NetworkEntityType " + type + "");
					}
				}
			}
		}
	}

}
