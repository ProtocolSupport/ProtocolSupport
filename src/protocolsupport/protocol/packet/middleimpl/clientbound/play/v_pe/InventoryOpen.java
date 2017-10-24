package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventoryOpen;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.protocol.utils.types.TileEntityType;
import protocolsupport.protocol.utils.types.WindowType;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

public class InventoryOpen extends MiddleInventoryOpen {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		System.out.println("SERVER OPEN!!!");
		cache.getInfTransactions().clear();
		if (type == WindowType.HORSE) {
			System.out.println("Horsey!");
			return RecyclableSingletonList.create(create(connection.getVersion(), windowId, type, new Position(0, 0, 0), horseId));
		}
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		Position mainpos = cache.prepareFakeInventoryCoords();
		packets.addAll(prepareFakeInventory(connection.getVersion(), cache.getLocale(), mainpos, type, title, cache.getOpenedWindowSlots()));
		packets.add(create(connection.getVersion(), windowId, type, mainpos, -1));
		return packets;
	}
	
	public static RecyclableArrayList<ClientBoundPacketData> prepareFakeInventory(ProtocolVersion version, String locale, Position position, WindowType type, BaseComponent title, int slots) {
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		if(type.getContainerId() != -1) {
			System.out.println("CUSSTTTOOOOMMMM!!" + position);
			packets.add(BlockChangeSingle.create(version, position, type.getContainerId() << 4));
			NBTTagCompoundWrapper tag = ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound();
			tag.setString("CustomName", title.toLegacyText(locale));
			if(type.getTileType() != TileEntityType.UNKNOWN) {
				tag.setString("id", type.getTileType().getRegistryId());
			}
			if(type == WindowType.CHEST && slots > 27) {
				System.out.println("Double Chest. Going for the double if required...");
				Position auxPos = new Position(position.getX() + 1, 0, position.getZ());
				tag.setInt("pairx", auxPos.getX());
				tag.setInt("pairz", auxPos.getZ());
				packets.add(BlockChangeSingle.create(version, auxPos, type.getContainerId() << 4));
				NBTTagCompoundWrapper auxTag = tag;
				auxTag.setString("id", type.getTileType().getRegistryId());
				auxTag.setInt("pairx", position.getX());
				packets.add(BlockTileUpdate.create(version, auxPos, auxTag));
			}
			packets.add(BlockTileUpdate.create(version, position, tag));
		}
		return packets;
	}
	
	public static ClientBoundPacketData create(ProtocolVersion version, int windowId, WindowType type, Position pePosition, int horseId) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.CONTAINER_OPEN, version);
		serializer.writeByte(windowId);
		serializer.writeByte(IdRemapper.WINDOWTYPE.getTable(version).getRemap(type.toLegacyId()));
		PositionSerializer.writePEPosition(serializer, pePosition);
		VarNumberSerializer.writeSVarLong(serializer, horseId);
		return serializer;
	}

}