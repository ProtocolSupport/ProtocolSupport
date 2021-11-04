package protocolsupport.protocol.packet.middle.impl.serverbound.play.v_12r1;

import java.util.function.Function;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.packet.middle.base.serverbound.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV12r1;

public class PrepareCraftingGrid extends ServerBoundMiddlePacket implements IServerboundMiddlePacketV12r1 {

	public PrepareCraftingGrid(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		clientdata.readByte();
		clientdata.readShort();
		Function<ByteBuf, Void> elementReader = from -> {
			ItemStackCodec.readItemStack(from, version);
			from.readByte();
			from.readByte();
			return null;
		};
		ArrayCodec.readShortTArray(clientdata, Void.class, elementReader);
		ArrayCodec.readShortTArray(clientdata, Void.class, elementReader);
	}

	@Override
	protected void write() {
	}

}
