package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventoryClose;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.protocol.utils.types.Position;

public class InventoryClose extends MiddleInventoryClose {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		System.out.println("SERVER CLOSE!!!");
		cache.getInfTransactions().clear();
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.CONTAINER_CLOSE, connection.getVersion());
		serializer.writeByte(windowId);
		packets.add(serializer);
		if(cache.getFakeInventoryCoords() != null) {
			System.out.println("Sending blockupdates...");
			packets.addAll(destroyFakeInventory(connection.getVersion(), cache.getFakeInventoryCoords()));
		}
		return packets;
	}
	
	public static RecyclableArrayList<ClientBoundPacketData> destroyFakeInventory(ProtocolVersion version, Position position) {
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		packets.add(BlockChangeSingle.create(version, position, 0));
		packets.add(BlockChangeSingle.create(version, new Position(position.getX() + 1, 0, position.getZ()), 0));
		return packets;
	}

}
