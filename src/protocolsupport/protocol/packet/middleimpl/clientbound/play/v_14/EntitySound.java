package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_14;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntitySound;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntitySound extends MiddleEntitySound {

	public EntitySound(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_ENTITY_SOUND_ID);
		VarNumberSerializer.writeVarInt(serializer, id);
		VarNumberSerializer.writeVarInt(serializer, category);
		VarNumberSerializer.writeVarInt(serializer, entityId);
		serializer.writeFloat(volume);
		serializer.writeFloat(pitch);
		return RecyclableSingletonList.create(serializer);
	}

}
