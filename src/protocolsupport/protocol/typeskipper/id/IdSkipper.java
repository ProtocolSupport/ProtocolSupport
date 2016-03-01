package protocolsupport.protocol.typeskipper.id;

import protocolsupport.utils.ProtocolVersionsHelper;

public class IdSkipper {

	public static final SkippingRegistry ENCHANT = new SkippingRegistry() {
		{
			//depth strider
			registerSkipEntry(8, ProtocolVersionsHelper.BEFORE_1_8);
			//lure
			registerSkipEntry(62, ProtocolVersionsHelper.BEFORE_1_7);
			//luck of the sea
			registerSkipEntry(61, ProtocolVersionsHelper.BEFORE_1_7);
		}
		@Override
		protected SkippingTable createTable() {
			return new SkippingTable(256);
		}
	};

	public static final SkippingRegistry EFFECT = new SkippingRegistry() {
		{
			//health boost
			registerSkipEntry(21, ProtocolVersionsHelper.BEFORE_1_6);
			//absorbtion
			registerSkipEntry(22, ProtocolVersionsHelper.BEFORE_1_6);
			//saturation
			registerSkipEntry(23, ProtocolVersionsHelper.BEFORE_1_6);
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
