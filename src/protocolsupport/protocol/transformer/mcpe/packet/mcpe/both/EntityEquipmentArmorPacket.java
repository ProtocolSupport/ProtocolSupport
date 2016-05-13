package protocolsupport.protocol.transformer.mcpe.packet.mcpe.both;

import io.netty.buffer.ByteBuf;

import java.util.Collections;
import java.util.List;

import net.minecraft.server.v1_9_R2.ItemStack;
import net.minecraft.server.v1_9_R2.Packet;
import net.minecraft.server.v1_9_R2.PlayerConnection;
import net.minecraft.server.v1_9_R2.PlayerInventory;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.PacketDataSerializer;
import protocolsupport.protocol.storage.SharedStorage;
import protocolsupport.protocol.transformer.mcpe.PEPlayerInventory;
import protocolsupport.protocol.transformer.mcpe.packet.SynchronizedHandleNMSPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.DualPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ServerboundPEPacket;
import protocolsupport.utils.Utils;

public class EntityEquipmentArmorPacket implements DualPEPacket {

	protected long entityId;
	protected ItemStack[] armor = new ItemStack[4];

	public EntityEquipmentArmorPacket() {
	}

	public EntityEquipmentArmorPacket(int entityId, ItemStack[] armor) {
		this.entityId = entityId;
		System.arraycopy(armor, 0, this.armor, 0, this.armor.length);
	}

	@Override
	public int getId() {
		return PEPacketIDs.MOB_ARMOR_EQUIPMENT_PACKET;
	}

	@Override
	public ServerboundPEPacket decode(ByteBuf buf) throws Exception {
		PacketDataSerializer serializer = new PacketDataSerializer(buf, ProtocolVersion.MINECRAFT_PE);
		entityId = serializer.readLong();
		for (int i = 0; i < armor.length; i++) {
			armor[i] = serializer.readItemStack();
		}
		return this;
	}

	@Override
	public ClientboundPEPacket encode(ByteBuf buf) throws Exception {
		PacketDataSerializer serializer = new PacketDataSerializer(buf, ProtocolVersion.MINECRAFT_PE);
		serializer.writeLong(entityId);
		for (int i = 0; i < armor.length; i++) {
			serializer.writeItemStack(armor[i]);
		}
		return this;
	}

	@Override
	public List<? extends Packet<?>> transfrom(SharedStorage storage) throws Exception {
		return Collections.singletonList(new SynchronizedHandleNMSPacket<PlayerConnection>() {
			@Override
			public void handle0(PlayerConnection listener) {
				PlayerInventory inventory = listener.player.inventory;
				ItemStack[] realArmorArr = inventory.armor;
				ItemStack[] packetArmorArr = Utils.reverseArray(armor);
				for (int i = 0; i < realArmorArr.length; i++) {
					ItemStack realArmor = realArmorArr[i];
					ItemStack packetArmor = packetArmorArr[i];
					if (packetArmor == null && realArmor != null) { //player unequipped armor
						int firstEmpty = inventory.getFirstEmptySlotIndex();
						if (firstEmpty != -1) {
							inventory.setItem(firstEmpty, realArmor);
							realArmorArr[i] = null;
						}
					} else if (packetArmor != null && !matches(packetArmor, realArmor)) {//player equipped or swapped armor
						int slotFrom = PEPlayerInventory.getSlotNumber(packetArmor);
						if (slotFrom != -1) {
							ItemStack armorItemStack = inventory.getItem(slotFrom);
							//TODO: check if it is an armor itemstack and it can fit in this slot
							if (armorItemStack != null) {
								inventory.setItem(slotFrom, realArmor);
								realArmorArr[i] = armorItemStack;
							}
						}
					}
				}
				listener.player.updateInventory(listener.player.defaultContainer);
			}
		});
	}

	//compare only using material
	//swapping between the armor with same material doesn't send the packet so no chance to know about it anyway :(
	protected static boolean matches(ItemStack itemstack1, ItemStack itemstack2) {
		boolean null1 = itemstack1 == null;
		boolean null2 = itemstack2 == null;
		if (null1 && null2) {
			return true;
		}
		if (null1 | null2) {
			return false;
		}
		return itemstack1.getItem() == itemstack2.getItem();
	}

}
