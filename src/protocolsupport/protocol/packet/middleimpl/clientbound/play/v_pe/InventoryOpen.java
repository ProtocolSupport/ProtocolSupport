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
import protocolsupport.protocol.storage.netcache.WindowCache;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.typeremapper.pe.inventory.fakes.FakeContainer;
import protocolsupport.protocol.typeremapper.pe.inventory.fakes.FakeVillager;
import protocolsupport.protocol.utils.minecraftdata.PocketData;
import protocolsupport.protocol.utils.minecraftdata.PocketData.PocketEntityData;
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
		WindowCache winCache = cache.getWindowCache();
		cache.getPEInventoryCache().getTransactionRemapper().clear();
		if (type == WindowType.HORSE) {
			//Horses, requires filter & some metadata to open.
			//TODO: Fix this shit. Horses are a pain in the ass and require a different packet with nbt.
			//Lama's are even worse with their variable slots. Some of this even has to be send in FREAKING metadata.
			//For now it seems most of the horses work but the filter's might still need adjusting, this code is correct however.
			NetworkEntity horse = cache.getWatchedEntityCache().getWatchedEntity(horseId);
			if (horse != null) {
				PocketEntityData horseTypeData = PocketData.getPocketEntityData(horse.getType());
				if (horseTypeData != null && horseTypeData.getInventoryFilter() != null) {
					packets.add(openEquipment(connection.getVersion(), windowId, type, horseId, horseTypeData.getInventoryFilter().getFilter()));
				}
			}
		} else if (type == WindowType.VILLAGER) {
			//Villagers, require fake villager to be spawned and with merchantdata it opens the actual inventory.
			System.out.println("VILLAGER: " + horseId + " slots: " + slots + " title " + title.toLegacyText());
			FakeVillager villager = cache.getPEInventoryCache().getFakeVillager();
			villager.setTitle(title);
			packets.add(villager.spawnVillager(cache, version));
		} else {
			//Normal inventory, requires fake blocks to open.
			Position open = FakeContainer.prepareContainer(title, connection, cache, packets);
			if (!FakeContainer.shouldDoDoubleChest(cache) && type != WindowType.BEACON) {
				//Unless we have a doublechest or beacon which take some time to create, open the inventory straight away.
				packets.add(create(version, windowId, type, open, -1));
			} else {
				//Schedule the double chest or beacon open on the server. The client needs time to settle in after preparing the large inventories.
				InternalPluginMessageRequest.receivePluginMessageRequest(connection, new InternalPluginMessageRequest.InventoryOpenRequest(
						winCache.getOpenedWindowId(), winCache.getOpenedWindow(), open, -1, 2
				));
			}
		}
		return packets;
	}
	
	public static ClientBoundPacketData create(ProtocolVersion version, int windowId, WindowType type, Position pePosition, int horseId) {
		System.out.println("Opening " + type + " inventory: " + windowId);
		return (ClientBoundPacketData) serialize(ClientBoundPacketData.create(PEPacketIDs.CONTAINER_OPEN), version, windowId, type, pePosition, horseId);
	}
	
	public static ClientBoundPacketData openEquipment(ProtocolVersion version, int windowId, WindowType type, long entityId, NBTTagCompoundWrapper nbt) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.EQUIPMENT);
		serializer.writeByte(windowId);
		serializer.writeByte(IdRemapper.WINDOWTYPE.getTable(version).getRemap(type.toLegacyId()));
		VarNumberSerializer.writeSVarInt(serializer, 0); //UNKOWN :F
		VarNumberSerializer.writeSVarLong(serializer, entityId);
		System.out.println("OPEN EQ - Eid: "+ entityId + "wId: " + windowId + " type: " + IdRemapper.WINDOWTYPE.getTable(version).getRemap(type.toLegacyId()) +  " Tag: " + nbt);
		ItemStackSerializer.writeTag(serializer, true, version, nbt);
		return serializer;
	}
	
	public static void sendInventoryOpen(Connection connection, int windowId, WindowType type, Position pePosition, int horseId) {
		System.out.println("Opening " + type + " inventory: " + windowId);
		ByteBuf serializer = Unpooled.buffer();
		PEPacketEncoder.sWritePacketId(serializer, PEPacketIDs.CONTAINER_OPEN);
		serialize(serializer, connection.getVersion(), windowId, type, pePosition, horseId);
		connection.sendRawPacket(MiscSerializer.readAllBytes(serializer));
	}
	
	private static ByteBuf serialize(ByteBuf serializer, ProtocolVersion version, int windowId, WindowType type, Position pePosition, int horseId) {
		serializer.writeByte(windowId);
		serializer.writeByte(IdRemapper.WINDOWTYPE.getTable(version).getRemap(
				IdRemapper.INVENTORY.getTable(version).getRemap(type)
			.toLegacyId()));
		PositionSerializer.writePEPosition(serializer, pePosition);
		VarNumberSerializer.writeSVarLong(serializer, horseId);
		return serializer;
	}

}