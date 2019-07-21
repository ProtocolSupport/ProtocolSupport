package protocolsupport.protocol.packet.middleimpl;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.PacketType;

public interface IPacketData {

	public PacketType getPacketType();

	public int getDataLength();

	public void writeData(ByteBuf to);

}
