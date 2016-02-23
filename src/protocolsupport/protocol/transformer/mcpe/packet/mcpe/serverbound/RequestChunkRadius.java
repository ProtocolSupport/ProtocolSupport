package protocolsupport.protocol.transformer.mcpe.packet.mcpe.serverbound;

import java.util.Collections;
import java.util.List;

import io.netty.buffer.ByteBuf;
import net.minecraft.server.v1_8_R3.Packet;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ServerboundPEPacket;

public class RequestChunkRadius implements ServerboundPEPacket {

	@Override
	public int getId() {
		return PEPacketIDs.REQUEST_CHUNK_RADIUS;
	}

	@Override
	public ServerboundPEPacket decode(ByteBuf buf) throws Exception {
		//TODO: decompile MCPE and find out what those bytes actually mean
		buf.skipBytes(4);
		return this;
	}

	@Override
	public List<? extends Packet<?>> transfrom() throws Exception {
		return Collections.emptyList();
	}

}
