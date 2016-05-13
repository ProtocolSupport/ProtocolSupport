package protocolsupport.protocol.packet.mcpe.packet.mcpe.both;

import java.util.Collections;
import java.util.List;

import io.netty.buffer.ByteBuf;

import net.minecraft.server.v1_9_R2.Packet;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.DualPEPacket;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.PEPacketIDs;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.ServerboundPEPacket;
import protocolsupport.protocol.storage.SharedStorage;

public class PongPacket implements DualPEPacket {

	protected long pingId;

	public PongPacket() {
	}

	public PongPacket(long pingId) {
		this.pingId = pingId;
	}

	@Override
	public int getId() {
		return PEPacketIDs.PONG;
	}

	@Override
	public ServerboundPEPacket decode(ByteBuf buf) throws Exception {
		pingId = buf.readLong();
		return this;
	}

	@Override
	public ClientboundPEPacket encode(ByteBuf buf) throws Exception {
		buf.writeLong(pingId);
		return this;
	}

	@Override
	public List<? extends Packet<?>> transfrom(SharedStorage storage) throws Exception {
		return Collections.emptyList();
	}

}
