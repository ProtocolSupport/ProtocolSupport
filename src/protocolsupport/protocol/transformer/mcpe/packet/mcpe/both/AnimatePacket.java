package protocolsupport.protocol.transformer.mcpe.packet.mcpe.both;

import io.netty.buffer.ByteBuf;

import java.util.Collections;
import java.util.List;

import net.minecraft.server.v1_8_R3.Packet;

import protocolsupport.protocol.ServerboundPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.DualPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ServerboundPEPacket;
import protocolsupport.utils.PacketCreator;

public class AnimatePacket implements DualPEPacket {

	protected int action;
	protected int entityId;

	public AnimatePacket() {
	}

	public AnimatePacket(int action, int entityId) {
		this.action = action;
		this.entityId = entityId;
	}

	@Override
	public int getId() {
		return PEPacketIDs.ANIMATE_PACKET;
	}

	@Override
	public ServerboundPEPacket decode(ByteBuf buf) throws Exception {
		action = buf.readByte();
		entityId = (int) buf.readLong();
		return this;
	}

	@Override
	public ClientboundPEPacket encode(ByteBuf buf) throws Exception {
		buf.writeByte(action);
		buf.writeLong(entityId);
		return this;
	}

	@Override
	public List<? extends Packet<?>> transfrom() throws Exception {
		return Collections.singletonList(new PacketCreator(ServerboundPacket.PLAY_ANIMATION.get()).create());
	}

}
