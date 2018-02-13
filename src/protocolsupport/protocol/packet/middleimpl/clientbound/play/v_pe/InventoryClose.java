package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventoryClose;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.typeremapper.pe.PEInventory.InvBlock;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class InventoryClose extends MiddleInventoryClose {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		System.out.println("SERVER CLOSE " + windowId);
		cache.getInfTransactions().clear();
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		packets.add(create(connection.getVersion(), windowId));
		if(connection.hasMetadata("peInvBlocks")) {
			packets.addAll(destroyFakeInventory(connection.getVersion(), (InvBlock[]) connection.getMetadata("peInvBlocks")));
			connection.removeMetadata("peInvBlocks");
		}
		return packets;
	}
	
	public static ClientBoundPacketData create(ProtocolVersion version, int windowId) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.CONTAINER_CLOSE, version);
		serializer.writeByte(windowId);
		return serializer;
	}
	
	public static RecyclableArrayList<ClientBoundPacketData> destroyFakeInventory(ProtocolVersion version, InvBlock[] blocks) {
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		packets.add(BlockChangeSingle.create(version, blocks[0].getPosition(), blocks[0].getTypeData()));
		packets.add(BlockChangeSingle.create(version, blocks[1].getPosition(), blocks[1].getTypeData()));
		return packets;
	}

}
