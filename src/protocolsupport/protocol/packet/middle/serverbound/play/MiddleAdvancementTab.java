package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.utils.EnumConstantLookups;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleAdvancementTab extends ServerBoundMiddlePacket {

	public MiddleAdvancementTab(ConnectionImpl connection) {
		super(connection);
	}

	protected Action action;
	protected String identifier;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.PLAY_ADVANCEMENT_TAB);
		MiscSerializer.writeVarIntEnum(creator, action);
		if (action == Action.OPEN) {
			StringSerializer.writeVarIntUTF8String(creator, identifier);
		}
		return RecyclableSingletonList.create(creator);
	}

	protected static enum Action {
		OPEN, CLOSE;
		public static final EnumConstantLookups.EnumConstantLookup<Action> CONSTANT_LOOKUP = new EnumConstantLookups.EnumConstantLookup<>(Action.class);
	}

}
