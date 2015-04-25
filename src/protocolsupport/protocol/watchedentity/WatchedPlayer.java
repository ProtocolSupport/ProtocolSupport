package protocolsupport.protocol.watchedentity;

public class WatchedPlayer extends WatchedEntity {

	public WatchedPlayer(int id) {
		super(id);
	}

	@Override
	public boolean isLiving() {
		return true;
	}

	@Override
	public boolean isAgeable() {
		return false;
	}

	@Override
	public boolean isEnderman() {
		return false;
	}

	@Override
	public boolean isWolf() {
		return false;
	}

	@Override
	public boolean isMinecart() {
		return false;
	}

	@Override
	public boolean isBoat() {
		return false;
	}

	@Override
	public boolean isItemFrame() {
		return false;
	}

	@Override
	public boolean isFallingBlockOrTnt() {
		return false;
	}

}
