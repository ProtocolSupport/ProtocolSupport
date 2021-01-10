package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_12r1;

import java.util.function.Function;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.ItemStackSerializer;

public class PrepareCraftingGrid extends ServerBoundMiddlePacket {

	public PrepareCraftingGrid(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		clientdata.readByte();
		clientdata.readShort();
		Function<ByteBuf, Void> elementReader = from -> {
			ItemStackSerializer.readItemStack(from, version);
			from.readByte();
			from.readByte();
			return null;
		};
		ArraySerializer.readShortTArray(clientdata, Void.class, elementReader);
		ArraySerializer.readShortTArray(clientdata, Void.class, elementReader);
	}

	@Override
	protected void write() {
	}

}
