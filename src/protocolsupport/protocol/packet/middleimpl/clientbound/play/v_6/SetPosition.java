package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_6;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSetPosition;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class SetPosition extends MiddleSetPosition {

	public SetPosition(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		y += +1.6200000047683716D;
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_POSITION_ID);
		serializer.writeDouble(x);
		serializer.writeDouble(y);
		serializer.writeDouble(y + 1.6200000047683716D);
		serializer.writeDouble(z);
		serializer.writeFloat(yaw);
		serializer.writeFloat(pitch);
		serializer.writeBoolean(false);
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
