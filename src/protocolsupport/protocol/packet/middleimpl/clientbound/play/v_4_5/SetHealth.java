package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5;

import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSetHealth;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class SetHealth extends MiddleSetHealth {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_UPDATE_HEALTH_ID);
		serializer.writeShort((int) Math.ceil((health * 20.0F) / cache.getWatchedEntityCache().getSelfPlayer().getDataCache().getMaxHealth()));
		serializer.writeShort(food);
		serializer.writeFloat(saturation);
		return RecyclableSingletonList.create(serializer);
	}

}
