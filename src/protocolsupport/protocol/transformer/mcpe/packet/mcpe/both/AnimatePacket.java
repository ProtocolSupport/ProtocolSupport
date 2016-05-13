package protocolsupport.protocol.transformer.mcpe.packet.mcpe.both;

import io.netty.buffer.ByteBuf;

import java.util.Collections;
import java.util.List;

import net.minecraft.server.v1_9_R2.Packet;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middleimpl.PacketCreator;
import protocolsupport.protocol.storage.SharedStorage;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.DualPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ServerboundPEPacket;

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
	public List<? extends Packet<?>> transfrom(SharedStorage storage) throws Exception {
		PacketCreator creator = PacketCreator.create(ServerBoundPacket.PLAY_ANIMATION.get());
		creator.writeVarInt(0);
		return Collections.singletonList(creator.create());
	}

}
