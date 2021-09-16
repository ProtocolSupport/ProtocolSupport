package itemstack;

import org.bukkit.enchantments.Enchantment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.basic.GenericIdSkipper;
import protocolsupport.protocol.typeremapper.legacy.LegacyEnchantmentId;
import protocolsupport.protocol.typeremapper.utils.SkippingTable.GenericSkippingTable;
import protocolsupport.protocol.utils.CommonNBT;

class NetworkItemStackEnchantTests {

	@Test
	void testFakeEnchantmentRegistered() {
		int id = Assertions.assertDoesNotThrow(() -> LegacyEnchantmentId.getId(CommonNBT.FAKE_ENCHANTMENT));
		Assertions.assertEquals(CommonNBT.FAKE_ENCHANTMENT, LegacyEnchantmentId.getById(id));
	}

	@Test
	void testLegacyId() {
		GenericSkippingTable<String> enchSkipTable = GenericIdSkipper.ENCHANT.getTable(ProtocolVersion.MINECRAFT_1_12_2);
		for (Enchantment ench : Enchantment.values()) {
			if (!enchSkipTable.isSet(ench.getKey().toString())) {
				Assertions.assertDoesNotThrow(() -> LegacyEnchantmentId.getId(ench));
			}
		}
	}

}
