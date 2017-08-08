package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;


import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockTileUpdate;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.typeremapper.tileentity.TileNBTRemapper;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class BlockTileUpdate extends MiddleBlockTileUpdate {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		ProtocolVersion version = connection.getVersion();
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.TILE_DATA_UPDATE, version);
		PositionSerializer.writePEPosition(serializer, position);
		ItemStackSerializer.writeTag(serializer, true, version, TileNBTRemapper.remap(version, tag));
		packets.add(serializer);
		return packets;
	}

}
