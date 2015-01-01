package protocolsupport.protocol.watchedentites;

public abstract class WatchedEntity {

	private int id;

	public WatchedEntity(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public abstract boolean isLiving();

	public abstract boolean isAgeable();

	public abstract boolean isEnderman();

	public abstract boolean isWolf();

	public abstract boolean isMinecart();

	public abstract boolean isBoat();

	public abstract boolean isItemFrame();

	public abstract boolean isFallingBlockOrTnt();

}
