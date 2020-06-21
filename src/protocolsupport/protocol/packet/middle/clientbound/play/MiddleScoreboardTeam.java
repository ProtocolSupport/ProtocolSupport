package protocolsupport.protocol.packet.middle.clientbound.play;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.ChatColor;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.CancelMiddlePacketException;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.utils.EnumConstantLookups;
import protocolsupport.protocol.utils.EnumConstantLookups.EnumConstantLookup;

public abstract class MiddleScoreboardTeam extends ClientBoundMiddlePacket {

	public MiddleScoreboardTeam(ConnectionImpl connection) {
		super(connection);
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
	protected void readServerData(ByteBuf serverdata) {
		name = StringSerializer.readVarIntUTF8String(serverdata);
		mode = MiscSerializer.readByteEnum(serverdata, Mode.CONSTANT_LOOKUP);
		if ((mode == Mode.CREATE) || (mode == Mode.UPDATE)) {
			displayName = ChatAPI.fromJSON(StringSerializer.readVarIntUTF8String(serverdata), true);
			friendlyFire = serverdata.readUnsignedByte();
			nameTagVisibility = StringSerializer.readVarIntUTF8String(serverdata);
			collisionRule = StringSerializer.readVarIntUTF8String(serverdata);
			format = MiscSerializer.readVarIntEnum(serverdata, EnumConstantLookups.CHAT_COLOR);
			prefix = ChatAPI.fromJSON(StringSerializer.readVarIntUTF8String(serverdata), true);
			suffix = ChatAPI.fromJSON(StringSerializer.readVarIntUTF8String(serverdata), true);
		}
		if ((mode == Mode.CREATE) || (mode == Mode.PLAYERS_ADD) || (mode == Mode.PLAYERS_REMOVE)) {
			players = ArraySerializer.readVarIntVarIntUTF8StringArray(serverdata);
		}

		switch (mode) {
			case CREATE: {
				if (!teamsTracker.addTeam(name, players)) {
					throw CancelMiddlePacketException.INSTANCE;
				}
				break;
			}
			case REMOVE: {
				if (!teamsTracker.removeTeam(name)) {
					throw CancelMiddlePacketException.INSTANCE;
				}
				break;
			}
			case PLAYERS_ADD: {
				if (!teamsTracker.addPlayersToTeam(name, players)) {
					throw CancelMiddlePacketException.INSTANCE;
				}
				break;
			}
			case PLAYERS_REMOVE: {
				if (!teamsTracker.removePlayersFromTeam(name, players)) {
					throw CancelMiddlePacketException.INSTANCE;
				}
				break;
			}
			default: {
				if (teamsTracker.getTeam(name) == null) {
					throw CancelMiddlePacketException.INSTANCE;
				}
				break;
			}
		}
	}

	protected static enum Mode {
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
