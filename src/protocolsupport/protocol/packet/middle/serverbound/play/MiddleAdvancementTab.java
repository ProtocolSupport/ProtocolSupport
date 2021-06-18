package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.utils.EnumConstantLookup;

public abstract class MiddleAdvancementTab extends ServerBoundMiddlePacket {

	protected MiddleAdvancementTab(MiddlePacketInit init) {
		super(init);
	}

	protected Action action;
	protected String identifier;

	@Override
	protected void write() {
		ServerBoundPacketData advanvementtab = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_ADVANCEMENT_TAB);
		MiscDataCodec.writeVarIntEnum(advanvementtab, action);
		if (action == Action.OPEN) {
			StringCodec.writeVarIntUTF8String(advanvementtab, identifier);
		}
		codec.writeServerbound(advanvementtab);
	}

	protected enum Action {
		OPEN, CLOSE;
		public static final EnumConstantLookup<Action> CONSTANT_LOOKUP = new EnumConstantLookup<>(Action.class);
	}

}
