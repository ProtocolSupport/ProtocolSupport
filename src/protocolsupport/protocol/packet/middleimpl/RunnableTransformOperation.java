package protocolsupport.protocol.packet.middleimpl;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.PacketType;

public interface RunnableTransformOperation extends Runnable, IPacketData {

	@Override
	default PacketType getPacketType() {
		return PacketType.NONE;
	}

	@Override
	default void writeData(ByteBuf to) {
		run();
	}

}
