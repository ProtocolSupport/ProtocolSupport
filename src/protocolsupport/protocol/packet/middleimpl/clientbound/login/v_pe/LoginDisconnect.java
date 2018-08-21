package protocolsupport.protocol.packet.middleimpl.clientbound.login.v_pe;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.login.MiddleLoginDisconnect;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.KickDisconnect;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class LoginDisconnect extends MiddleLoginDisconnect {

	public LoginDisconnect(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		return KickDisconnect.create(connection.getVersion(), cache.getAttributesCache().getLocale(), message);
	}

}
