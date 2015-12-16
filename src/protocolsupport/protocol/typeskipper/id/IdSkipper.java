package protocolsupport.protocol.typeskipper.id;

import protocolsupport.utils.ProtocolVersionsHelper;

import net.minecraft.server.v1_8_R3.Enchantment;
import net.minecraft.server.v1_8_R3.MobEffectList;

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

	public static final SkippingRegistry EFFECT = new SkippingRegistry() {
		{
			registerSkipEntry(MobEffectList.HEALTH_BOOST.id, ProtocolVersionsHelper.BEFORE_1_6);
			registerSkipEntry(MobEffectList.ABSORBTION.id, ProtocolVersionsHelper.BEFORE_1_6);
			registerSkipEntry(MobEffectList.SATURATION.id, ProtocolVersionsHelper.BEFORE_1_6);
		}
		@Override
		protected SkippingTable createTable() {
			return new SkippingTable(64);
		}
	};

	public static final SkippingRegistry INVENTORY = new SkippingRegistry() {
		{
			registerSkipEntry(11, ProtocolVersionsHelper.BEFORE_1_6);
		}
		@Override
		protected SkippingTable createTable() {
			return new SkippingTable(16);
		}
	};

}
