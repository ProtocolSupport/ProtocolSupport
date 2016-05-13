package protocolsupport.protocol.transformer.mcpe.middlepacketimpl.clientbound;

import java.io.IOException;

import gnu.trove.map.hash.TIntObjectHashMap;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnExpOrb;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound.AddEntityPacket;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class SpawnExpOrb extends MiddleSpawnExpOrb<RecyclableCollection<? extends ClientboundPEPacket>> {

	@Override
	public RecyclableCollection<? extends ClientboundPEPacket> toData(ProtocolVersion version) throws IOException {
		return RecyclableSingletonList.create(new AddEntityPacket(
			entityId, 69,
			(float) x, (float) y, (float) z,
			0, 0, 0, 0, 0,
			new TIntObjectHashMap<DataWatcherObject<?>>()
		));
	}

}
