package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13_14;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleStopSound;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class StopSound extends MiddleStopSound {

	public StopSound(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_STOP_SOUND);
		serializer.writeByte((source != -1 ? FLAG_SOURCE : 0) | (name != null ? FLAG_NAME : 0));
		if (source != -1) {
			VarNumberSerializer.writeVarInt(serializer, source);
		}
		if (name != null) {
			StringSerializer.writeString(serializer, version, name);
		}
		return RecyclableSingletonList.create(serializer);
	}

}