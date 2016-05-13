package protocolsupport.protocol.packet.mcpe.packet.mcpe.serverbound;

import io.netty.buffer.ByteBuf;

import java.util.Collections;
import java.util.List;

import protocolsupport.protocol.packet.mcpe.packet.SynchronizedHandleNMSPacket;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.PEPacketIDs;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.ServerboundPEPacket;
import protocolsupport.protocol.storage.SharedStorage;
import net.minecraft.server.v1_9_R2.ChatComponentText;
import net.minecraft.server.v1_9_R2.Packet;
import net.minecraft.server.v1_9_R2.PacketListener;

public class ClientDisconnectPacket implements ServerboundPEPacket {

	@Override
	public int getId() {
		return PEPacketIDs.CLIENT_DISCONNECT;
	}

	@Override
	public ServerboundPEPacket decode(ByteBuf buf) throws Exception {
		return this;
	}

	@Override
	public List<? extends Packet<?>> transfrom(SharedStorage storage) throws Exception {
		return Collections.singletonList(new SynchronizedHandleNMSPacket<PacketListener>() {
			@Override
			public void handle0(PacketListener listener) {
				listener.a(new ChatComponentText("Disconnected"));
			}
		});
	}

}
