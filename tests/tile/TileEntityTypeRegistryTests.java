package tile;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import protocolsupport.protocol.types.TileEntityType;

class TileEntityTypeRegistryTests {

	@Test
	void registryFilled() {
		for (TileEntityType type : TileEntityType.values()) {
			if (type != TileEntityType.UNKNOWN) {
				Assertions.assertTrue(type.getNetworkId() >= 0, "TileEntityType " + type + " network id > 0 ");
			}
		}
	}

}
