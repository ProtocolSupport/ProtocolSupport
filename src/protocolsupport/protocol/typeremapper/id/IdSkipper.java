package protocolsupport.protocol.typeremapper.id;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.utils.SkippingRegistry.EnumSkippingRegistry;
import protocolsupport.protocol.typeremapper.utils.SkippingRegistry.GenericSkippingRegistry;
import protocolsupport.protocol.typeremapper.utils.SkippingRegistry.IntSkippingRegistry;
import protocolsupport.protocol.typeremapper.utils.SkippingTable;
import protocolsupport.protocol.typeremapper.utils.SkippingTable.ArrayBasedIntSkippingTable;
import protocolsupport.protocol.typeremapper.utils.SkippingTable.EnumSkippingTable;
import protocolsupport.protocol.typeremapper.utils.SkippingTable.GenericSkippingTable;
import protocolsupport.protocol.typeremapper.utils.SkippingTable.HashMapBasedIntSkippingTable;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.types.NetworkEntityType;
import protocolsupport.protocol.utils.types.Particle;
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

			//curse of binding, sweeping edge, curse of vanishing
			registerSkipEntry(10, ProtocolVersion.MINECRAFT_PE);
			registerSkipEntry(22, ProtocolVersion.MINECRAFT_PE);
			registerSkipEntry(71, ProtocolVersion.MINECRAFT_PE);
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

	public static final EnumSkippingRegistry<Particle, EnumSkippingTable<Particle>> PARTICLE = new EnumSkippingRegistry<Particle, SkippingTable.EnumSkippingTable<Particle>>() {
		{
			registerSkipEntry(Particle.TOTEM, ProtocolVersionsHelper.BEFORE_1_11);
			registerSkipEntry(Particle.SPIT, ProtocolVersionsHelper.BEFORE_1_11);
			registerSkipEntry(Particle.FALLING_DUST, ProtocolVersionsHelper.BEFORE_1_10);
			registerSkipEntry(Particle.DRAGON_BREATH, ProtocolVersionsHelper.BEFORE_1_9);
			registerSkipEntry(Particle.END_ROD, ProtocolVersionsHelper.BEFORE_1_9);
			registerSkipEntry(Particle.DAMAGE_INDICATOR, ProtocolVersionsHelper.BEFORE_1_9);
			registerSkipEntry(Particle.SWEEP_ATTACK, ProtocolVersionsHelper.BEFORE_1_9);
		}
		@Override
		protected EnumSkippingTable<Particle> createTable() {
			return new EnumSkippingTable<>(Particle.class);
		}
	};

}
