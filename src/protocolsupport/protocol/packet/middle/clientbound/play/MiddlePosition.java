package protocolsupport.protocol.packet.middle.clientbound.play;

import org.bukkit.Location;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddlePosition<T> extends ClientBoundMiddlePacket<T> {

	protected double xOrig;
	protected double yOrig;
	protected double zOrig;
	protected float yawOrig;
	protected float pitchOrig;
	protected double x;
	protected double y;
	protected double z;
	protected float yaw;
	protected float pitch;
	protected int flags;
	protected int teleportConfirmId;

	@Override
	public boolean needsPlayer() {
		return true;
	}

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) {
		xOrig = x = serializer.readDouble();
		yOrig = y = serializer.readDouble();
		zOrig = z = serializer.readDouble();
		yawOrig = yaw = serializer.readFloat();
		pitchOrig = pitch = serializer.readFloat();
		flags = serializer.readByte();
		Location location = player.getLocation();
		if ((flags & 0x01) != 0) {
			x += location.getX();
		}
		if ((flags & 0x02) != 0) {
			y += location.getY();
		}
		if ((flags & 0x04) != 0) {
			z += location.getX();
		}
		if ((flags & 0x08) != 0) {
			yaw += location.getYaw();
		}
		if ((flags & 0x10) != 0) {
			pitch += location.getPitch();
		}
		teleportConfirmId = serializer.readVarInt();
	}

	@Override
	public void handle() {
		if (teleportConfirmId != 0) {
			sharedstorage.setTeleportLocation(x, y, z, teleportConfirmId);
		}
	}

}
