package protocolsupport.api.chat.components;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

/**
 * Chat component that displays a scoreboard score
 */
public class ScoreComponent extends BaseComponent {

	private final String playername;
	private final String objectivename;

	private String forcedvalue;

	public ScoreComponent(String playername, String objectivename) {
		this.playername = playername;
		this.objectivename = objectivename;
	}

	public String getPlayerName() {
		return playername;
	}

	public String getObjectiveName() {
		return objectivename;
	}

	public boolean hasValue() {
		return forcedvalue != null;
	}

	public void setValue(String value) {
		forcedvalue = value;
	}

	@Override
	public String getValue(String locale) {
		String value = forcedvalue;
		if (value == null) {
			Player player = Bukkit.getPlayerExact(playername);
			if (player != null) {
				Scoreboard board = player.getScoreboard();
				Objective objective = board.getObjective(objectivename);
				if (objective != null) {
					value = String.valueOf(objective.getScore(playername).getScore());
				}
			}
		}
		return value != null ? value : "";
	}

}
