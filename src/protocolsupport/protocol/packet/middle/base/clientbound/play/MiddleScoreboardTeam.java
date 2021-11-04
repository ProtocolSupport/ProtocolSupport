package protocolsupport.protocol.packet.middle.base.clientbound.play;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.ChatColor;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.middle.MiddlePacketCancelException;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;
import protocolsupport.protocol.utils.EnumConstantLookup;

public abstract class MiddleScoreboardTeam extends ClientBoundMiddlePacket {

	protected MiddleScoreboardTeam(IMiddlePacketInit init) {
		super(init);
	}

	protected final ScoreboardTeamsTracker teamsTracker = new ScoreboardTeamsTracker();

	protected String name;
	protected Mode mode;
	protected BaseComponent displayName;
	protected BaseComponent prefix;
	protected BaseComponent suffix;
	protected int friendlyFire;
	protected String nameTagVisibility;
	protected String collisionRule;
	protected ChatColor format;
	protected String[] players;

	@Override
	protected void decode(ByteBuf serverdata) {
		name = StringCodec.readVarIntUTF8String(serverdata);
		mode = MiscDataCodec.readByteEnum(serverdata, Mode.CONSTANT_LOOKUP);
		if ((mode == Mode.CREATE) || (mode == Mode.UPDATE)) {
			displayName = ChatAPI.fromJSON(StringCodec.readVarIntUTF8String(serverdata), true);
			friendlyFire = serverdata.readUnsignedByte();
			nameTagVisibility = StringCodec.readVarIntUTF8String(serverdata);
			collisionRule = StringCodec.readVarIntUTF8String(serverdata);
			format = MiscDataCodec.readVarIntEnum(serverdata, EnumConstantLookup.CHAT_COLOR);
			prefix = ChatAPI.fromJSON(StringCodec.readVarIntUTF8String(serverdata), true);
			suffix = ChatAPI.fromJSON(StringCodec.readVarIntUTF8String(serverdata), true);
		}
		if ((mode == Mode.CREATE) || (mode == Mode.PLAYERS_ADD) || (mode == Mode.PLAYERS_REMOVE)) {
			players = ArrayCodec.readVarIntVarIntUTF8StringArray(serverdata);
		}

		switch (mode) {
			case CREATE: {
				if (!teamsTracker.addTeam(name, players)) {
					throw MiddlePacketCancelException.INSTANCE;
				}
				break;
			}
			case REMOVE: {
				if (!teamsTracker.removeTeam(name)) {
					throw MiddlePacketCancelException.INSTANCE;
				}
				break;
			}
			case PLAYERS_ADD: {
				if (!teamsTracker.addPlayersToTeam(name, players)) {
					throw MiddlePacketCancelException.INSTANCE;
				}
				break;
			}
			case PLAYERS_REMOVE: {
				if (!teamsTracker.removePlayersFromTeam(name, players)) {
					throw MiddlePacketCancelException.INSTANCE;
				}
				break;
			}
			default: {
				if (teamsTracker.getTeam(name) == null) {
					throw MiddlePacketCancelException.INSTANCE;
				}
				break;
			}
		}
	}

	protected enum Mode {
		CREATE, REMOVE, UPDATE, PLAYERS_ADD, PLAYERS_REMOVE;
		public static final EnumConstantLookup<Mode> CONSTANT_LOOKUP = new EnumConstantLookup<>(Mode.class);
	}

	public static class ScoreboardTeamsTracker {

		protected final Map<String, TrackedScoreboardTeam> teams = new HashMap<>();
		protected final Map<String, TrackedScoreboardTeam> playerTeam = new HashMap<>();

		public TrackedScoreboardTeam getTeam(String teamName) {
			return teams.get(teamName);
		}

		public boolean addTeam(String teamName, String[] players) {
			if (teams.containsKey(teamName)) {
				return false;
			}
			TrackedScoreboardTeam team = new TrackedScoreboardTeam(teamName);
			teams.put(teamName, team);
			addPlayersToTeam(team, players);
			return true;
		}

		public boolean addPlayersToTeam(String teamName, String[] players) {
			TrackedScoreboardTeam team = teams.get(teamName);
			if (team == null) {
				return false;
			}
			addPlayersToTeam(team, players);
			return true;
		}

		protected void addPlayersToTeam(TrackedScoreboardTeam team, String[] players) {
			for (String player : players) {
				team.addPlayer(player);
				TrackedScoreboardTeam oldTeam = playerTeam.put(player, team);
				if (oldTeam != null) {
					oldTeam.removePlayer(player);
				}
			}
		}

		public boolean removePlayersFromTeam(String teamName, String[] players) {
			TrackedScoreboardTeam team = teams.get(teamName);
			if (team == null) {
				return false;
			}
			Set<String> teamPlayers = team.players;
			for (String player : players) {
				if (!teamPlayers.contains(player)) {
					return false;
				}
			}
			for (String player : players) {
				teamPlayers.remove(player);
				playerTeam.remove(player);
			}
			return true;
		}

		public boolean removeTeam(String teamName) {
			TrackedScoreboardTeam team = teams.remove(teamName);
			if (team == null) {
				return false;
			}
			for (String player : team.players) {
				playerTeam.remove(player);
			}
			return true;
		}

		public static class TrackedScoreboardTeam {

			protected final String name;
			protected final Set<String> players = new HashSet<>();

			public TrackedScoreboardTeam(String name) {
				this.name = name;
			}

			public String getName() {
				return name;
			}

			public Set<String> getPlayers() {
				return players;
			}

			public void addPlayer(String player) {
				players.add(player);
			}

			public void removePlayer(String player) {
				players.remove(player);
			}

		}

	}

}
