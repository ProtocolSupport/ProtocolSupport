package protocolsupport.protocol.transformer.mcpe.packet.mcpe;

import io.netty.buffer.ByteBuf;

import java.util.List;

import net.minecraft.server.v1_9_R2.Packet;
import protocolsupport.protocol.storage.SharedStorage;
import protocolsupportbuildprocessor.annotations.NeedsNoArgConstructor;

@NeedsNoArgConstructor
public interface ServerboundPEPacket extends PEPacket {

	public ServerboundPEPacket decode(ByteBuf buf) throws Exception;

	public List<? extends Packet<?>> transfrom(SharedStorage sharedstorage) throws Exception;

}
