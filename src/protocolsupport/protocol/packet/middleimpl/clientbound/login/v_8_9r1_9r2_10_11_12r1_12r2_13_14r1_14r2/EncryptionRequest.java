package protocolsupport.protocol.packet.middleimpl.clientbound.login.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.login.MiddleEncryptionRequest;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EncryptionRequest extends MiddleEncryptionRequest {

	public EncryptionRequest(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<? extends IPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PacketType.CLIENTBOUND_LOGIN_ENCRYPTION_BEGIN);
		StringSerializer.writeVarIntUTF8String(serializer, serverId);
		ArraySerializer.writeVarIntByteArray(serializer, publicKey);
		ArraySerializer.writeVarIntByteArray(serializer, verifyToken);
		return RecyclableSingletonList.create(serializer);
	}

}
