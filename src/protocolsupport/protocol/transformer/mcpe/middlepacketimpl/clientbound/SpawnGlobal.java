package protocolsupport.protocol.transformer.mcpe.middlepacketimpl.clientbound;

import java.io.IOException;

import gnu.trove.map.hash.TIntObjectHashMap;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound.AddEntityPacket;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleSpawnGlobal;
import protocolsupport.utils.DataWatcherObject;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class SpawnGlobal extends MiddleSpawnGlobal<RecyclableCollection<? extends ClientboundPEPacket>> {

	@Override
	public RecyclableCollection<? extends ClientboundPEPacket> toData(ProtocolVersion version) throws IOException {
		return RecyclableSingletonList.create(new AddEntityPacket(
			entityId, 93,
			x / 32.0F, y / 32.0F, z / 32.0F,
			0, 0, 0, 0, 0,
			new TIntObjectHashMap<DataWatcherObject>()
		));
	}

}
