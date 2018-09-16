package protocolsupport.protocol.packet.middle.clientbound.play;

import org.bukkit.Location;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleSetPosition extends ClientBoundMiddlePacket {

	public MiddleSetPosition(ConnectionImpl connection) {
		super(connection);
	}

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
	public void readFromServerData(ByteBuf serverdata) {
		xOrig = x = serverdata.readDouble();
		yOrig = y = serverdata.readDouble();
		zOrig = z = serverdata.readDouble();
		yawOrig = yaw = serverdata.readFloat();
		pitchOrig = pitch = serverdata.readFloat();
		flags = serverdata.readByte();
		if (flags != 0) {
			Location location = connection.getPlayer().getLocation();
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
				pitch += location.getPitch();
			}
			if ((flags & 0x10) != 0) {
				yaw += location.getYaw();
			}
		}
		teleportConfirmId = VarNumberSerializer.readVarInt(serverdata);
	}

}
