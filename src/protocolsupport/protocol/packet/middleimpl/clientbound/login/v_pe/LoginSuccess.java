package protocolsupport.protocol.packet.middleimpl.clientbound.login.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.login.MiddleLoginSuccess;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class LoginSuccess extends MiddleLoginSuccess {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		return RecyclableSingletonList.create(createPlayStatus(connection.getVersion(), 0));
	}

	public static ClientBoundPacketData createPlayStatus(ProtocolVersion version, int status) {
		ClientBoundPacketData playstatus = ClientBoundPacketData.create(PEPacketIDs.PLAY_STATUS, version);
		playstatus.writeInt(status);
		return playstatus;
	}

}
