package protocolsupport.protocol.packet.middleimpl.clientbound.login.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.login.MiddleLoginDisconnect;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.KickDisconnect;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class LoginDisconnect extends MiddleLoginDisconnect {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		return KickDisconnect.create(version, messageJson);
	}

}
