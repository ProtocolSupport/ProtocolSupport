package protocolsupport.protocol.typeremapper.legacy;

import protocolsupport.protocol.typeremapper.utils.MappingRegistry.IntMappingRegistry;
import protocolsupport.protocol.typeremapper.utils.MappingTable.HashMapBasedIntMappingTable;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupportbuildprocessor.Preload;

//TODO: move effect legacy data remaps here too
@Preload
public class LegacyEffect {

	private LegacyEffect() {
	}

	public static final IntMappingRegistry<HashMapBasedIntMappingTable> REGISTRY = new IntMappingRegistry<>() {
		{
			register(1003, 1002, ProtocolVersionsHelper.DOWN_1_8);
			register(1004, 1002, ProtocolVersionsHelper.DOWN_1_8);
			register(1005, 1003, ProtocolVersionsHelper.DOWN_1_8);
			register(1006, 1003, ProtocolVersionsHelper.DOWN_1_8);
			register(1007, 1003, ProtocolVersionsHelper.DOWN_1_8);
			register(1008, 1003, ProtocolVersionsHelper.DOWN_1_8);
			register(1009, 1004, ProtocolVersionsHelper.DOWN_1_8);
			register(1010, 1005, ProtocolVersionsHelper.DOWN_1_8);
			register(1011, 1006, ProtocolVersionsHelper.DOWN_1_8);
			register(1012, 1006, ProtocolVersionsHelper.DOWN_1_8);
			register(1013, 1006, ProtocolVersionsHelper.DOWN_1_8);
			register(1014, 1006, ProtocolVersionsHelper.DOWN_1_8);
			register(1015, 1007, ProtocolVersionsHelper.DOWN_1_8);
			register(1016, 1008, ProtocolVersionsHelper.DOWN_1_8);
			register(1017, 1008, ProtocolVersionsHelper.DOWN_1_8);
			register(1018, 1009, ProtocolVersionsHelper.DOWN_1_8);
			register(1019, 1010, ProtocolVersionsHelper.DOWN_1_8);
			register(1020, 1011, ProtocolVersionsHelper.DOWN_1_8);
			register(1021, 1012, ProtocolVersionsHelper.DOWN_1_8);
			register(1022, 1012, ProtocolVersionsHelper.DOWN_1_8);
			register(1023, 1013, ProtocolVersionsHelper.DOWN_1_8);
			register(1024, 1014, ProtocolVersionsHelper.DOWN_1_8);
			register(1025, 1015, ProtocolVersionsHelper.DOWN_1_8);
			register(1026, 1016, ProtocolVersionsHelper.DOWN_1_8);
			register(1027, 1017, ProtocolVersionsHelper.DOWN_1_8);
			register(1028, 1018, ProtocolVersionsHelper.DOWN_1_8);
			register(1029, 1020, ProtocolVersionsHelper.DOWN_1_8);
			register(1030, 1021, ProtocolVersionsHelper.DOWN_1_8);
			register(1031, 1022, ProtocolVersionsHelper.DOWN_1_8);
			register(1036, 1003, ProtocolVersionsHelper.DOWN_1_8);
			register(1037, 1006, ProtocolVersionsHelper.DOWN_1_8);
		}
		@Override
		protected HashMapBasedIntMappingTable createTable() {
			return new HashMapBasedIntMappingTable();
		}
	};

}
