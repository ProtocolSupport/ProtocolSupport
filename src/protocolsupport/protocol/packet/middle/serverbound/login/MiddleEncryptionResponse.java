package protocolsupport.protocol.packet.middle.serverbound.login;

import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleEncryptionResponse extends ServerBoundMiddlePacket {

	protected byte[] sharedSecret;
	protected byte[] verifyToken;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.LOGIN_ENCRYPTION_BEGIN);
		creator.writeByteArray(sharedSecret);
		creator.writeByteArray(verifyToken);
		return RecyclableSingletonList.create(creator);
	}

}
