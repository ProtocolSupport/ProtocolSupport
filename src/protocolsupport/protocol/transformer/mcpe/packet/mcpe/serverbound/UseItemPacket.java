package protocolsupport.protocol.transformer.mcpe.packet.mcpe.serverbound;

import io.netty.buffer.ByteBuf;

import java.util.Collections;
import java.util.List;

import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ServerboundPEPacket;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockPlace;

public class UseItemPacket implements ServerboundPEPacket {

	protected int x;
	protected int y;
	protected int z;
	protected int face;
	protected short item;
	protected short meta;
	protected float fx;
	protected float fy;
	protected float fz;

	@Override
	public int getId() {
		return 0x9B;
	}

	@Override
	public ServerboundPEPacket decode(ByteBuf buf) throws Exception {
		this.x = buf.readInt();
		this.y = buf.readInt();
		this.z = buf.readInt();
		this.face = buf.readByte() & 0xFF;
		this.item = buf.readShort();
		this.meta = buf.readShort();
		buf.readLong();
		this.fx = buf.readFloat();
		this.fy = buf.readFloat();
		this.fz = buf.readFloat();
		buf.readFloat();
		buf.readFloat();
		buf.readFloat();
		return this;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<? extends Packet> transfrom() throws Exception {
		return Collections.singletonList(new PacketPlayInBlockPlace(new BlockPosition(x, y, z), face,  new ItemStack(Item.getById(item), meta), fx, fy, fz));
	}

}
