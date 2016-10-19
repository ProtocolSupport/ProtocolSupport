package protocolsupport.protocol.packet.middleimpl.clientbound.login.v_1_7__1_8__1_9_r1__1_9_r2__1_10;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.login.MiddleLoginSuccess;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class LoginSuccess extends MiddleLoginSuccess<RecyclableCollection<ClientBoundPacketData>> {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		if (version == ProtocolVersion.MINECRAFT_1_7_5) {
			uuidstring = uuidstring.replace("-", "");
		}
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.LOGIN_SUCCESS_ID, version);
		serializer.writeString(uuidstring);
		serializer.writeString(name);
		return RecyclableSingletonList.create(serializer);
	}

}
