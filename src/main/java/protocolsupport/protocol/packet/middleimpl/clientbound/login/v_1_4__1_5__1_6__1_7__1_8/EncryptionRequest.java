package protocolsupport.protocol.packet.middleimpl.clientbound.login.v_1_4__1_5__1_6__1_7__1_8;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.login.MiddleEncryptionRequest;
import protocolsupport.protocol.packet.middleimpl.PacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EncryptionRequest extends MiddleEncryptionRequest<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) throws IOException {
		PacketData serializer = PacketData.create(ClientBoundPacket.LOGIN_ENCRYPTION_BEGIN_ID, version);
		serializer.writeString(serverId);
		serializer.writeByteArray(publicKey);
		serializer.writeByteArray(verifyToken);
		return RecyclableSingletonList.create(serializer);
	}

}
