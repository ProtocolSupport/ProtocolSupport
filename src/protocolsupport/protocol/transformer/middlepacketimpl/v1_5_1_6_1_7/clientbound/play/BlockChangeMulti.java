package protocolsupport.protocol.transformer.middlepacketimpl.v1_5_1_6_1_7.clientbound.play;

import java.util.Collection;
import java.util.Collections;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleBlockChangeMulti;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.protocol.typeremapper.id.RemappingTable;

public class BlockChangeMulti extends MiddleBlockChangeMulti<Collection<PacketData>> {

	@Override
	public Collection<PacketData> toData(ProtocolVersion version) {
		RemappingTable remapper = IdRemapper.BLOCK.getTable(version);
		PacketDataSerializer serializer = PacketDataSerializer.createNew(version);
		serializer.writeInt(chunkX);
		serializer.writeInt(chunkZ);
		serializer.writeShort(records.length);
		serializer.writeInt(records.length * 4);
		for (Record record : records) {
			serializer.writeShort(record.coord);
			int id = record.id;
			serializer.writeShort((remapper.getRemap(id >> 4) << 4) | (id & 0xF));
		}
		return Collections.singletonList(new PacketData(ClientBoundPacket.PLAY_BLOCK_CHANGE_MULTI_ID, serializer));
	}

}
