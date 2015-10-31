package protocolsupport.protocol.transformer.mcpe.packet.mcpe.both;

import java.util.Collections;
import java.util.List;

import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.mcpe.PEPlayerInventory;
import protocolsupport.protocol.transformer.mcpe.packet.SynchronizedHandleNMSPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.DualPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ServerboundPEPacket;

public class EntityEquipmentInventoryPacket implements DualPEPacket {

	protected long entityId;
	protected ItemStack itemstack;
	protected int invSlot;
	protected int hotbarSlot;

	public EntityEquipmentInventoryPacket() {
	}

	public EntityEquipmentInventoryPacket(int entityId, ItemStack itemstack) {
		this.entityId = entityId;
		this.itemstack = itemstack;
	}

	@Override
	public int getId() {
		return PEPacketIDs.MOB_EQUIPMENT_PACKET;
	}

	@Override
	public ClientboundPEPacket encode(ByteBuf buf) throws Exception {
		PacketDataSerializer serializer = new PacketDataSerializer(buf, ProtocolVersion.MINECRAFT_PE);
		serializer.writeLong(entityId);
		serializer.writeItemStack(itemstack);
		serializer.writeByte(invSlot);
		serializer.writeByte(hotbarSlot);
		return this;
	}

	@Override
	public ServerboundPEPacket decode(ByteBuf buf) throws Exception {
		PacketDataSerializer serializer = new PacketDataSerializer(buf, ProtocolVersion.MINECRAFT_PE);
		entityId = serializer.readLong();
		itemstack = serializer.readItemStack();
		invSlot = serializer.readByte();
		hotbarSlot = serializer.readByte();
		return this;
	}

	@Override
	public List<? extends Packet<?>> transfrom() throws Exception {
		return Collections.singletonList(new SynchronizedHandleNMSPacket<PlayerConnection>() {
			@Override
			public void handle(PlayerConnection listener) {
				int realSlot = -1;
				if (invSlot >= 9 && invSlot <= 35) {
					realSlot = invSlot;
				} else if (invSlot >= 36 && invSlot <= 44) {
					realSlot = invSlot - 36;
				}
				if (realSlot != -1) {
					PEPlayerInventory inventory = (PEPlayerInventory) listener.player.inventory;
					inventory.setSelectedSlot(realSlot);
					inventory.setHotbarRef(realSlot, hotbarSlot);
				}
			}
		});
	}

}
