package protocolsupport.protocol.types.networkentity.data;

import protocolsupport.protocol.utils.EnumConstantLookup;

public enum VillagerProfession {

	NONE,
	ARMORER,
	BUTCHER,
	CARTOGRAPHER,
	CLERIC,
	FARMER,
	FISHERMAN,
	FLETCHER,
	LEATHERWORKER,
	LIBRARIAN,
	MASON,
	NITWIT,
	SHEPHERD,
	TOOLSMITH,
	WEAPONSMITH;

	public static final EnumConstantLookup<VillagerProfession> CONSTANT_LOOKUP = new EnumConstantLookup<>(VillagerProfession.class);

}
