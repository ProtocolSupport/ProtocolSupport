package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddlePlayerAbilities;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class PlayerAbilities extends MiddlePlayerAbilities {

	public PlayerAbilities(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<? extends IPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_PLAYER_ABILITIES);
		serializer.writeByte(flags);
		serializer.writeByte((int) (flyspeed * 255.0F));
		serializer.writeByte((int) (walkspeed * 255.0F));
		return RecyclableSingletonList.create(serializer);
	}

}
