package protocolsupport.protocol.types;

import protocolsupport.protocol.utils.EnumConstantLookups;

public enum EntityPose {
	STANDING,
	FALL_FLYING,
	SLEEPING,
	SWIMMING,
	SPIN_ATTACK,
	SNEAKING,
	DYING;
	public static final EnumConstantLookups.EnumConstantLookup<EntityPose> CONSTANT_LOOKUP = new EnumConstantLookups.EnumConstantLookup<>(EntityPose.class);
}
