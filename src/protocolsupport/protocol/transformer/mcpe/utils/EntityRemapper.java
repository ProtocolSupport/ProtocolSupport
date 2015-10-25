package protocolsupport.protocol.transformer.mcpe.utils;

import protocolsupport.protocol.typeremapper.id.RemappingTable;

public class EntityRemapper {

	public static final RemappingTable TABLE = new RemappingTable(256) {
		{
			for (int i = 0; i < table.length; i++) {
				setRemap(i, 32);
			}
		}
	};

}
