package protocolsupport.protocol.codec;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.utils.Any;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.VibrationPath;
import protocolsupport.protocol.types.VibrationPath.VibrationEntityData;

public class VibrationPathCodec {

	public static VibrationPath readVibrationPath(ByteBuf buffer) {
		String destinationType = StringCodec.readVarIntUTF8String(buffer);
		Any<Position, VibrationEntityData> destination = switch (destinationType) {
			case "block", "minecraft:block" -> new Any<>(PositionCodec.readPosition(buffer), null);
			case "entity", "minecraft:entity" -> {
				int entityId = VarNumberCodec.readVarInt(buffer);
				float eyeHeight = buffer.readFloat();
				yield new Any<>(null, new VibrationEntityData(entityId, eyeHeight));
			}
			default -> throw new IllegalArgumentException("Unexpected destination type " + destinationType);
		};
		int arrivalTime = VarNumberCodec.readVarInt(buffer);
		return new VibrationPath(destination, arrivalTime);
	}

	public static void writeVibrationPath(ByteBuf buffer, VibrationPath path) {
		Any<Position, VibrationEntityData> destination = path.destination();
		if (destination.hasObj1()) {
			StringCodec.writeVarIntUTF8String(buffer, "minecraft:block");
			PositionCodec.writePosition(buffer, destination.getObj1());
		} else {
			StringCodec.writeVarIntUTF8String(buffer, "minecraft:entity");
			VibrationEntityData entitydata = destination.getObj2();
			VarNumberCodec.writeVarInt(buffer, entitydata.entityId());
			buffer.writeFloat(entitydata.eyeHeight());
		}
		VarNumberCodec.writeVarInt(buffer, path.arrivalTime());
	}

	public static void writeVibrationPathSource(ByteBuf buffer, Position source, VibrationPath path) {
		PositionCodec.writePosition(buffer, source);
		Any<Position, VibrationEntityData> destination = path.destination();
		if (destination.hasObj1()) {
			StringCodec.writeVarIntUTF8String(buffer, "minecraft:block");
			PositionCodec.writePosition(buffer, destination.getObj1());
		} else {
			StringCodec.writeVarIntUTF8String(buffer, "minecraft:entity");
			VarNumberCodec.writeVarInt(buffer, destination.getObj2().entityId());
		}
		VarNumberCodec.writeVarInt(buffer, path.arrivalTime());
	}

}
