package protocolsupport.protocol.transformer.mcpe.packet.mcpe.serverbound;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.List;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.ServerBoundPacket;
import protocolsupport.protocol.storage.SharedStorage;
import protocolsupport.protocol.transformer.mcpe.PEPlayerInventory;
import protocolsupport.protocol.transformer.mcpe.packet.SynchronizedHandleNMSPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ServerboundPEPacket;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketCreator;
import net.minecraft.server.v1_9_R1.BlockPosition;
import net.minecraft.server.v1_9_R1.ItemStack;
import net.minecraft.server.v1_9_R1.Packet;
import net.minecraft.server.v1_9_R1.PlayerConnection;

public class UseItemPacket implements ServerboundPEPacket {

	protected int againstX;
	protected int againstY;
	protected int againstZ;
	protected int face;
	protected float cursorX;
	protected float cursorY;
	protected float cursorZ;
	protected float playerPosX;
	protected float playerPosY;
	protected float playerPosZ;
	protected ItemStack itemstack;

	@Override
	public int getId() {
		return PEPacketIDs.USE_ITEM_PACKET;
	}

	@Override
	public ServerboundPEPacket decode(ByteBuf buf) throws Exception {
		PacketDataSerializer serializer = new PacketDataSerializer(buf, ProtocolVersion.MINECRAFT_PE);
		this.againstX = buf.readInt();
		this.againstY = buf.readInt();
		this.againstZ = buf.readInt();
		this.face = buf.readByte();
		this.cursorX = buf.readFloat();
		this.cursorY = buf.readFloat();
		this.cursorZ = buf.readFloat();
		this.playerPosX = buf.readFloat();
		this.playerPosY = buf.readFloat();
		this.playerPosZ = buf.readFloat();
		this.itemstack = serializer.readItemStack();
		return this;
	}

	@Override
	public List<? extends Packet<?>> transfrom(SharedStorage storage) throws Exception {
		ArrayList<Packet<?>> packets = new ArrayList<Packet<?>>();
		final int slot = PEPlayerInventory.getSlotNumber(itemstack);
		if (slot != -1) {
			packets.add(new SynchronizedHandleNMSPacket<PlayerConnection>() {
				@Override
				public void handle0(PlayerConnection listener) {
					((PEPlayerInventory) listener.player.inventory).setSelectedSlot(slot);
				}
			});
		}
		if (face != -1) {
			PacketCreator creator = PacketCreator.create(ServerBoundPacket.PLAY_USE_ITEM.get());
			creator.writePosition(new BlockPosition(againstX, againstY, againstZ));
			creator.writeVarInt(face);
			creator.writeVarInt(0);
			creator.writeByte((int) (cursorX / 16.0F));
			creator.writeByte((int) (cursorY / 16.0F));
			creator.writeByte((int) (cursorZ / 16.0F));
			packets.add(creator.create());
		} else {
			PacketCreator creator = PacketCreator.create(ServerBoundPacket.PLAY_BLOCK_PLACE.get());
			creator.writeVarInt(0);
			packets.add(creator.create());
		}
		return packets;
	}

}
