package protocolsupport.protocol.transformer.mcpe.packet.mcpe.serverbound;

import io.netty.buffer.ByteBuf;

import java.util.Collections;
import java.util.List;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.mcpe.handler.PELoginListener;
import protocolsupport.protocol.transformer.mcpe.packet.HandleNMSPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ServerboundPEPacket;
import net.minecraft.server.v1_8_R3.Packet;

public class ClientHandshakePacket implements ServerboundPEPacket {

	@Override
	public int getId() {
		return PEPacketIDs.CLIENT_HANDSHAKE;
	}

	@Override
	public ServerboundPEPacket decode(ByteBuf buf) throws Exception {
		PacketDataSerializer serializer = new PacketDataSerializer(buf, ProtocolVersion.MINECRAFT_PE);
		for (int i = 0; i < 11; i++) {
			serializer.readAddress();
		}
		serializer.readLong();
		serializer.readLong();
		return this;
	}

	@Override
	public List<? extends Packet<?>> transfrom() {
		return Collections.singletonList(new HandleNMSPacket<PELoginListener>() {
			@Override
			public void handle(PELoginListener listener) {
				listener.handleHandshake();
			}
		});
	}

}
