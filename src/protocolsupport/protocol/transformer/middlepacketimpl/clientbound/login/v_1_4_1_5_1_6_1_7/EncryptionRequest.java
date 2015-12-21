package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.login.v_1_4_1_5_1_6_1_7;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.login.MiddleEncryptionRequest;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;

public class EncryptionRequest extends MiddleEncryptionRequest<Collection<PacketData>> {

	@Override
	public Collection<PacketData> toData(ProtocolVersion version) throws IOException {
		PacketDataSerializer serializer = PacketDataSerializer.createNew(version);
		serializer.writeString(serverId);
		serializer.writeArray(publicKey);
		serializer.writeArray(verifyToken);
		return Collections.singletonList(new PacketData(ClientBoundPacket.LOGIN_ENCRYPTION_BEGIN_ID, serializer));
	}

}
