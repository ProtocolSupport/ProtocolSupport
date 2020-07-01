package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.NetworkEntityCache;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.NetworkEntityDataCache;
import protocolsupport.utils.BitUtils;

public abstract class MiddleSetPosition extends ClientBoundMiddlePacket {

	public MiddleSetPosition(ConnectionImpl connection) {
		super(connection);
	}

	protected static final int FLAGS_BIT_X = 0;
	protected static final int FLAGS_BIT_Y = 1;
	protected static final int FLAGS_BIT_Z = 2;
	protected static final int FLAGS_BIT_PITCH = 3;
	protected static final int FLAGS_BIT_YAW = 4;

	protected final NetworkEntityCache entityCache = cache.getEntityCache();

	protected NetworkEntity self;
	protected double x;
	protected double y;
	protected double z;
	protected float yaw;
	protected float pitch;
	protected int flags;
	protected int teleportConfirmId;

	@Override
	protected void readServerData(ByteBuf serverdata) {
		self = entityCache.getSelf();
		x = serverdata.readDouble();
		y = serverdata.readDouble();
		z = serverdata.readDouble();
		yaw = serverdata.readFloat();
		pitch = serverdata.readFloat();
		flags = serverdata.readByte();
		teleportConfirmId = VarNumberSerializer.readVarInt(serverdata);
	}

	@Override
	protected void handleReadData() {
		NetworkEntityDataCache ecache = self.getDataCache();

		if (BitUtils.isIBitSet(flags, FLAGS_BIT_X)) {
			ecache.addX(x);
		} else {
			ecache.setX(x);
		}
		if (BitUtils.isIBitSet(flags, FLAGS_BIT_Y)) {
			ecache.addY(y);
		} else {
			ecache.setY(y);
		}
		if (BitUtils.isIBitSet(flags, FLAGS_BIT_Z)) {
			ecache.addZ(z);
		} else {
			ecache.setZ(z);
		}
		if (BitUtils.isIBitSet(flags, FLAGS_BIT_PITCH)) {
			ecache.addPitch(pitch);
		} else {
			ecache.setPitch(pitch);
		}
		if (BitUtils.isIBitSet(flags, FLAGS_BIT_YAW)) {
			ecache.addYaw(yaw);
		} else {
			ecache.setYaw(yaw);
		}
	}

}
