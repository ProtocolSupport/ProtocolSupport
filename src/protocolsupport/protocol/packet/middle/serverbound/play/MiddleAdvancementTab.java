package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.api.ProtocolType;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleAdvancementTab extends ServerBoundMiddlePacket {

	protected Action action;
	protected String identifier;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.PLAY_ADVANCEMENT_TAB);
		MiscSerializer.writeEnum(creator, action);
		if (action == Action.OPEN) {
			StringSerializer.writeString(creator, ProtocolVersion.getLatest(ProtocolType.PC), identifier);
		}
		return RecyclableSingletonList.create(creator);
	}

	protected static enum Action {
		OPEN, CLOSE;
	}

}
