package protocolsupport.protocol.transformer.mcpe.packet.mcpe.both;

import io.netty.buffer.ByteBuf;

import java.util.Collections;
import java.util.List;

import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.DualPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ServerboundPEPacket;
import protocolsupport.utils.Allocator;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying.PacketPlayInPositionLook;

public class MovePlayerPacket implements DualPEPacket {

	protected long entityId;
	protected float x;
	protected float y;
	protected float z;
	protected float yaw;
	protected float pitch;
	protected float bodyYaw;
	protected byte mode;
	protected boolean onGround;

	public MovePlayerPacket() {
	}

	public MovePlayerPacket(int entityId, float x, float y, float z, float yaw, float pitch, boolean onGround) {
		this.entityId = entityId;
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
		this.onGround = onGround;
	}

	@Override
	public int getId() {
		return PEPacketIDs.MOVE_PLAYER_PACKET;
	}

	@Override
	public ServerboundPEPacket decode(ByteBuf buf) throws Exception {
		this.entityId = buf.readLong();
        this.x = buf.readFloat();
        this.y = buf.readFloat() - 1.62f;
        this.z = buf.readFloat();
        this.yaw = buf.readFloat();
        this.bodyYaw = buf.readFloat();
        this.pitch = buf.readFloat();
        this.mode = buf.readByte();
        this.onGround = buf.readBoolean();
        return this;
	}

	@Override
	public ClientboundPEPacket encode(ByteBuf buf) throws Exception {
		buf.writeLong(entityId);
		buf.writeFloat(x);
		buf.writeFloat(y + 1.62F);
		buf.writeFloat(z);
		buf.writeFloat(yaw);
		buf.writeFloat(0);
		buf.writeFloat(pitch);
		buf.writeByte(mode);
		buf.writeBoolean(onGround);
		return this;
	}

	@Override
	public List<? extends Packet<?>> transfrom() throws Exception {
		PacketPlayInPositionLook mlook = new PacketPlayInPositionLook();
		ByteBuf packetdata = Allocator.allocateBuffer();
		try {
			packetdata.writeDouble(x);
			packetdata.writeDouble(y);
			packetdata.writeDouble(z);
			packetdata.writeFloat(yaw);
			packetdata.writeFloat(pitch);
			packetdata.writeBoolean(onGround);
			mlook.a(new PacketDataSerializer(packetdata));
		} finally {
			packetdata.release();
		}
		return Collections.singletonList(mlook);
	}

}
