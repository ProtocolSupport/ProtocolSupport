package protocolsupport.protocol.typeskipper.id;

import protocolsupport.utils.ProtocolVersionsHelper;
import net.minecraft.server.v1_8_R3.Enchantment;

public class IdSkipper {

	public static final SkippingRegistry ENCHANT = new SkippingRegistry() {
		{
			registerSkipEntry(Enchantment.DEPTH_STRIDER.id, ProtocolVersionsHelper.BEFORE_1_8);
			registerSkipEntry(Enchantment.LURE.id, ProtocolVersionsHelper.BEFORE_1_7);
			registerSkipEntry(Enchantment.LUCK.id, ProtocolVersionsHelper.BEFORE_1_7);
		}
		@Override
		protected SkippingTable createTable() {
			return new SkippingTable(128);
		}
	};

}
