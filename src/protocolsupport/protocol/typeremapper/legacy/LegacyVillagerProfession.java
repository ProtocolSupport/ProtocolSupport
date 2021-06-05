package protocolsupport.protocol.typeremapper.legacy;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

import protocolsupport.protocol.types.VillagerProfession;

public class LegacyVillagerProfession {

	private LegacyVillagerProfession() {
	}

	public static @Nonnegative int toLegacyId(@Nonnull VillagerProfession villagerdata) {
		switch (villagerdata) {
			case FARMER: {
				return 0;
			}
			case LIBRARIAN: {
				return 1;
			}
			case CLERIC: {
				return 2;
			}
			case TOOLSMITH:
			case WEAPONSMITH:
			case ARMORER: {
				return 3;
			}
			case BUTCHER: {
				return 4;
			}
			default: {
				return 0;
			}
		}
	}

}
