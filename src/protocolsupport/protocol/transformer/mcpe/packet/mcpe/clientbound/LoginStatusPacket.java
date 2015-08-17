package protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;

public class LoginStatusPacket implements ClientboundPEPacket {

	protected Status status;

	public LoginStatusPacket(Status status) {
		this.status = status;
	}

	@Override
	public int getId() {
		return PEPacketIDs.PLAY_STATUS_PACKET;
	}

	@Override
	public ClientboundPEPacket encode(ByteBuf buf) throws Exception {
		buf.writeInt(status.ordinal());
		return this;
	}

	public static enum Status {
		LOGIN_SUCCESS, LOGIN_FAILED_CLIENT, LOGIN_FAILED_SERVER, PLAYER_SPAWN
	}

}
