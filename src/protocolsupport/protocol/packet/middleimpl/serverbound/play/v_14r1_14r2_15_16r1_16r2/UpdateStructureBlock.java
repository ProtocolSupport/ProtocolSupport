package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_14r1_14r2_15_16r1_16r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleUpdateStructureBlock;

public class UpdateStructureBlock extends MiddleUpdateStructureBlock {

	public UpdateStructureBlock(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		PositionCodec.readPosition(clientdata, position);
		action = MiscDataCodec.readVarIntEnum(clientdata, Action.CONSTANT_LOOKUP);
		mode = MiscDataCodec.readVarIntEnum(clientdata, Mode.CONSTANT_LOOKUP);
		name = StringCodec.readVarIntUTF8String(clientdata, Short.MAX_VALUE);
		offsetX = clientdata.readByte();
		offsetY = clientdata.readByte();
		offsetZ = clientdata.readByte();
		sizeX = clientdata.readByte();
		sizeY = clientdata.readByte();
		sizeZ = clientdata.readByte();
		mirror = MiscDataCodec.readVarIntEnum(clientdata, Mirror.CONSTANT_LOOKUP);
		rotation = MiscDataCodec.readVarIntEnum(clientdata, Rotation.CONSTANT_LOOKUP);
		metadata = StringCodec.readVarIntUTF8String(clientdata, Short.MAX_VALUE);
		integrity = clientdata.readFloat();
		seed = VarNumberCodec.readVarLong(clientdata);
		flags = clientdata.readByte();
	}

}
