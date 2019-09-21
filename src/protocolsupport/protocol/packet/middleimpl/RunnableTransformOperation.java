package protocolsupport.protocol.packet.middleimpl;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.PacketType;

public class RunnableTransformOperation implements IPacketData {

	protected final Runnable runnable;
	public RunnableTransformOperation(Runnable runnable) {
		this.runnable = runnable;
	}

	@Override
	public void writeData(ByteBuf to) {
		runnable.run();
	}

	@Override
	public int getDataLength() {
		return 0;
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.NONE;
	}

	@Override
	public void recycle() {
	}

	@Override
	public RunnableTransformOperation clone() {
		return new RunnableTransformOperation(runnable);
	}

}
