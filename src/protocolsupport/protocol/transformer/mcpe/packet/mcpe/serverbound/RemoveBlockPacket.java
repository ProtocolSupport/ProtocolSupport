package protocolsupport.protocol.transformer.mcpe.packet.mcpe.serverbound;

import io.netty.buffer.ByteBuf;

import java.util.Collections;
import java.util.List;

import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ServerboundPEPacket;
import protocolsupport.utils.Allocator;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockDig;

public class RemoveBlockPacket implements ServerboundPEPacket {

    protected int x;
    protected int z;
    protected int y;

	@Override
	public int getId() {
		return PEPacketIDs.REMOVE_BLOCK_PACKET;
	}

	@Override
	public ServerboundPEPacket decode(ByteBuf buf) throws Exception {
		buf.readLong();
		this.x = buf.readInt();
		this.z = buf.readInt();
		this.y = buf.readByte();
		return this;
	}

	@Override
	public List<? extends Packet<?>> transfrom() throws Exception {
		PacketPlayInBlockDig dig = new PacketPlayInBlockDig();
		PacketDataSerializer packetdata = new PacketDataSerializer(Allocator.allocateBuffer());
		try {
			packetdata.writeByte(2);
			packetdata.a(new BlockPosition(x, y, z));
			packetdata.writeByte(1);
			dig.a(new PacketDataSerializer(packetdata));
		} finally {
			packetdata.release();
		}
		return Collections.singletonList(dig);
	}

}
