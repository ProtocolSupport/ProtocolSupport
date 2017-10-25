package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import org.bukkit.block.Block;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventoryClose;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.protocol.utils.minecraftdata.MinecraftData;
import protocolsupport.protocol.utils.types.Position;

public class InventoryClose extends MiddleInventoryClose {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		cache.getInfTransactions().clear();
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.CONTAINER_CLOSE, connection.getVersion());
		serializer.writeByte(windowId);
		packets.add(serializer);
		if(connection.hasMetadata("fakeInvBlocks")) {
			packets.addAll(destroyFakeInventory(connection.getVersion(), (Block[]) connection.getMetadata("fakeInvBlocks")));
			connection.removeMetadata("fakeInvBlock");
		}
		return packets;
	}
	
	@SuppressWarnings("deprecation")
	public static RecyclableArrayList<ClientBoundPacketData> destroyFakeInventory(ProtocolVersion version, Block[] blocks) {
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		packets.add(BlockChangeSingle.create(version, Position.fromBukkit(blocks[0].getLocation()), MinecraftData.getBlockStateFromIdAndData(blocks[0].getTypeId(), blocks[0].getData())));
		packets.add(BlockChangeSingle.create(version, Position.fromBukkit(blocks[1].getLocation()), MinecraftData.getBlockStateFromIdAndData(blocks[1].getTypeId(), blocks[1].getData())));
		return packets;
	}

}
