package protocolsupport.protocol.packet.middle.serverbound.login;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleEncryptionResponse extends ServerBoundMiddlePacket {

	public MiddleEncryptionResponse(ConnectionImpl connection) {
		super(connection);
	}

	protected ByteBuf sharedSecret;
	protected ByteBuf verifyToken;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.LOGIN_ENCRYPTION_BEGIN);
		ArraySerializer.writeVarIntByteArray(creator, sharedSecret);
		ArraySerializer.writeVarIntByteArray(creator, verifyToken);
		return RecyclableSingletonList.create(creator);
	}

}
