package protocolsupport.protocol.typeskipper.id;

import net.minecraft.server.v1_8_R3.Enchantment;
import net.minecraft.server.v1_8_R3.MobEffectList;
import protocolsupport.utils.ProtocolVersionsHelper;

public class IdSkipper {

	public static final SkippingRegistry ENCHANT = new SkippingRegistry() {
		{
			registerSkipEntry(Enchantment.DEPTH_STRIDER.id, ProtocolVersionsHelper.BEFORE_1_8);
			registerSkipEntry(Enchantment.LURE.id, ProtocolVersionsHelper.BEFORE_1_7);
			registerSkipEntry(Enchantment.LUCK.id, ProtocolVersionsHelper.BEFORE_1_7);
		}
		@Override
		protected SkippingTable createTable() {
			return new SkippingTable(256);
		}
	};

	public static final SkippingRegistry EFFECT = new SkippingRegistry() {
		{
			registerSkipEntry(MobEffectList.HEALTH_BOOST.id, ProtocolVersionsHelper.BEFORE_1_6);
			registerSkipEntry(MobEffectList.ABSORBTION.id, ProtocolVersionsHelper.BEFORE_1_6);
			registerSkipEntry(MobEffectList.SATURATION.id, ProtocolVersionsHelper.BEFORE_1_6);
		}
		@Override
		protected SkippingTable createTable() {
			return new SkippingTable(32);
		}
	};

	public static final SkippingRegistry INVENTORY = new SkippingRegistry() {
		{
			registerSkipEntry(11, ProtocolVersionsHelper.BEFORE_1_6);
			registerSkipEntry(9, ProtocolVersionsHelper.BEFORE_1_5);
			registerSkipEntry(10, ProtocolVersionsHelper.BEFORE_1_5);
		}
		@Override
		protected SkippingTable createTable() {
			return new SkippingTable(16);
		}
	};

}
