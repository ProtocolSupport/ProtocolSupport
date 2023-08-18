package protocolsupport.protocol.types.networkentity.data;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

//TODO: enum for type
public class VillagerData {

	protected final int type;
	protected final VillagerProfession profession;
	protected final int level;

	public VillagerData(@Nonnegative int type, @Nonnull VillagerProfession profession, @Nonnegative int level) {
		this.type = type;
		this.profession = profession;
		this.level = level;
	}

	public @Nonnegative int getType() {
		return type;
	}

	public @Nonnull VillagerProfession getProfession() {
		return profession;
	}

	public @Nonnegative int getLevel() {
		return level;
	}

}
