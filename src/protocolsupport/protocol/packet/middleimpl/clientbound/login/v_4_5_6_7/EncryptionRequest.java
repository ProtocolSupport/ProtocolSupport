package protocolsupport.protocol.packet.middleimpl.clientbound.login.v_4_5_6_7;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.login.MiddleEncryptionRequest;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EncryptionRequest extends MiddleEncryptionRequest {

	public EncryptionRequest(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.LOGIN_ENCRYPTION_BEGIN_ID);
		StringSerializer.writeString(serializer, version, serverId);
		ArraySerializer.writeShortByteArray(serializer, publicKey);
		ArraySerializer.writeShortByteArray(serializer, verifyToken);
		return RecyclableSingletonList.create(serializer);
	}

}
