package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleSteerVehicle extends ServerBoundMiddlePacket {

	public static final int FLAGS_BIT_JUMPING = 0;
	public static final int FLAGS_BIT_UNMOUNT = 1;

	public MiddleSteerVehicle(ConnectionImpl connection) {
		super(connection);
	}

	protected float sideForce;
	protected float forwardForce;
	protected int flags;

	@Override
	public RecyclableCollection<? extends IPacketData> toNative() {
		return RecyclableSingletonList.create(create(sideForce, forwardForce, flags));
	}

	public static ServerBoundPacketData create(float sideForce, float forwardForce, int flags) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(PacketType.SERVERBOUND_PLAY_STEER_VEHICLE);
		creator.writeFloat(sideForce);
		creator.writeFloat(forwardForce);
		creator.writeByte(flags);
		return creator;
	}

}
