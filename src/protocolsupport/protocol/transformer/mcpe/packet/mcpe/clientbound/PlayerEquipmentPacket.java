package protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound;

import java.util.Collections;
import java.util.List;

import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Packet;
import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ServerboundPEPacket;

public class PlayerEquipmentPacket implements ServerboundPEPacket {

	protected long entityId;
	protected ItemStack[] equipment = new ItemStack[4];

	@Override
	public int getId() {
		return PEPacketIDs.MOB_ARMOR_EQUIPMENT_PACKET;
	}

	@Override
	public ServerboundPEPacket decode(ByteBuf buf) throws Exception {
		PacketDataSerializer serializer = new PacketDataSerializer(buf, ProtocolVersion.MINECRAFT_PE);
		entityId = serializer.readLong();
		for (int i = 0; i < equipment.length; i++) {
			equipment[i] = serializer.readItemStack();
		}
		return this;
	}

	@Override
	public List<? extends Packet<?>> transfrom() throws Exception {
		return Collections.emptyList();
	}

}
