package protocolsupport.api.chat.components;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreComponent extends BaseComponent {

	private String playername;
	private String objectivename;
	private String value;

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

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String getValue() {
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
		if (value == null) {
			value = "";
		}
		return value;
	}

}
