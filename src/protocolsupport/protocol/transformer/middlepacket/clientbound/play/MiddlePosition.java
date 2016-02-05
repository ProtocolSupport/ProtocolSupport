package protocolsupport.protocol.transformer.middlepacket.clientbound.play;

import org.bukkit.Location;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.ClientBoundMiddlePacket;

public abstract class MiddlePosition<T> extends ClientBoundMiddlePacket<T> {

	protected double x;
	protected double y;
	protected double z;
	protected float yaw;
	protected float pitch;

	@Override
	public boolean needsPlayer() {
		return true;
	}

	@Override
	public void readFromServerData(PacketDataSerializer serializer) {
		x = serializer.readDouble();
		y = serializer.readDouble();
		z = serializer.readDouble();
		yaw = serializer.readFloat();
		pitch = serializer.readFloat();
		short field = serializer.readByte();
		Location location = player.getLocation();
		if ((field & 0x01) != 0) {
			x += location.getX();
		}
		if ((field & 0x02) != 0) {
			y += location.getY();
		}
		if ((field & 0x04) != 0) {
			z += location.getX();
		}
		if ((field & 0x08) != 0) {
			yaw += location.getYaw();
		}
		if ((field & 0x10) != 0) {
			pitch += location.getPitch();
		}
	}

}
