package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleTabComplete extends ClientBoundMiddlePacket {

	public MiddleTabComplete(ConnectionImpl connection) {
		super(connection);
	}

	protected int id;
	protected int start;
	protected int length;
	protected CommandMatch[] matches;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		id = VarNumberSerializer.readVarInt(serverdata);
		start = VarNumberSerializer.readVarInt(serverdata);
		length = VarNumberSerializer.readVarInt(serverdata);
		matches = ArraySerializer.readVarIntTArray(serverdata, CommandMatch.class, data -> {
			String match = StringSerializer.readVarIntUTF8String(serverdata);
			String tooltip = serverdata.readBoolean() ? StringSerializer.readVarIntUTF8String(serverdata) : null;
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
