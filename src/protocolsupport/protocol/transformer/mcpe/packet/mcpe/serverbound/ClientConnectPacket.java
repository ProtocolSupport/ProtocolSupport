package protocolsupport.protocol.transformer.mcpe.packet.mcpe.serverbound;

import io.netty.buffer.ByteBuf;

import java.util.Collections;
import java.util.List;

import protocolsupport.protocol.transformer.mcpe.handler.PELoginListener;
import protocolsupport.protocol.transformer.mcpe.packet.HandleNMSPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ServerboundPEPacket;
import net.minecraft.server.v1_8_R3.Packet;

public class ClientConnectPacket implements ServerboundPEPacket {

	protected long clientID;
	protected long sessionID;
	protected boolean useSecurity;

	@Override
	public int getId() {
		return PEPacketIDs.CLIENT_CONNECT;
	}

	@Override
	public ServerboundPEPacket decode(ByteBuf buf) throws Exception {
		clientID = buf.readLong();
		sessionID = buf.readLong();
		useSecurity = buf.readBoolean();
		return this;
	}

	@Override
	public List<? extends Packet<?>> transfrom() {
		return Collections.singletonList(new HandleNMSPacket<PELoginListener>() {
			@Override
			public void handle(PELoginListener listener) {
				listener.handleClientConnect(ClientConnectPacket.this);
			}
		});
	}

	public long getClientId() {
		return clientID;
	}

}
