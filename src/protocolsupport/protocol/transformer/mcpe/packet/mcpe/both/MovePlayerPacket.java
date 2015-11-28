package protocolsupport.protocol.transformer.mcpe.packet.mcpe.both;

import io.netty.buffer.ByteBuf;

import java.util.Collections;
import java.util.List;

import protocolsupport.protocol.ServerboundPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.DualPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ServerboundPEPacket;
import protocolsupport.utils.PacketCreator;

import net.minecraft.server.v1_8_R3.Packet;

public class MovePlayerPacket implements DualPEPacket {

	protected long entityId;
	protected float x;
	protected float y;
	protected float z;
	protected float yaw;
	protected float pitch;
	protected float headYaw;
	protected byte mode;
	protected boolean onGround;

	public MovePlayerPacket() {
	}

	public MovePlayerPacket(int entityId, float x, float y, float z, float yaw, float headYaw, float pitch, boolean onGround) {
		this.entityId = entityId;
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.headYaw = headYaw;
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
		this.headYaw = buf.readFloat();
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
		buf.writeFloat(headYaw);
		buf.writeFloat(pitch);
		buf.writeByte(mode);
		buf.writeBoolean(onGround);
		return this;
	}

	@Override
	public List<? extends Packet<?>> transfrom() throws Exception {
		PacketCreator creator = new PacketCreator(ServerboundPacket.PLAY_POSITION_LOOK.get());
		creator.writeDouble(x);
		creator.writeDouble(y);
		creator.writeDouble(z);
		creator.writeFloat(yaw);
		creator.writeFloat(pitch);
		creator.writeBoolean(onGround);
		return Collections.singletonList(creator.create());
	}

}
