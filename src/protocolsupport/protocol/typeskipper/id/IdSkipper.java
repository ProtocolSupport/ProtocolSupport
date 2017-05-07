package protocolsupport.protocol.typeskipper.id;

import protocolsupport.protocol.typeskipper.id.SkippingRegistry.GenericSkippingRegistry;
import protocolsupport.protocol.typeskipper.id.SkippingRegistry.IntSkippingRegistry;
import protocolsupport.protocol.typeskipper.id.SkippingTable.ArrayBasedIntSkippingTable;
import protocolsupport.protocol.typeskipper.id.SkippingTable.GenericSkippingTable;
import protocolsupport.protocol.typeskipper.id.SkippingTable.HashMapBasedIntSkippingTable;
import protocolsupport.protocol.utils.types.WindowType;
import protocolsupport.utils.ProtocolVersionsHelper;

public class IdSkipper {

	public static final IntSkippingRegistry<HashMapBasedIntSkippingTable> ENCHANT = new IntSkippingRegistry<HashMapBasedIntSkippingTable>() {
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
		protected HashMapBasedIntSkippingTable createTable() {
			return new HashMapBasedIntSkippingTable();
		}
	};

	public static final IntSkippingRegistry<ArrayBasedIntSkippingTable> EFFECT = new IntSkippingRegistry<ArrayBasedIntSkippingTable>() {
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
		protected ArrayBasedIntSkippingTable createTable() {
			return new ArrayBasedIntSkippingTable(32);
		}
	};

	public static final GenericSkippingRegistry<String, GenericSkippingTable<String>> ATTRIBUTES = new GenericSkippingRegistry<String, GenericSkippingTable<String>>() {
		{
			registerSkipEntry("generic.armorToughness", ProtocolVersionsHelper.BEFORE_1_9_1);
			registerSkipEntry("generic.luck", ProtocolVersionsHelper.BEFORE_1_9);
			registerSkipEntry("generic.armor", ProtocolVersionsHelper.BEFORE_1_9);
			registerSkipEntry("generic.attackSpeed", ProtocolVersionsHelper.BEFORE_1_9);
		}
		@Override
		protected GenericSkippingTable<String> createTable() {
			return new GenericSkippingTable<>();
		}
	};

	public static final GenericSkippingRegistry<WindowType, GenericSkippingTable<WindowType>> INVENTORY = new GenericSkippingRegistry<WindowType, GenericSkippingTable<WindowType>>() {
		{
			registerSkipEntry(WindowType.HORSE, ProtocolVersionsHelper.BEFORE_1_11);
			registerSkipEntry(WindowType.HOPPER, ProtocolVersionsHelper.BEFORE_1_5);
		}
		@Override
		protected GenericSkippingTable<WindowType> createTable() {
			return new GenericSkippingTable<>();
		}
	};

	public static void init() {
	}

}
