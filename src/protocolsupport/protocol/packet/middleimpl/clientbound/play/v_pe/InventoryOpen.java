package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import protocolsupport.api.Connection;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventoryOpen;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.protocol.typeremapper.pe.PEInventory;
import protocolsupport.protocol.typeremapper.pe.PEInventory.TradeVillager;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.utils.minecraftdata.PocketData;
import protocolsupport.protocol.utils.minecraftdata.PocketData.PocketEntityData;
import protocolsupport.protocol.utils.types.NetworkEntity;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.protocol.utils.types.WindowType;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

public class InventoryOpen extends MiddleInventoryOpen {
	
	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		ProtocolVersion version = connection.getVersion();
		cache.getPEInventoryCache().getTransactionRemapper().clear();
		//Horses
		if (type == WindowType.HORSE) {
			//TODO: Fix this shit. Horses are a pain in the ass and require a different packer. Lama's are even worse with their variable slots. We'll see.
			NetworkEntity horse = cache.getWatchedEntityCache().getWatchedEntity(horseId);
			if (horse != null) {
				PocketEntityData horseTypeData = PocketData.getPocketEntityData(horse.getType());
				if (horseTypeData != null && horseTypeData.getInventoryFilter() != null) {
					NBTTagCompoundWrapper filter = horseTypeData.getInventoryFilter().getFilter();
					packets.add(openEquipment(connection.getVersion(), windowId, type, horseId, filter));
					return packets;
				}
			}
			return packets;
		} else if (type == WindowType.VILLAGER) {
			System.out.println("VILLAGER: " + horseId + " slots: " + slots + " title " + title.toLegacyText());
			TradeVillager villager = cache.getPEInventoryCache().getTradeVillager();
			villager.setTitle(title);
			packets.add(villager.spawnVillager(cache, version));
		} else {
			//Normal inventory.
			Position open = PEInventory.prepareFakeInventory(title, connection, cache, packets);
			//Unless we have a doublechest or beacon which take some time to create, open the inventory straight away.
			if (!PEInventory.doDoubleChest(cache) && type != WindowType.BEACON) {
				packets.add(create(version, windowId, type, open, -1));
			}
		}
		return packets;
	}
	
	public static ClientBoundPacketData create(ProtocolVersion version, int windowId, WindowType type, Position pePosition, int horseId) {
		System.out.println("Opening " + type + " inventory: " + windowId);
		return (ClientBoundPacketData) serialize(ClientBoundPacketData.create(PEPacketIDs.CONTAINER_OPEN, version), version, windowId, type, pePosition, horseId);
	}
	
	public static ClientBoundPacketData openEquipment(ProtocolVersion version, int windowId, WindowType type, long entityId, NBTTagCompoundWrapper nbt) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.EQUIPMENT, version);
		serializer.writeByte(windowId);
		serializer.writeByte(IdRemapper.WINDOWTYPE.getTable(version).getRemap(type.toLegacyId()));
		VarNumberSerializer.writeSVarInt(serializer, 0); //? :F
		VarNumberSerializer.writeSVarLong(serializer, entityId);
		System.out.println("OPEN EQ - Eid: "+ entityId + "wId: " + windowId + " type: " + IdRemapper.WINDOWTYPE.getTable(version).getRemap(type.toLegacyId()) +  " Tag: " + nbt);
		ItemStackSerializer.writeTag(serializer, true, version, nbt);
		return serializer;
	}
	
	public static void sendInventoryOpen(Connection connection, int windowId, WindowType type, Position pePosition, int horseId) {
		System.out.println("Opening " + type + " inventory: " + windowId);
		ByteBuf serializer = Unpooled.buffer();
		VarNumberSerializer.writeVarInt(serializer, PEPacketIDs.CONTAINER_OPEN);
		serializer.writeByte(0);
		serializer.writeByte(0);
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