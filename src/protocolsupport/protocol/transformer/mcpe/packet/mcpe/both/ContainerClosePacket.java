package protocolsupport.protocol.transformer.mcpe.packet.mcpe.both;

import java.util.Collections;
import java.util.List;

import io.netty.buffer.ByteBuf;
import net.minecraft.server.v1_9_R2.Packet;
import net.minecraft.server.v1_9_R2.PlayerConnection;
import protocolsupport.protocol.storage.SharedStorage;
import protocolsupport.protocol.transformer.mcpe.packet.SynchronizedHandleNMSPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.DualPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ServerboundPEPacket;

public class ContainerClosePacket implements DualPEPacket {

	protected int windowId;

	public ContainerClosePacket() {
	}

	public ContainerClosePacket(int windowId) {
		this.windowId = windowId;
	}

	@Override
	public int getId() {
		return PEPacketIDs.CONTAINER_CLOSE_PACKET;
	}

	@Override
	public ServerboundPEPacket decode(ByteBuf buf) throws Exception {
		windowId = buf.readByte();
		return this;
	}

	@Override
	public ClientboundPEPacket encode(ByteBuf buf) throws Exception {
		buf.writeByte(windowId);
		return this;
	}

	@Override
	public List<? extends Packet<?>> transfrom(SharedStorage storage) throws Exception {
		//have to use this instead of just returning InventoryClose packet because i need InventoryClose packet to be sent back to player
		return Collections.singletonList(new SynchronizedHandleNMSPacket<PlayerConnection>() {
			@Override
			public void handle0(PlayerConnection listener) {
				listener.player.closeInventory();
			}
		});
	}

}
