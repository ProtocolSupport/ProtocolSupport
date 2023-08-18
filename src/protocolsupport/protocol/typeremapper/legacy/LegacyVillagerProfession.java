package protocolsupport.protocol.typeremapper.legacy;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

import protocolsupport.protocol.types.networkentity.data.VillagerProfession;

public class LegacyVillagerProfession {

	private LegacyVillagerProfession() {
	}

	public static @Nonnegative int toLegacyId(@Nonnull VillagerProfession villagerdata) {
		return switch (villagerdata) {
			case FARMER -> 0;
			case LIBRARIAN -> 1;
			case CLERIC -> 2;
			case TOOLSMITH, WEAPONSMITH, ARMORER -> 3;
			case BUTCHER -> 4;
			default -> 0;
		};
	}

}
