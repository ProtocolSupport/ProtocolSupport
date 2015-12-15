package protocolsupport.protocol.transformer.middlepacketimpl.v_1_7.clientbound.login;

import java.util.Collection;
import java.util.Collections;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.login.MiddleLoginSuccess;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;

public class LoginSuccess extends MiddleLoginSuccess<Collection<PacketData>> {

	@Override
	public Collection<PacketData> toData(ProtocolVersion version) {
		if (version == ProtocolVersion.MINECRAFT_1_7_5) {
			uuidstring = uuidstring.replace("-", "");
		}
		PacketDataSerializer serializer = PacketDataSerializer.createNew(version);
		serializer.writeString(uuidstring);
		serializer.writeString(name);
		return Collections.singletonList(new PacketData(ClientBoundPacket.LOGIN_SUCCESS_ID, serializer));
	}

}
