package protocolsupport.protocol.typeremapper.watchedentity.types;

public abstract class WatchedEntity {

	private final int id;

	public WatchedEntity(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public abstract WatchedType getType();

	@Override
	public String toString() {
		return new StringBuilder(100)
		.append("Id: ").append(getId()).append(", ")
		.append("Type: ").append(getType())
		.toString();
	}

}
