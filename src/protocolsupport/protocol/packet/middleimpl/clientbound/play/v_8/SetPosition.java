package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8.AbstractSetPosition;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class SetPosition extends AbstractSetPosition {

	public SetPosition(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<? extends IPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_POSITION);
		if (teleportConfirmId == 0) {
			serializer.writeDouble(xOrig);
			serializer.writeDouble(yOrig);
			serializer.writeDouble(zOrig);
			serializer.writeFloat(yawOrig);
			serializer.writeFloat(pitchOrig);
			serializer.writeByte(flags);
		} else {
			serializer.writeDouble(x);
			serializer.writeDouble(y);
			serializer.writeDouble(z);
			serializer.writeFloat(yaw);
			serializer.writeFloat(pitch);
			serializer.writeByte(0);
		}
		return RecyclableSingletonList.create(serializer);
	}

}
