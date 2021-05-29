package protocolsupport.protocol.types;

import protocolsupport.protocol.utils.EnumConstantLookup;

public enum EntityPose {
	STANDING,
	FALL_FLYING,
	SLEEPING,
	SWIMMING,
	SPIN_ATTACK,
	SNEAKING,
	DYING;
	public static final EnumConstantLookup<EntityPose> CONSTANT_LOOKUP = new EnumConstantLookup<>(EntityPose.class);
}
