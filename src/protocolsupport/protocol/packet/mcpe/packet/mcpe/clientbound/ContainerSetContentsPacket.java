package protocolsupport.protocol.packet.mcpe.packet.mcpe.clientbound;

import net.minecraft.server.v1_9_R2.ItemStack;
import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.PEPacketIDs;
import protocolsupport.protocol.serializer.PacketDataSerializer;

public class ContainerSetContentsPacket implements ClientboundPEPacket {

	public static final int[] EMPTY_HOTBAR_SLOTS = new int[0];

	protected int windowId;
	protected ItemStack[] contents;
	protected int[] hotbar;

	public ContainerSetContentsPacket(int windowId, ItemStack[] contents, int[] hotbar) {
		this.windowId = windowId;
		this.contents = contents;
		this.hotbar = hotbar;
	}

	@Override
	public int getId() {
		return PEPacketIDs.CONTAINER_SET_CONTENT_PACKET;
	}

	@Override
	public ClientboundPEPacket encode(ByteBuf buf) throws Exception {
		PacketDataSerializer serializer = new PacketDataSerializer(buf, ProtocolVersion.MINECRAFT_PE);
		serializer.writeByte(windowId);
		serializer.writeShort(contents.length);
		for (ItemStack itemstack : contents) {
			serializer.writeItemStack(itemstack);
		}
		buf.writeShort(hotbar.length);
		for (int slot : hotbar) {
			buf.writeInt(slot);
		}
		return this;
	}

}
