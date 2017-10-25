package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import org.bukkit.block.Block;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleInventoryClose;
import protocolsupport.protocol.utils.minecraftdata.MinecraftData;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.zplatform.ServerPlatform;

public class InventoryClose extends MiddleInventoryClose {

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		windowId = clientdata.readByte();
		cache.getInfTransactions().clear();
		if(connection.hasMetadata("fakeInvBlocks")) {
			destroyFakeContainers((Block[]) connection.getMetadata("fakeInvBlocks"));
		}
	}
	
	@SuppressWarnings("deprecation")
	private void destroyFakeContainers(Block[] blocks) {
		connection.sendPacket(ServerPlatform.get().getPacketFactory().createBlockUpdatePacket(
				Position.fromBukkit(blocks[0].getLocation()), MinecraftData.getBlockStateFromIdAndData(blocks[0].getTypeId(), blocks[0].getData())));
		connection.sendPacket(ServerPlatform.get().getPacketFactory().createBlockUpdatePacket(
				Position.fromBukkit(blocks[1].getLocation()), MinecraftData.getBlockStateFromIdAndData(blocks[1].getTypeId(), blocks[1].getData())));
	}

}
