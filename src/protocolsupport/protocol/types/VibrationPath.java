package protocolsupport.protocol.types;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.utils.Any;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class VibrationPath {

	public static VibrationPath fromNetworkData(ByteBuf buffer) {
		Position source = PositionSerializer.readPosition(buffer);
		String destinationType = StringSerializer.readVarIntUTF8String(buffer);
		Any<Position, Integer> destination = switch (destinationType) {
			case "block", "minecraft:block" -> new Any<>(PositionSerializer.readPosition(buffer), null);
			case "entity", "minecraft:entity" -> new Any<>(null, VarNumberSerializer.readVarInt(buffer));
			default -> throw new IllegalArgumentException("Unexpected destination type " + destinationType);
		};
		int arrivalTime = VarNumberSerializer.readVarInt(buffer);
		return new VibrationPath(source, destination, arrivalTime);
	}

	public static void writeNetworkData(ByteBuf buffer, VibrationPath path) {
		PositionSerializer.writePosition(buffer, path.getSource());
		Any<Position, Integer> destination = path.getDestination();
		if (destination.hasObj1()) {
			StringSerializer.writeVarIntUTF8String(buffer, "minecraft:block");
			PositionSerializer.writePosition(buffer, destination.getObj1());
		} else {
			StringSerializer.writeVarIntUTF8String(buffer, "minecraft:entity");
			VarNumberSerializer.writeVarInt(buffer, destination.getObj2());
		}
		VarNumberSerializer.writeVarInt(buffer, path.getArrivaltime());
	}

	protected Position source;
	protected Any<Position, Integer> destination;
	protected int arrivalTime;

	public VibrationPath(Position source, Any<Position, Integer> destination, int arrivalTime) {
		this.source = source;
		this.destination = destination;
		this.arrivalTime = arrivalTime;
	}

	public Position getSource() {
		return source;
	}

	public Any<Position, Integer> getDestination() {
		return destination;
	}

	public int getArrivaltime() {
		return arrivalTime;
	}

}
