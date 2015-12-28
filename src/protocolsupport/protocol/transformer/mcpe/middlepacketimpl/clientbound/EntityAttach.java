package protocolsupport.protocol.transformer.mcpe.middlepacketimpl.clientbound;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound.AttachEntityPacket;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleEntityAttach;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityAttach extends MiddleEntityAttach<RecyclableCollection<? extends ClientboundPEPacket>> {

	@Override
	public RecyclableCollection<? extends ClientboundPEPacket> toData(ProtocolVersion version) throws IOException {
		boolean attach = vehicleId != -1;
		if (attach) {
			storage.getPEStorage().setVehicleId(vehicleId);
		} else {
			vehicleId = storage.getPEStorage().getVehicleId();
		}
		if (!leash) {
			return RecyclableSingletonList.create(new AttachEntityPacket(vehicleId, entityId, attach));
		} else {
			return RecyclableEmptyList.get();
		}
	}

}
