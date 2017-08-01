package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleUnloadChunk;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;

public class UnloadChunk extends MiddleUnloadChunk {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		cache.unmarkSentChunk(chunkX, chunkZ);
		return RecyclableEmptyList.get();
	}

}
