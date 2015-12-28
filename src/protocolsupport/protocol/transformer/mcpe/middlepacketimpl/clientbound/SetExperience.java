package protocolsupport.protocol.transformer.mcpe.middlepacketimpl.clientbound;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound.SetAttributesPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound.SetAttributesPacket.AttributeRecord;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleSetExperience;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class SetExperience extends MiddleSetExperience<RecyclableCollection<? extends ClientboundPEPacket>> {

	@Override
	public RecyclableCollection<? extends ClientboundPEPacket> toData(ProtocolVersion version) throws IOException {
		//TODO: figure out why attributes kill player
		/*return RecyclableSingletonList.create(new SetAttributesPacket(
			storage.getWatchedSelfPlayer().getId(),
			new AttributeRecord("player.experience", 0.0F, 1.0F, exp),
			new AttributeRecord("player.level", 0.0F, Float.MAX_VALUE, level)
		));*/
		return RecyclableEmptyList.get();
	}

}
