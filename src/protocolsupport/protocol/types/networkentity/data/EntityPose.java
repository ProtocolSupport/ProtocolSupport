package protocolsupport.protocol.types.networkentity.data;

import protocolsupport.protocol.utils.EnumConstantLookup;

public enum EntityPose {
	STANDING,
	FALL_FLYING,
	SLEEPING,
	SWIMMING,
	SPIN_ATTACK,
	SNEAKING,
	LONG_JUMPING,
	DYING,
	CROAKING,
	USING_TONGUE,
	SITTING,
	ROARING,
	SNIFFING,
	EMERGIN,
	DIGGINGG;
	public static final EnumConstantLookup<EntityPose> CONSTANT_LOOKUP = new EnumConstantLookup<>(EntityPose.class);
}
