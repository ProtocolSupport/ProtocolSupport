package protocolsupport.protocol.transformer.mcpe.packet.mcpe.serverbound;

import io.netty.buffer.ByteBuf;

import java.util.Collections;
import java.util.List;

import protocolsupport.protocol.transformer.mcpe.packet.SynchronizedHandleNMSPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ServerboundPEPacket;

import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketListener;

public class ClientDisconnectPacket implements ServerboundPEPacket {

	@Override
	public int getId() {
		return 0x15;
	}

	@Override
	public ServerboundPEPacket decode(ByteBuf buf) throws Exception {
		return this;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<? extends Packet> transfrom() throws Exception {
		return Collections.singletonList(new SynchronizedHandleNMSPacket<PacketListener>() {
			@Override
			public void handle(PacketListener listener) {
				listener.a(new ChatComponentText("Disconnected"));
			}
		});
	}

}
