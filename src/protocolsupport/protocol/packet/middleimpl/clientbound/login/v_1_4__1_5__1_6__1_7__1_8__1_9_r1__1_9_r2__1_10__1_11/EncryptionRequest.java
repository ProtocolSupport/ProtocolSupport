package protocolsupport.protocol.packet.middleimpl.clientbound.login.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2__1_10__1_11;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.login.MiddleEncryptionRequest;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EncryptionRequest extends MiddleEncryptionRequest {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.LOGIN_ENCRYPTION_BEGIN_ID, version);
		StringSerializer.writeString(serializer, version, serverId);
		ArraySerializer.writeByteArray(serializer, version, publicKey);
		ArraySerializer.writeByteArray(serializer, version, verifyToken);
		return RecyclableSingletonList.create(serializer);
	}

}
