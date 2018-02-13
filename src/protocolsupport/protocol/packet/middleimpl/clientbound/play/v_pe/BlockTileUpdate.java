package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockTileUpdate;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.typeremapper.tileentity.TileNBTRemapper;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

public class BlockTileUpdate extends MiddleBlockTileUpdate {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		return RecyclableSingletonList.create(create(connection.getVersion(), position, tag));
	}
	
	public static ClientBoundPacketData create(ProtocolVersion version, protocolsupport.protocol.utils.types.Position position, NBTTagCompoundWrapper tag) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.TILE_DATA_UPDATE, version);
		PositionSerializer.writePEPosition(serializer, position);
		ItemStackSerializer.writeTag(serializer, true, version, TileNBTRemapper.remap(version, tag));
		return serializer;
	}

}
