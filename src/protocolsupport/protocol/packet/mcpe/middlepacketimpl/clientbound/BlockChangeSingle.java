package protocolsupport.protocol.packet.mcpe.middlepacketimpl.clientbound;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.clientbound.SetBlocksPacket;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.clientbound.SetBlocksPacket.UpdateBlockRecord;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockChangeSingle;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class BlockChangeSingle extends MiddleBlockChangeSingle<RecyclableCollection<? extends ClientboundPEPacket>> {

	@Override
	public RecyclableCollection<? extends ClientboundPEPacket> toData(ProtocolVersion version) throws IOException {
		return RecyclableSingletonList.create(new SetBlocksPacket(new UpdateBlockRecord(
			position.getX(),
			position.getY(),
			position.getZ(),
			IdRemapper.BLOCK.getTable(version).getRemap(id >> 4),
			id & 0xF,
			SetBlocksPacket.FLAG_ALL_PRIORITY
		)));
	}

}
