package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.login.v_1_7;

import java.util.Collection;
import java.util.Collections;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.login.MiddleLoginSuccess;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;
import protocolsupport.protocol.transformer.middlepacketimpl.SupportedVersions;
import protocolsupportbuildprocessor.annotations.NeedsNoArgConstructor;

@NeedsNoArgConstructor
@SupportedVersions({ProtocolVersion.MINECRAFT_1_7_10, ProtocolVersion.MINECRAFT_1_7_5})
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
