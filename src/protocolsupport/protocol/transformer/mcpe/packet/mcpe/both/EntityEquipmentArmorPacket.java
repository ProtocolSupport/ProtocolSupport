package protocolsupport.protocol.transformer.mcpe.packet.mcpe.both;

import java.util.Collections;
import java.util.List;

import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Packet;
import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.DualPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ServerboundPEPacket;

public class EntityEquipmentArmorPacket implements DualPEPacket {

	protected long entityId;
	protected ItemStack[] armor = new ItemStack[4];

	public EntityEquipmentArmorPacket() {
	}

	//TODO: real equipment
	public EntityEquipmentArmorPacket(int entityId) {
		this.entityId = entityId;
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

	//Player operates own armor using this packet, TODO: actually implement invetnory handling
	@Override
	public List<? extends Packet<?>> transfrom() throws Exception {
		return Collections.emptyList();
	}

}
