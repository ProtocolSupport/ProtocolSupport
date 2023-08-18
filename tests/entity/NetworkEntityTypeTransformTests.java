package entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.FlatteningNetworkEntityIdRegistry;
import protocolsupport.protocol.typeremapper.entity.format.NetworkEntityLegacyFormatRegistry;
import protocolsupport.protocol.typeremapper.entity.format.NetworkEntityLegacyFormatRegistry.NetworkEntityLegacyFormatEntry;
import protocolsupport.protocol.typeremapper.entity.format.NetworkEntityLegacyFormatRegistry.NetworkEntityLegacyFormatTable;
import protocolsupport.protocol.typeremapper.entity.legacy.NetworkEntityLegacyDataRegistry;
import protocolsupport.protocol.typeremapper.entity.legacy.NetworkEntityLegacyDataRegistry.NetworkEntityLegacyDataEntry;
import protocolsupport.protocol.typeremapper.entity.legacy.NetworkEntityLegacyDataRegistry.NetworkEntityLegacyDataTable;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import zinit.PlatformInit;

class NetworkEntityTypeTransformTests extends PlatformInit {

	@Test
	void testMappingRegistries() {
		for (ProtocolVersion version : ProtocolVersionsHelper.ALL) {
			NetworkEntityLegacyDataTable dataTable = NetworkEntityLegacyDataRegistry.INSTANCE.getTable(version);
			NetworkEntityLegacyFormatTable formatTable = NetworkEntityLegacyFormatRegistry.INSTANCE.getTable(version);
			for (NetworkEntityType type : NetworkEntityType.values()) {
				NetworkEntityType mappedType = type;
				if (mappedType == NetworkEntityType.NONE) {
					continue;
				}

				NetworkEntityLegacyDataEntry dataEntry = dataTable.get(mappedType);
				Assertions.assertNotNull(dataEntry, "NetworkEntityLegacyDataEntry for ProtocolVersion " + version + " and NetworkEntityType " + type);

				mappedType = dataEntry.getType();
				if (mappedType == NetworkEntityType.NONE) {
					continue;
				}

				NetworkEntityLegacyFormatEntry formatEntry = formatTable.get(mappedType);
				Assertions.assertNotNull(formatEntry, "NetworkEntityLegacyFormatEntry for ProtocolVersion " + version + " and NetworkEntityType " + type);
				mappedType = formatEntry.getType();
				Assertions.assertNotEquals(NetworkEntityType.NONE, mappedType, "NetworkEntityLegacyFormatEntry#getType for ProtocolVersion " + version + " and NetworkEntityType " + type + "");

				NetworkEntityType fMappedType = mappedType;
				switch (mappedType.getMetaType()) {
					case MOB: {
						if (version.isAfterOrEq(ProtocolVersion.MINECRAFT_1_13)) {
							Assertions.assertDoesNotThrow(
								() -> FlatteningNetworkEntityIdRegistry.INSTANCE.getTable(version).get(fMappedType.getNetworkTypeId()),
								"NetworkEntityTypeFlatteningId for ProtocolVersion " + version + " and NetworkEntityType " + type
							);
						}
						break;
					}
					case OBJECT: {
						if (version.isAfterOrEq(ProtocolVersion.MINECRAFT_1_14)) {
							Assertions.assertDoesNotThrow(
								() -> FlatteningNetworkEntityIdRegistry.INSTANCE.getTable(version).get(fMappedType.getNetworkTypeId()),
								"NetworkEntityTypeFlatteningId for ProtocolVersion " + version + " and NetworkEntityType " + type
							);
						}
						break;
					}
					default: {
						break;
					}
				}
			}
		}
	}

}
