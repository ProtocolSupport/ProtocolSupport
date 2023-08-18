package protocolsupport.protocol.packet.middle.impl.serverbound.play.v_4__7;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.packet.middle.base.serverbound.play.MiddleBlockPlace;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV6;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV7;
import protocolsupport.protocol.types.UsedHand;

public class BlockPlace extends MiddleBlockPlace implements
IServerboundMiddlePacketV4,
IServerboundMiddlePacketV5,
IServerboundMiddlePacketV6,
IServerboundMiddlePacketV7 {

	public BlockPlace(IMiddlePacketInit init) {
		super(init);
		hand = UsedHand.MAIN;
		insideblock = false;
	}

	@Override
	protected void read(ByteBuf clientdata) {
		PositionCodec.readPositionIBI(clientdata, position);
		face = clientdata.readByte();
		ItemStackCodec.readItemStack(clientdata, version);
		cX = clientdata.readUnsignedByte() / 16.0F;
		cY = clientdata.readUnsignedByte() / 16.0F;
		cZ = clientdata.readUnsignedByte() / 16.0F;
	}

}
