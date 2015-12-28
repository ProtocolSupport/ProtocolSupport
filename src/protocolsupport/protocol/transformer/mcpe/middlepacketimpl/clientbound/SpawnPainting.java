package protocolsupport.protocol.transformer.mcpe.middlepacketimpl.clientbound;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound.AddPaintingPacket;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleSpawnPainting;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class SpawnPainting extends MiddleSpawnPainting<RecyclableCollection<? extends ClientboundPEPacket>> {

	@Override
	public RecyclableCollection<? extends ClientboundPEPacket> toData(ProtocolVersion version) throws IOException {
		int x = position.getX();
		int z = position.getZ();
		switch (direction) {
			case 0: {
				--z;
				break;
			}
			case 1: {
				++x;
				break;
			}
			case 2: {
				++z;
				break;
			}
			case 3: {
				--x;
				break;
			}
		}
		return RecyclableSingletonList.create(new AddPaintingPacket(entityId, x, position.getY(), z, direction, type)); 
	}

}
