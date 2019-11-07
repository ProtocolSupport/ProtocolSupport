package protocolsupport.protocol.utils.pingresponse;

import protocolsupport.api.Connection;
import protocolsupport.api.events.ServerPingResponseEvent;
import protocolsupport.zplatform.PlatformUtils;
import protocolsupport.zplatform.ServerPlatform;

public abstract class PingResponseHandler {

	public abstract ServerPingResponseEvent createResponse(Connection connection);

	protected static String createServerVersionString() {
		PlatformUtils putils = ServerPlatform.get().getMiscUtils();
		return putils.getModName() + " " + putils.getVersionName();
	}

}
