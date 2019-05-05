package protocolsupport.protocol.types;

//TODO: enums for type and profession
public class VillagerData {

	protected final int type;
	protected final int profession;
	protected final int level;

	public VillagerData(int type, int profession, int level) {
		this.type = type;
		this.profession = profession;
		this.level = level;
	}

	public int getType() {
		return type;
	}

	public int getProfession() {
		return profession;
	}

	public int getLevel() {
		return level;
	}

}
