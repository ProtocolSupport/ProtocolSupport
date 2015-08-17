package protocolsupport.protocol.transformer.mcpe.packet.mcpe;

import io.netty.buffer.ByteBuf;

import java.util.List;

import net.minecraft.server.v1_8_R3.Packet;

public interface ServerboundPEPacket extends PEPacket {

	public ServerboundPEPacket decode(ByteBuf buf) throws Exception;

	public List<? extends Packet<?>> transfrom() throws Exception;

}
