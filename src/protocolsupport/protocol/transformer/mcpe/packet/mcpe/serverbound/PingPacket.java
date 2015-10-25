package protocolsupport.protocol.transformer.mcpe.packet.mcpe.serverbound;

import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.mcpe.handler.PELoginListener;
import protocolsupport.protocol.transformer.mcpe.packet.HandleNMSPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ServerboundPEPacket;
import protocolsupport.utils.Allocator;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketListener;
import net.minecraft.server.v1_8_R3.PacketPlayInKeepAlive;
import net.minecraft.server.v1_8_R3.PlayerConnection;

public class PingPacket implements ServerboundPEPacket {

	protected long pingId;

	public PingPacket() {
	}

	@Override
	public int getId() {
		return PEPacketIDs.PING;
	}

	@Override
	public ServerboundPEPacket decode(ByteBuf buf) throws Exception {
		pingId = buf.readLong();
		return this;
	}

	@Override
	public List<? extends Packet<?>> transfrom() throws Exception {
		return Collections.singletonList(new HandleNMSPacket<PacketListener>() {
			@Override
			public void handle(PacketListener listener) {
				if (listener instanceof PELoginListener) {
					((PELoginListener) listener).handleKeepALive(PingPacket.this);
				} else if (listener instanceof PlayerConnection) {
					((PlayerConnection) listener).a(getPlayKeepAlive());
				}
			}
		});
	}

	private PacketPlayInKeepAlive getPlayKeepAlive() {
		PacketPlayInKeepAlive packet = new PacketPlayInKeepAlive();
		PacketDataSerializer serializer = new PacketDataSerializer(Allocator.allocateBuffer(), ProtocolVersion.MINECRAFT_1_8);
		try {
			serializer.writeVarInt((int) pingId);
			packet.a(serializer);
			return packet;
		} catch (IOException e) {
		} finally {
			serializer.release();
		}
		return new PacketPlayInKeepAlive();
	}

	public long getKeepAliveId() {
		return pingId;
	}

}
