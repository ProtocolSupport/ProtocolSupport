package protocolsupport.protocol.packet.middleimpl;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.utils.recyclable.Recyclable;

public interface IPacketData extends Recyclable {

	public PacketType getPacketType();

	public int getDataLength();

	public void writeData(ByteBuf to);

}
