package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_14r1_14r2_15;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleUpdateStructureBlock;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class UpdateStructureBlock extends MiddleUpdateStructureBlock {

	public UpdateStructureBlock(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void readClientData(ByteBuf clientdata) {
		PositionSerializer.readPositionTo(clientdata, position);
		action = MiscSerializer.readVarIntEnum(clientdata, Action.CONSTANT_LOOKUP);
		mode = MiscSerializer.readVarIntEnum(clientdata, Mode.CONSTANT_LOOKUP);
		name = StringSerializer.readVarIntUTF8String(clientdata, Short.MAX_VALUE);
		offsetX = clientdata.readByte();
		offsetY = clientdata.readByte();
		offsetZ = clientdata.readByte();
		sizeX = clientdata.readByte();
		sizeY = clientdata.readByte();
		sizeZ = clientdata.readByte();
		mirror = MiscSerializer.readVarIntEnum(clientdata, Mirror.CONSTANT_LOOKUP);
		rotation = MiscSerializer.readVarIntEnum(clientdata, Rotation.CONSTANT_LOOKUP);
		metadata = StringSerializer.readVarIntUTF8String(clientdata, Short.MAX_VALUE);
		integrity = clientdata.readFloat();
		seed = VarNumberSerializer.readVarLong(clientdata);
		flags = clientdata.readByte();
	}

}
