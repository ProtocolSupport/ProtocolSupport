package protocolsupport.protocol.packet.middleimpl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.UnpooledByteBufAllocator;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.utils.recyclable.Recyclable;

public interface IPacketData extends Recyclable {

	public static final ByteBufAllocator ALLOCATOR = new UnpooledByteBufAllocator(false);

	public PacketType getPacketType();

	public int getDataLength();

	public void writeData(ByteBuf to);

	public IPacketData clone();

}
