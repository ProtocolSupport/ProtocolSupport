package protocolsupport.protocol.packet.mcpe.packet.mcpe.clientbound;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.PEPacketIDs;

public class SetEntityEffect implements ClientboundPEPacket {

	public static final int ACTION_ADD = 1;
	public static final int ACTION_MODFIY = 2;
	public static final int ACTION_REMOVE = 3;

	protected int entityId;
	protected int action;
	protected int effectId;
	protected int amplifier;
	protected boolean particles;
	protected int duration;

	public SetEntityEffect(int entityId, int effectId) {
		this.action = ACTION_REMOVE;
		this.entityId = entityId;
		this.effectId = effectId;
	}

	public SetEntityEffect(int entityId, int effectId, int amplifier, boolean particles, int duration) {
		this.action = ACTION_ADD;
		this.entityId = entityId;
		this.effectId = effectId;
		this.amplifier = amplifier;
		this.particles = particles;
		this.duration = duration;
	}

	@Override
	public int getId() {
		return PEPacketIDs.MOB_EFFECT_PACKET;
	}

	@Override
	public ClientboundPEPacket encode(ByteBuf buf) throws Exception {
		buf.writeLong(entityId);
		buf.writeByte(action);
		buf.writeByte(effectId);
		buf.writeByte(amplifier);
		buf.writeBoolean(particles);
		buf.writeInt(duration);
		return this;
	}

}
