package protocolsupport.protocol.packet.middle.base.serverbound.play;

import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ServerBoundPacketData;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.base.serverbound.ServerBoundMiddlePacket;
import protocolsupport.protocol.utils.EnumConstantLookup;

public abstract class MiddleAdvancementTab extends ServerBoundMiddlePacket {

	protected MiddleAdvancementTab(IMiddlePacketInit init) {
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
		io.writeServerbound(advanvementtab);
	}

	protected enum Action {
		OPEN, CLOSE;
		public static final EnumConstantLookup<Action> CONSTANT_LOOKUP = new EnumConstantLookup<>(Action.class);
	}

}
