package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import protocolsupport.api.Connection;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.listeners.InternalPluginMessageRequest;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventoryOpen;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.pipeline.version.v_pe.PEPacketEncoder;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.PEInventoryCache;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.protocol.typeremapper.pe.PEDataValues;
import protocolsupport.protocol.typeremapper.pe.PEDataValues.PEEntityData;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.typeremapper.pe.inventory.fakes.PEFakeContainer;
import protocolsupport.protocol.typeremapper.pe.inventory.fakes.PEFakeVillager;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.protocol.utils.types.WindowType;
import protocolsupport.protocol.utils.types.networkentity.NetworkEntity;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

public class InventoryOpen extends MiddleInventoryOpen {
	
	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		ProtocolVersion version = connection.getVersion();
		PEInventoryCache invCache = cache.getPEInventoryCache();
		//Don't muck with unhandled transactions, just clear it.
		invCache.getTransactionRemapper().clear();
		if (type == WindowType.HORSE) {
			//TODO: Fix lama filter in entitydata.json, add correct metadata for horse inventories.
			NetworkEntity horse = cache.getWatchedEntityCache().getWatchedEntity(horseId);
			if (horse != null) {
				PEEntityData horseTypeData = PEDataValues.getEntityData(horse.getType());
				if (horseTypeData != null && horseTypeData.getInventoryFilter() != null) {
					packets.add(openEquipment(connection.getVersion(), windowId, type, horseId, horseTypeData.getInventoryFilter().getFilter()));
				}
			}
		} else if (type == WindowType.VILLAGER) {
			//Villagers, require fake villager to be spawned and with merchantdata it opens the actual inventory.
			PEFakeVillager villager = cache.getPEInventoryCache().getFakeVillager();
			villager.setTitle(title);
			packets.add(villager.spawnVillager(cache, version));
		} else {
			//Normal inventory, requires fake blocks to open. First check if plugins (Hmmpf) have closed inventory.
			if (invCache.getPreviousWindowId() != 0 && invCache.getPreviousWindowId() != windowId) {
				packets.add(InventoryClose.create(version, invCache.getPreviousWindowId()));
			}
			Position open = PEFakeContainer.prepareContainer(title, connection, cache, packets);
			//The fake blocks take some time, we schedule the opening be requesting a delay from the server.
			InternalPluginMessageRequest.receivePluginMessageRequest(connection, new InternalPluginMessageRequest.InventoryOpenRequest(
					windowId, type, open, -1, 2
			));
		}
		invCache.setPreviousWindowId(windowId);
		return packets;
	}
	
	public static ClientBoundPacketData create(ProtocolVersion version, int windowId, WindowType type, Position pePosition, int horseId) {
		return (ClientBoundPacketData) serialize(ClientBoundPacketData.create(PEPacketIDs.CONTAINER_OPEN), version, windowId, type, pePosition, horseId);
	}
	
	public static ClientBoundPacketData openEquipment(ProtocolVersion version, int windowId, WindowType type, long entityId, NBTTagCompoundWrapper nbt) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.EQUIPMENT);
		serializer.writeByte(windowId);
		serializer.writeByte(PEDataValues.WINDOWTYPE.getTable(version).getRemap(type.toLegacyId()));
		VarNumberSerializer.writeSVarInt(serializer, 0); //UNKOWN :F
		VarNumberSerializer.writeSVarLong(serializer, entityId);
		ItemStackSerializer.writeTag(serializer, true, version, nbt);
		return serializer;
	}
	
	public static void sendInventoryOpen(Connection connection, int windowId, WindowType type, Position pePosition, int horseId) {
		ByteBuf serializer = Unpooled.buffer();
		PEPacketEncoder.sWritePacketId(serializer, PEPacketIDs.CONTAINER_OPEN);
		serialize(serializer, connection.getVersion(), windowId, type, pePosition, horseId);
		connection.sendRawPacket(MiscSerializer.readAllBytes(serializer));
	}
	
	private static ByteBuf serialize(ByteBuf serializer, ProtocolVersion version, int windowId, WindowType type, Position pePosition, int horseId) {
		serializer.writeByte(windowId);
		serializer.writeByte(PEDataValues.WINDOWTYPE.getTable(version).getRemap(
				IdRemapper.INVENTORY.getTable(version).getRemap(type)
			.toLegacyId()));
		PositionSerializer.writePEPosition(serializer, pePosition);
		VarNumberSerializer.writeSVarLong(serializer, horseId);
		return serializer;
	}

}