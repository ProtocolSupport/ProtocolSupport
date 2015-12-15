package protocolsupport.protocol.transformer.middlepacket.serverbound.play;

import java.util.Collection;
import java.util.Collections;

import net.minecraft.server.v1_8_R3.Packet;

import protocolsupport.protocol.ServerBoundPacket;
import protocolsupport.protocol.transformer.middlepacket.ServerBoundMiddlePacket;
import protocolsupport.utils.PacketCreator;

public abstract class MiddleSteerVehicle extends ServerBoundMiddlePacket {

	protected float sideForce;
	protected float forwardForce;
	protected int flags;

	@Override
	public Collection<Packet<?>> toNative() throws Exception {
		PacketCreator creator = new PacketCreator(ServerBoundPacket.PLAY_STEER_VEHICLE.get());
		creator.writeFloat(sideForce);
		creator.writeFloat(forwardForce);
		creator.writeByte(flags);
		return Collections.<Packet<?>>singletonList(creator.create());
	}

}
