package protocolsupport.protocol.transformer.mcpe.packet.mcpe.both;

import io.netty.buffer.ByteBuf;

import java.util.Collections;
import java.util.List;

import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInArmAnimation;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.DualPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ServerboundPEPacket;

public class AnimatePacket implements DualPEPacket {

	@Override
	public int getId() {
		return 0xA3;
	}

	@Override
	public ServerboundPEPacket decode(ByteBuf buf) throws Exception {
		buf.readByte();
		buf.readLong();
		return this;
	}

	@Override
	public ClientboundPEPacket encode(ByteBuf buf) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<? extends Packet> transfrom() throws Exception {
		return Collections.singletonList(new PacketPlayInArmAnimation());
	}

}
