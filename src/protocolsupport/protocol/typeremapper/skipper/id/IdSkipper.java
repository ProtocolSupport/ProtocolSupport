package protocolsupport.protocol.typeremapper.skipper.id;

import protocolsupport.protocol.typeremapper.skipper.id.SkippingRegistry.EnumSkippingRegistry;
import protocolsupport.protocol.typeremapper.skipper.id.SkippingRegistry.GenericSkippingRegistry;
import protocolsupport.protocol.typeremapper.skipper.id.SkippingRegistry.IntSkippingRegistry;
import protocolsupport.protocol.typeremapper.skipper.id.SkippingTable.ArrayBasedIntSkippingTable;
import protocolsupport.protocol.typeremapper.skipper.id.SkippingTable.EnumSkippingTable;
import protocolsupport.protocol.typeremapper.skipper.id.SkippingTable.GenericSkippingTable;
import protocolsupport.protocol.typeremapper.skipper.id.SkippingTable.HashMapBasedIntSkippingTable;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.types.NetworkEntityType;
import protocolsupport.protocol.utils.types.WindowType;

public class IdSkipper {

	public static final EnumSkippingRegistry<NetworkEntityType, EnumSkippingTable<NetworkEntityType>> ENTITY = new EnumSkippingRegistry<NetworkEntityType, EnumSkippingTable<NetworkEntityType>>() {
		{
			registerSkipEntry(NetworkEntityType.ARMOR_STAND_MOB, ProtocolVersionsHelper.BEFORE_1_8);
			registerSkipEntry(NetworkEntityType.ARMOR_STAND_OBJECT, ProtocolVersionsHelper.BEFORE_1_8);
		}

		@Override
		protected EnumSkippingTable<NetworkEntityType> createTable() {
			return new EnumSkippingTable<>(NetworkEntityType.class);
		}

	};

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
			registerSkipEntry("generic.flyingSpeed", ProtocolVersionsHelper.BEFORE_1_12);
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

	public static final EnumSkippingRegistry<WindowType, EnumSkippingTable<WindowType>> INVENTORY = new EnumSkippingRegistry<WindowType, EnumSkippingTable<WindowType>>() {
		{
			registerSkipEntry(WindowType.HORSE, ProtocolVersionsHelper.BEFORE_1_6);
			registerSkipEntry(WindowType.HOPPER, ProtocolVersionsHelper.BEFORE_1_5);
		}
		@Override
		protected EnumSkippingTable<WindowType> createTable() {
			return new EnumSkippingTable<>(WindowType.class);
		}
	};

}
