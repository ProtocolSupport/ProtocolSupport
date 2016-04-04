package protocolsupport.protocol.transformer.mcpe.packet.mcpe.serverbound;

import io.netty.buffer.ByteBuf;

import java.util.Collections;
import java.util.List;

import protocolsupport.protocol.storage.SharedStorage;
import protocolsupport.protocol.transformer.mcpe.handler.PELoginListener;
import protocolsupport.protocol.transformer.mcpe.packet.HandleNMSPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ServerboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.raknet.RakNetDataSerializer;
import net.minecraft.server.v1_9_R1.Packet;

public class ClientHandshakePacket implements ServerboundPEPacket {

	@Override
	public int getId() {
		return PEPacketIDs.CLIENT_HANDSHAKE;
	}

	@Override
	public ServerboundPEPacket decode(ByteBuf buf) throws Exception {
		for (int i = 0; i < 11; i++) {
			RakNetDataSerializer.readAddress(buf);
		}
		buf.readLong();
		buf.readLong();
		return this;
	}

	@Override
	public List<? extends Packet<?>> transfrom(SharedStorage storage) {
		return Collections.singletonList(new HandleNMSPacket<PELoginListener>() {
			@Override
			public void handle0(PELoginListener listener) {
				listener.handleHandshake();
			}
		});
	}

}
