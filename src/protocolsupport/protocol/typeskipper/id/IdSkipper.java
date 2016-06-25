package protocolsupport.protocol.typeskipper.id;

import protocolsupport.utils.ProtocolVersionsHelper;

public class IdSkipper {

	public static final SkippingRegistry ENCHANT = new SkippingRegistry() {
		{
			//frost walker, mending
			registerSkipEntry(9, ProtocolVersionsHelper.BEFORE_1_9);
			registerSkipEntry(70, ProtocolVersionsHelper.BEFORE_1_9);
			//depth strider
			registerSkipEntry(8, ProtocolVersionsHelper.BEFORE_1_8);
			//lure, luck of the sea
			registerSkipEntry(62, ProtocolVersionsHelper.BEFORE_1_7);
			registerSkipEntry(61, ProtocolVersionsHelper.BEFORE_1_7);
		}
		@Override
		protected SkippingTable createTable() {
			return new SkippingTable.HashSkippingTable();
		}
	};

	public static final SkippingRegistry EFFECT = new SkippingRegistry() {
		{
			//glowing, levitation, luck, unluck
			registerSkipEntry(24, ProtocolVersionsHelper.BEFORE_1_9);
			registerSkipEntry(25, ProtocolVersionsHelper.BEFORE_1_9);
			registerSkipEntry(26, ProtocolVersionsHelper.BEFORE_1_9);
			registerSkipEntry(27, ProtocolVersionsHelper.BEFORE_1_9);
			//health boost, absorbtion, saturation
			registerSkipEntry(21, ProtocolVersionsHelper.BEFORE_1_6);
			registerSkipEntry(22, ProtocolVersionsHelper.BEFORE_1_6);
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

	public static void init() {
	}

}
