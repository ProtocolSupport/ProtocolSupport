package protocolsupport.protocol.transformer.mcpe.packet.mcpe.serverbound;

import io.netty.buffer.ByteBuf;

import java.util.Collections;
import java.util.List;

import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;
import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity.EnumEntityUseAction;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ServerboundPEPacket;
import protocolsupport.utils.Allocator;

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
		PacketPlayInUseEntity useent = new PacketPlayInUseEntity();
		PacketDataSerializer serializer = new PacketDataSerializer(Allocator.allocateBuffer(), ProtocolVersion.MINECRAFT_PE);
		try {
			serializer.writeVarInt((int) targetId);
			serializer.a(EnumEntityUseAction.ATTACK);
			useent.a(serializer);
		} finally {
			serializer.release();
		}
		return Collections.singletonList(useent);
	}

}
