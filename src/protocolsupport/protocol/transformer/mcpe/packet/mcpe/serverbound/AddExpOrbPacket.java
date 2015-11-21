package protocolsupport.protocol.transformer.mcpe.packet.mcpe.serverbound;

import java.util.Collections;
import java.util.List;

import io.netty.buffer.ByteBuf;

import net.minecraft.server.v1_8_R3.Packet;

import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ServerboundPEPacket;

public class AddExpOrbPacket implements ServerboundPEPacket {

	protected int entityId;
	protected float part1;
	protected float part2;
	protected float part3;
	protected int unknown;

	@Override
	public int getId() {
		return PEPacketIDs.ADD_EXP_ORB_PACKET;
	}

	@Override
	public ServerboundPEPacket decode(ByteBuf buf) throws Exception {
		entityId = (int) buf.readLong();
		part1 = buf.readFloat();
		part2 = buf.readFloat();
		part3 = buf.readFloat();
		unknown = buf.readInt();
		return this;
	}

	@Override
	public List<? extends Packet<?>> transfrom() throws Exception {
		System.out.println(part1);
		System.out.println(part2);
		System.out.println(part2);
		System.out.println(unknown);
		return Collections.emptyList();
	}

}
