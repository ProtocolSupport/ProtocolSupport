package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_12r1;

import java.util.function.Function;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;

public class PrepareCraftingGrid extends ServerBoundMiddlePacket {

	public PrepareCraftingGrid(MiddlePacketInit init) {
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
