package protocolsupport.protocol.types;

import protocolsupport.api.utils.Any;

public record VibrationPath(Any<Position, VibrationEntityData> destination, int arrivalTime) {

	public static record VibrationEntityData(int entityId, float eyeHeight) {
	}

}
