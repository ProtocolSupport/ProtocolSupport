package protocolsupport.protocol.transformer.mcpe.packet.mcpe.both;

import java.util.ArrayList;
import java.util.List;

import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.DualPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketRegistry;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ServerboundPEPacket;
import protocolsupport.utils.CompressionUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.server.v1_8_R3.Packet;

public class BatchPacket implements DualPEPacket {

	public BatchPacket() {
	}

	public BatchPacket(ClientboundPEPacket packet) {
		packets.add(packet);
	}

	protected ArrayList<PEPacket> packets = new ArrayList<PEPacket>();

	@Override
	public int getId() {
		return PEPacketIDs.BATCH_PACKET;
	}

	@Override
	public BatchPacket decode(ByteBuf buf) throws Exception {
        ByteBuf uncompressedbuf = CompressionUtils.uncompress(buf.readBytes(buf.readInt()), 1024 * 1024 * 64);
        while (uncompressedbuf.isReadable()) {
        	ByteBuf packetData = uncompressedbuf.readBytes(uncompressedbuf.readInt());
            packets.add(PEPacketRegistry.getPacket(packetData.readUnsignedByte()).decode(packetData));
        }
        return this;
	}

	@Override
	public BatchPacket encode(ByteBuf buf) throws Exception {
		ByteBuf temporal = Unpooled.buffer();
		for (PEPacket pepacket : packets) {
			ByteBuf packetData = Unpooled.buffer();
			packetData.writeByte(pepacket.getId());
			((ClientboundPEPacket) pepacket).encode(packetData);
			temporal.writeInt(packetData.readableBytes());
			temporal.writeBytes(packetData);
		}
		ByteBuf compressed = CompressionUtils.compress(temporal);
		buf.writeInt(compressed.readableBytes());
		buf.writeBytes(compressed);
		return this;
	}

	@Override
	public List<Packet<?>> transfrom() throws Exception {
		ArrayList<Packet<?>> result = new ArrayList<Packet<?>>();
		for (PEPacket pepacket : packets) {
			result.addAll(((ServerboundPEPacket) pepacket).transfrom());
		}
		return result;
	}

}
