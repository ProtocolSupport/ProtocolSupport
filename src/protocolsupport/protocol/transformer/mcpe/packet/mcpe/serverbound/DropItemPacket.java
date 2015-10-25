package protocolsupport.protocol.transformer.mcpe.packet.mcpe.serverbound;

import io.netty.buffer.ByteBuf;

import java.util.Collections;
import java.util.List;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockDig;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ServerboundPEPacket;
import protocolsupport.utils.Allocator;

public class DropItemPacket implements ServerboundPEPacket {

	@Override
	public int getId() {
		return PEPacketIDs.DROP_ITEM_PACKET;
	}

	@Override
	public ServerboundPEPacket decode(ByteBuf buf) throws Exception {
		PacketDataSerializer serializer = new PacketDataSerializer(buf, ProtocolVersion.MINECRAFT_PE);
		serializer.readByte();
		serializer.readItemStack();
		return this;
	}

	@Override
	public List<? extends Packet<?>> transfrom() throws Exception {
		PacketPlayInBlockDig dig = new PacketPlayInBlockDig();
		PacketDataSerializer serializer = new PacketDataSerializer(Allocator.allocateBuffer(), ProtocolVersion.MINECRAFT_PE);
		try {
			serializer.writeByte(3);
			serializer.a(BlockPosition.ZERO);
			serializer.writeByte(0);
			dig.a(serializer);
		} finally {
			serializer.release();
		}
		return Collections.singletonList(dig);
	}

}
