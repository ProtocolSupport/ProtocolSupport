package entity;

import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import zinit.PlatformInit;

class NetworkEntityTypeTests extends PlatformInit {

	@Test
	void testFilled() {
		for (EntityType bukkitType : EntityType.values()) {
			if ((bukkitType != EntityType.UNKNOWN) && (bukkitType != EntityType.MARKER)) {
				Assertions.assertNotEquals(NetworkEntityType.NONE, NetworkEntityType.getByBukkitType(bukkitType), "NetworkEntityType for Bukkit EntityType " + bukkitType + " ");
			}
		}
	}

}
