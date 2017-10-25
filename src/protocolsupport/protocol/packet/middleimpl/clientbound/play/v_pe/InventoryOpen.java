package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import org.bukkit.block.Block;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import protocolsupport.api.Connection;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventoryOpen;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
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
		cache.getInfTransactions().clear();
		if (type == WindowType.HORSE) {
			return RecyclableSingletonList.create(create(connection.getVersion(), windowId, type, new Position(0, 0, 0), horseId));
		}
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		if(connection.hasMetadata("fakeInvBlocks")) {
			Block[] blocks = (Block[]) connection.getMetadata("fakeInvBlocks");
			packets.addAll(prepareFakeInventory(connection.getVersion(), cache.getLocale(), blocks, type, title, cache.getOpenedWindowSlots()));
			if(
				(type == WindowType.CHEST) &&
				(cache.getOpenedWindowSlots() > 27)
			) {
				//When it is a doublechest, re-smuggle the windowId back to the metadata.
				connection.addMetadata("smuggledWindowId", windowId);
			} else {
				//Only double chests need some time to verify on the client (FFS Mojang!), the rest can be instantly opened after preparing.
				packets.add(create(connection.getVersion(), windowId, type, Position.fromBukkit(blocks[0].getLocation()), -1));
			}
		}
		return packets;
	}
	
	public static RecyclableArrayList<ClientBoundPacketData> prepareFakeInventory(ProtocolVersion version, String locale, Block[] blocks, WindowType type, BaseComponent title, int slots) {
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		if(type.getContainerId() != -1) {
			Position mainpos = Position.fromBukkit(blocks[0].getLocation());
			packets.add(BlockChangeSingle.create(version, mainpos, type.getContainerId() << 4));
			NBTTagCompoundWrapper tag = ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound();
			tag.setString("CustomName", title.toLegacyText(locale));
			if(type.getTileType() != TileEntityType.UNKNOWN) {
				tag.setString("id", type.getTileType().getRegistryId());
			}
			if(type == WindowType.CHEST && slots > 27) {
				Position auxPos = Position.fromBukkit(blocks[1].getLocation());
				packets.add(BlockChangeSingle.create(version, auxPos, type.getContainerId() << 4));
				tag.setInt("pairx", auxPos.getX());
				tag.setInt("pairz", auxPos.getZ());
				tag.setByte("pairlead", 1);
				packets.add(BlockTileUpdate.create(version, mainpos, tag));
				NBTTagCompoundWrapper auxTag = ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound();;
				auxTag.setString("CustomName", title.toLegacyText(locale));
				auxTag.setString("id", type.getTileType().getRegistryId());
				auxTag.setInt("pairx", mainpos.getX());
				auxTag.setInt("pairz", mainpos.getZ());
				auxTag.setByte("pairlead", 0);
				packets.add(BlockTileUpdate.create(version, auxPos, auxTag));
			} else {
				packets.add(BlockTileUpdate.create(version, mainpos, tag));
			}
			
		}
		return packets;
	}
	
	public static ClientBoundPacketData create(ProtocolVersion version, int windowId, WindowType type, Position pePosition, int horseId) {
		return (ClientBoundPacketData) serialize(ClientBoundPacketData.create(PEPacketIDs.CONTAINER_OPEN, version), version, windowId, type, pePosition, horseId);
	}
	
	public static void sendInventory(Connection connection, int windowId, WindowType type, Position pePosition, int horseId) {
		ByteBuf serializer = Unpooled.buffer();
		VarNumberSerializer.writeVarInt(serializer, PEPacketIDs.CONTAINER_OPEN);
		serializer.writeByte(0);
		serializer.writeByte(0);
		serialize(serializer, connection.getVersion(), windowId, type, pePosition, horseId);
		connection.sendRawPacket(MiscSerializer.readAllBytes(serializer));
	}
	
	private static ByteBuf serialize(ByteBuf serializer, ProtocolVersion version, int windowId, WindowType type, Position pePosition, int horseId) {
		serializer.writeByte(windowId);
		serializer.writeByte(IdRemapper.WINDOWTYPE.getTable(version).getRemap(type.toLegacyId()));
		PositionSerializer.writePEPosition(serializer, pePosition);
		VarNumberSerializer.writeSVarLong(serializer, horseId);
		return serializer;
	}

}