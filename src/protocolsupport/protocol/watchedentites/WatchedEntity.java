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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(100);
		builder
		.append("Id: ").append(getId()).append(", ")
		.append("living: ").append(isLiving()).append(", ")
		.append("ageable: ").append(isAgeable()).append(", ")
		.append("enderman: ").append(isEnderman()).append(", ")
		.append("wolf: ").append(isWolf()).append(", ")
		.append("minecart: ").append(isMinecart()).append(", ")
		.append("boat: ").append(isBoat()).append(", ")
		.append("itemframe: ").append(isItemFrame()).append(", ")
		.append("falling: ").append(isFallingBlockOrTnt());
		return builder.toString();
	}

}
