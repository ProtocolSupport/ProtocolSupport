package protocolsupport.protocol.transformer.mcpe.middlepacketimpl.clientbound;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.both.EntityEquipmentArmorPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.both.EntityEquipmentInventoryPacket;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleEntityEquipment;
import protocolsupport.utils.Utils;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityEquipment extends MiddleEntityEquipment<RecyclableCollection<? extends ClientboundPEPacket>> {

	@Override
	public RecyclableCollection<? extends ClientboundPEPacket> toData(ProtocolVersion version) throws IOException {
		if (slot == 0) {
			return RecyclableSingletonList.create(new EntityEquipmentInventoryPacket(entityId, itemstack));
		} else {
			storage.getPEStorage().setArmorSlot(entityId, slot - 1, itemstack);
			return RecyclableSingletonList.create(new EntityEquipmentArmorPacket(entityId, Utils.reverseArray(storage.getPEStorage().getArmor(entityId))));
		}
	}

}
