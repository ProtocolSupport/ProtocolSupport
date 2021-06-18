package protocolsupport.protocol.types;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.utils.Any;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;

public class VibrationPath {

	public static VibrationPath fromNetworkData(ByteBuf buffer) {
		Position source = PositionCodec.readPosition(buffer);
		String destinationType = StringCodec.readVarIntUTF8String(buffer);
		Any<Position, Integer> destination = switch (destinationType) {
			case "block", "minecraft:block" -> new Any<>(PositionCodec.readPosition(buffer), null);
			case "entity", "minecraft:entity" -> new Any<>(null, VarNumberCodec.readVarInt(buffer));
			default -> throw new IllegalArgumentException("Unexpected destination type " + destinationType);
		};
		int arrivalTime = VarNumberCodec.readVarInt(buffer);
		return new VibrationPath(source, destination, arrivalTime);
	}

	public static void writeNetworkData(ByteBuf buffer, VibrationPath path) {
		PositionCodec.writePosition(buffer, path.getSource());
		Any<Position, Integer> destination = path.getDestination();
		if (destination.hasObj1()) {
			StringCodec.writeVarIntUTF8String(buffer, "minecraft:block");
			PositionCodec.writePosition(buffer, destination.getObj1());
		} else {
			StringCodec.writeVarIntUTF8String(buffer, "minecraft:entity");
			VarNumberCodec.writeVarInt(buffer, destination.getObj2());
		}
		VarNumberCodec.writeVarInt(buffer, path.getArrivaltime());
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
