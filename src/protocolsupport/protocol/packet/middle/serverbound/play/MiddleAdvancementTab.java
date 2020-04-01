package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.utils.EnumConstantLookups;

public abstract class MiddleAdvancementTab extends ServerBoundMiddlePacket {

	public MiddleAdvancementTab(ConnectionImpl connection) {
		super(connection);
	}

	protected Action action;
	protected String identifier;

	@Override
	public void writeToServer() {
		ServerBoundPacketData advanvementtab = ServerBoundPacketData.create(PacketType.SERVERBOUND_PLAY_ADVANCEMENT_TAB);
		MiscSerializer.writeVarIntEnum(advanvementtab, action);
		if (action == Action.OPEN) {
			StringSerializer.writeVarIntUTF8String(advanvementtab, identifier);
		}
		codec.read(advanvementtab);
	}

	protected static enum Action {
		OPEN, CLOSE;
		public static final EnumConstantLookups.EnumConstantLookup<Action> CONSTANT_LOOKUP = new EnumConstantLookups.EnumConstantLookup<>(Action.class);
	}

}
