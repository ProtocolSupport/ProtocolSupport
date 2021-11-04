package protocolsupport.protocol.packet.middle.impl.serverbound.play.v_13;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.base.serverbound.play.MiddleUpdateStructureBlock;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV13;

public class UpdateStructureBlock extends MiddleUpdateStructureBlock implements IServerboundMiddlePacketV13 {

	public UpdateStructureBlock(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		PositionCodec.readPositionLXYZ(clientdata, position);
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
