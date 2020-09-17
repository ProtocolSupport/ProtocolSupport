package protocolsupport.protocol.packet.handler;

import protocolsupport.api.chat.components.BaseComponent;

public interface IPacketListener {

	public void disconnect(BaseComponent message);

	public void destroy();

}
