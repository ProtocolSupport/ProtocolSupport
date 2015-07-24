package protocolsupport.protocol.transformer.mcpe.packet.mcpe.both;

import io.netty.buffer.ByteBuf;

import java.util.Collections;
import java.util.List;

import net.minecraft.server.v1_8_R3.Packet;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.DualPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ServerboundPEPacket;

public class SetHealthPacket implements DualPEPacket {

	protected int health;

	public SetHealthPacket() {
	}

	public SetHealthPacket(int health) {
		this.health = health;
	} 

	@Override
	public int getId() {
		return 0xA1;
	}

	@Override
	public ServerboundPEPacket decode(ByteBuf buf) throws Exception {
		health = buf.readInt();
		return this;
	}

	@Override
	public ClientboundPEPacket encode(ByteBuf buf) throws Exception {
		buf.writeInt(health);
		return this;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<? extends Packet> transfrom() throws Exception {
		return Collections.emptyList();
	}

}
