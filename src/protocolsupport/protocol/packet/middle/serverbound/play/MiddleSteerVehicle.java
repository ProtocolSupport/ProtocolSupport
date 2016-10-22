package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleSteerVehicle extends ServerBoundMiddlePacket {

	protected float sideForce;
	protected float forwardForce;
	protected int flags;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.PLAY_STEER_VEHICLE);
		creator.writeFloat(sideForce);
		creator.writeFloat(forwardForce);
		creator.writeByte(flags);
		return RecyclableSingletonList.create(creator);
	}

}
