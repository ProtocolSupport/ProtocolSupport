package protocolsupport.protocol.packet.middle.serverbound.login;

import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleLoginStart extends ServerBoundMiddlePacket {

	protected String name;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() throws Exception {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.LOGIN_START);
		creator.writeString(name);
		return RecyclableSingletonList.create(creator);
	}

}
