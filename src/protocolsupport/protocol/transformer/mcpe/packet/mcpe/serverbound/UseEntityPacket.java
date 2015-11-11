package protocolsupport.protocol.transformer.mcpe.packet.mcpe.serverbound;

import io.netty.buffer.ByteBuf;

import java.util.Collections;
import java.util.List;

import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity.EnumEntityUseAction;

import protocolsupport.protocol.ServerboundPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ServerboundPEPacket;
import protocolsupport.utils.PacketCreator;

public class UseEntityPacket implements ServerboundPEPacket {

	protected byte action;
	protected long targetId;

	@Override
	public int getId() {
		return PEPacketIDs.INTERACT_PACKET;
	}

	@Override
	public ServerboundPEPacket decode(ByteBuf buf) throws Exception {
		action = buf.readByte();
		targetId = buf.readLong();
		return this;
	}

	@Override
	public List<? extends Packet<?>> transfrom() throws Exception {
		PacketCreator creator = new PacketCreator(ServerboundPacket.PLAY_USE_ENTITY.get());
		creator.writeVarInt((int) targetId);
		creator.a(EnumEntityUseAction.ATTACK);
		return Collections.singletonList(creator.create());
	}

}
