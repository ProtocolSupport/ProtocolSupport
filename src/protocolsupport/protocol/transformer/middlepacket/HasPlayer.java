package protocolsupport.protocol.transformer.middlepacket;

import org.bukkit.entity.Player;

public class HasPlayer {

	protected Player player;

	public boolean needsPlayer() {
		return false;
	}

	public final void setPlayer(Player player) {
		this.player = player;
	}

}
