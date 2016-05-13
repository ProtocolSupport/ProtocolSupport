package protocolsupport.protocol.packet.mcpe.middlepacketimpl.clientbound;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.clientbound.SetBlocksPacket;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.clientbound.SetBlocksPacket.UpdateBlockRecord;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockChangeMulti;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.protocol.typeremapper.id.RemappingTable;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class BlockChangeMulti extends MiddleBlockChangeMulti<RecyclableCollection<? extends ClientboundPEPacket>> {

	@Override
	public RecyclableCollection<? extends ClientboundPEPacket> toData(ProtocolVersion version) throws IOException {
		RemappingTable blockRemapper = IdRemapper.BLOCK.getTable(version);
		UpdateBlockRecord[] urecords = new UpdateBlockRecord[records.length];
		for (int i = 0; i < records.length; i++) {
			Record record = records[i];
			urecords[i] = new UpdateBlockRecord(
				(chunkX << 4) + (record.coord >> 12), record.coord & 0xFF, (chunkZ << 4) + ((record.coord >> 8) & 0xF),
				blockRemapper.getRemap(record.id >> 4), record.id & 0xF,
				SetBlocksPacket.FLAG_ALL_PRIORITY
			);
		}
		return RecyclableSingletonList.create(new SetBlocksPacket(urecords));
	}

}
