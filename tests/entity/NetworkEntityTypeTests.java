package entity;

import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import init.InitializePlatform;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;

public class NetworkEntityTypeTests extends InitializePlatform {

	@Test
	protected void testFilled() {
		for (EntityType bukkitType : EntityType.values()) {
			if (bukkitType != EntityType.UNKNOWN) {
				Assertions.assertNotEquals(NetworkEntityType.getByBukkitType(bukkitType), NetworkEntityType.NONE, "NetworkEntityType for Bukkit EntityType " + bukkitType);
			}
		}
	}

}
