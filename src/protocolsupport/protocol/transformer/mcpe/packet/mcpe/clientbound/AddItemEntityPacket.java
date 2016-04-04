package protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound;

import net.minecraft.server.v1_9_R1.ItemStack;
import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;

public class AddItemEntityPacket implements ClientboundPEPacket {

	protected int entityId;
	protected float x;
	protected float y;
	protected float z;
	protected float speedX;
	protected float speedY;
	protected float speedZ;
	protected ItemStack itemstack;

	public AddItemEntityPacket(int entityId, float x, float y, float z, float speedX, float speedY, float speedZ, ItemStack itemstack) {
		this.entityId = entityId;
		this.x = x;
		this.y = y;
		this.z = z;
		this.speedX = speedX;
		this.speedY = speedY;
		this.speedZ = speedZ;
		this.itemstack = itemstack;
	}

	@Override
	public int getId() {
		return PEPacketIDs.ADD_ITEM_ENTITY_PACKET;
	}

	@Override
	public ClientboundPEPacket encode(ByteBuf buf) throws Exception {
		PacketDataSerializer serializer = new PacketDataSerializer(buf, ProtocolVersion.MINECRAFT_PE);
		serializer.writeLong(entityId);
		serializer.writeItemStack(itemstack);
		serializer.writeFloat(x);
		serializer.writeFloat(y);
		serializer.writeFloat(z);
		serializer.writeFloat(speedX);
		serializer.writeFloat(speedY);
		serializer.writeFloat(speedZ);
		return this;
	}

}
