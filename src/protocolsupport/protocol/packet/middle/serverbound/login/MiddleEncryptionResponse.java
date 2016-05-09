package protocolsupport.protocol.packet.middle.serverbound.login;

import net.minecraft.server.v1_9_R1.Packet;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.PacketCreator;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleEncryptionResponse extends ServerBoundMiddlePacket {

	protected byte[] sharedSecret;
	protected byte[] verifyToken;

	@Override
	public RecyclableCollection<? extends Packet<?>> toNative() throws Exception {
		PacketCreator creator = PacketCreator.create(ServerBoundPacket.LOGIN_ENCRYPTION_BEGIN.get());
		creator.writeArray(sharedSecret);
		creator.writeArray(verifyToken);
		return RecyclableSingletonList.create(creator.create());
	}

}
