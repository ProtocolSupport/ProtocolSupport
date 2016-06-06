package protocolsupport.protocol.legacyremapper;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.id.RemappingRegistry.IdRemappingRegistry;
import protocolsupport.protocol.typeremapper.id.RemappingTable.HashMapBasedIdRemappingTable;
import protocolsupport.utils.ProtocolVersionsHelper;

public class LegacyEffect {

	private static final IdRemappingRegistry<HashMapBasedIdRemappingTable> effect = new IdRemappingRegistry<HashMapBasedIdRemappingTable>() {
		{
			registerRemapEntry(1003, 1002, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(1004, 1002, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(1005, 1003, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(1006, 1003, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(1007, 1003, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(1008, 1003, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(1009, 1004, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(1010, 1005, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(1011, 1006, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(1012, 1006, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(1013, 1006, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(1014, 1006, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(1015, 1007, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(1016, 1008, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(1017, 1008, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(1018, 1009, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(1019, 1010, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(1020, 1011, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(1021, 1012, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(1022, 1012, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(1023, 1013, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(1024, 1014, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(1025, 1015, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(1026, 1016, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(1027, 1017, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(1028, 1018, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(1029, 1020, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(1030, 1021, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(1031, 1022, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(1036, 1003, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(1037, 1006, ProtocolVersionsHelper.BEFORE_1_9);
		}
		@Override
		protected HashMapBasedIdRemappingTable createTable() {
			return new HashMapBasedIdRemappingTable();
		}
	};

	public static int getLegacyId(ProtocolVersion version, int id) {
		return effect.getTable(version).getRemap(id);
	}

}
