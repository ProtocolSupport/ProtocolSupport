package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

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
		matches = ArraySerializer.readVarIntTArray(serverdata, CommandMatch.class, (data) -> {
			String match = StringSerializer.readString(serverdata, ProtocolVersionsHelper.LATEST_PC, 32767);
			boolean hasTooltip = serverdata.readBoolean();
			String tooltip = null;
			if (hasTooltip) {
				tooltip = StringSerializer.readString(serverdata, ProtocolVersionsHelper.LATEST_PC);
			}
			return new CommandMatch(match, hasTooltip, tooltip);
		});
	}

	public class CommandMatch {
		String match;
		boolean hasTooltip;
		String tooltip;

		private CommandMatch(String match, boolean hasTooltip, String tooltip) {
			this.match = match;
			this.hasTooltip = hasTooltip;
			this.tooltip = tooltip;
		}

		public String getMatch() {
			return match;
		}

		public boolean hasTooltip() {
			return hasTooltip;
		}

		public String getTooltip() {
			return tooltip;
		}
	}
}
