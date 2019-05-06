package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_9r1_9r2_10_11_12r1_12r2_13_14;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSetPosition;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class SetPosition extends MiddleSetPosition {

	public SetPosition(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_POSITION_ID);
		serializer.writeDouble(xOrig);
		serializer.writeDouble(yOrig);
		serializer.writeDouble(zOrig);
		serializer.writeFloat(yawOrig);
		serializer.writeFloat(pitchOrig);
		serializer.writeByte(flags);
		VarNumberSerializer.writeVarInt(serializer, teleportConfirmId);
		return RecyclableSingletonList.create(serializer);
	}

}
