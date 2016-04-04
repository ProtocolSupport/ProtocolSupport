package protocolsupport.protocol.transformer.mcpe.packet.mcpe.serverbound;

import java.util.Collections;
import java.util.List;

import io.netty.buffer.ByteBuf;
import net.minecraft.server.v1_9_R1.Packet;
import net.minecraft.server.v1_9_R1.PlayerConnection;
import protocolsupport.protocol.storage.SharedStorage;
import protocolsupport.protocol.transformer.mcpe.UDPNetworkManager;
import protocolsupport.protocol.transformer.mcpe.packet.HandleNMSPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ServerboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound.ChunkRadiusResponse;

public class ChunkRadiusRequest implements ServerboundPEPacket {

	protected int radius;

	@Override
	public int getId() {
		return PEPacketIDs.CHUNK_RADIUS_REQUEST;
	}

	@Override
	public ServerboundPEPacket decode(ByteBuf buf) throws Exception {
		radius = buf.readInt();
		return this;
	}

	@Override
	public List<? extends Packet<?>> transfrom(SharedStorage storage) throws Exception {
		return Collections.singletonList(new HandleNMSPacket<PlayerConnection>() {
			@Override
			public void handle0(PlayerConnection listener) throws Throwable {
				((UDPNetworkManager) listener.networkManager).sendPEPacket(new ChunkRadiusResponse(30));
			}
		});
	}

}
