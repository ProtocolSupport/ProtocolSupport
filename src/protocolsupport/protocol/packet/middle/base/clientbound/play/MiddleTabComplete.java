package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;

public abstract class MiddleTabComplete extends ClientBoundMiddlePacket {

	protected MiddleTabComplete(IMiddlePacketInit init) {
		super(init);
	}

	protected int id;
	protected int start;
	protected int length;
	protected CommandMatch[] matches;

	@Override
	protected void decode(ByteBuf serverdata) {
		id = VarNumberCodec.readVarInt(serverdata);
		start = VarNumberCodec.readVarInt(serverdata);
		length = VarNumberCodec.readVarInt(serverdata);
		matches = ArrayCodec.readVarIntTArray(serverdata, CommandMatch.class, data -> {
			String match = StringCodec.readVarIntUTF8String(serverdata);
			String tooltip = serverdata.readBoolean() ? StringCodec.readVarIntUTF8String(serverdata) : null;
			return new CommandMatch(match, tooltip);
		});
	}

	public static class CommandMatch {

		protected final String match;
		protected final String tooltip;

		public CommandMatch(String match, String tooltip) {
			this.match = match;
			this.tooltip = tooltip;
		}

		public String getMatch() {
			return match;
		}

		public boolean hasTooltip() {
			return tooltip != null;
		}

		public String getTooltip() {
			return tooltip;
		}

	}

}
