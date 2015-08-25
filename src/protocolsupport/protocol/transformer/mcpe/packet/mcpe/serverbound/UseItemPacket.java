package protocolsupport.protocol.transformer.mcpe.packet.mcpe.serverbound;

import io.netty.buffer.ByteBuf;

import java.util.Collections;
import java.util.List;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ServerboundPEPacket;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockPlace;

public class UseItemPacket implements ServerboundPEPacket {

	protected int x;
	protected int y;
	protected int z;
	protected int face;
	protected float fx;
	protected float fy;
	protected float fz;
	protected ItemStack item;

	@Override
	public int getId() {
		return PEPacketIDs.USE_ITEM_PACKET;
	}

	@Override
	public ServerboundPEPacket decode(ByteBuf buf) throws Exception {
		PacketDataSerializer serializer = new PacketDataSerializer(buf, ProtocolVersion.MINECRAFT_PE);
		this.x = buf.readInt();
		this.y = buf.readInt();
		this.z = buf.readInt();
		this.face = buf.readByte() & 0xFF;
		this.fx = buf.readFloat();
		this.fy = buf.readFloat();
		this.fz = buf.readFloat();
		buf.readFloat();
		buf.readFloat();
		buf.readFloat();
		this.item = serializer.readItemStack();
		return this;
	}

	@Override
	public List<? extends Packet<?>> transfrom() throws Exception {
		return Collections.singletonList(new PacketPlayInBlockPlace(new BlockPosition(x, y, z), face, item, fx, fy, fz));
	}

}
