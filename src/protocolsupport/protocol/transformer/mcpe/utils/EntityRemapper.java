package protocolsupport.protocol.transformer.mcpe.utils;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.id.RemappingReigstry;
import protocolsupport.protocol.typeremapper.id.RemappingTable;

public class EntityRemapper {

	public static final RemappingReigstry REGISTRY = new RemappingReigstry() {
		{
			//everyone is a zombie, for testing purposes
			for (int i = 0; i < 256; i++) {
				registerRemapEntry(i, 32, ProtocolVersion.MINECRAFT_PE);
			}
		}
		@Override
		protected RemappingTable createTable() {
			return new RemappingTable(256);
		}
	};

}
