package protocolsupport.protocol.transformer.mcpe.packet.mcpe.serverbound;

import io.netty.buffer.ByteBuf;

import java.util.Collections;
import java.util.List;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Packet;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.ServerBoundPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ServerboundPEPacket;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketCreator;

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
		PacketCreator creator = PacketCreator.create(ServerBoundPacket.PLAY_BLOCK_DIG.get());
		creator.writeByte(3);
		creator.a(BlockPosition.ZERO);
		creator.writeByte(0);
		return Collections.singletonList(creator.create());
	}

}
