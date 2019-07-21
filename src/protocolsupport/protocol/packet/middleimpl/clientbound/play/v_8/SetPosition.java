package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSetPosition;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class SetPosition extends MiddleSetPosition {

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

	@Override
	public boolean postFromServerRead() {
		if (teleportConfirmId != 0) {
			cache.getMovementCache().setTeleportLocation(x, y, z, teleportConfirmId);
		}
		return true;
	}

}
