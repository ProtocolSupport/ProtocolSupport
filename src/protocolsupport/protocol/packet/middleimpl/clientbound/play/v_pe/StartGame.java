package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import org.bukkit.Bukkit;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleStartGame;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEAdventureSettings;
import protocolsupport.protocol.typeremapper.pe.PEBlocks;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.typeremapper.pe.inventory.PEInventory.PESource;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.GameMode;
import protocolsupport.protocol.types.Position;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.zplatform.impl.pe.PECreativeInventory;

public class StartGame extends MiddleStartGame {

	public StartGame(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		NetworkEntity player = cache.getWatchedEntityCache().getSelfPlayer();
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();

		//Send fake resource packet for sounds.
		ClientBoundPacketData resourcepack = ClientBoundPacketData.create(PEPacketIDs.RESOURCE_PACK);
		resourcepack.writeBoolean(false); // required
		resourcepack.writeShortLE(0); //beh packs count
		if (version.isAfterOrEq(ProtocolVersion.MINECRAFT_PE_1_9)) {
			resourcepack.writeBoolean(false); // ???
		}
		resourcepack.writeShortLE(0); //res packs count
		if (version.isAfterOrEq(ProtocolVersion.MINECRAFT_PE_1_9)) {
			resourcepack.writeBoolean(false); // ???
		}
		packets.add(resourcepack);

		//Send fake resource stack for sounds.
		ClientBoundPacketData resourcestack = ClientBoundPacketData.create(PEPacketIDs.RESOURCE_STACK);
		resourcestack.writeBoolean(false); // required
		VarNumberSerializer.writeVarInt(resourcestack, 0); //beh packs count
		VarNumberSerializer.writeVarInt(resourcestack, 0); //res packs count
		VarNumberSerializer.writeVarInt(resourcestack, 0); //?
		VarNumberSerializer.writeVarInt(resourcestack, 0); //?
		packets.add(resourcestack);

		//Send actual start game information.
		ClientBoundPacketData startgame = ClientBoundPacketData.create(PEPacketIDs.START_GAME);
		VarNumberSerializer.writeSVarLong(startgame, playerEntityId); //player eid
		VarNumberSerializer.writeVarLong(startgame, playerEntityId); //player eid
		VarNumberSerializer.writeSVarInt(startgame, gamemode.getId()); //player gamemode
		startgame.writeFloatLE(0); //player x
		startgame.writeFloatLE(0); //player y
		startgame.writeFloatLE(0); //player z
		startgame.writeFloatLE(0); //player pitch
		startgame.writeFloatLE(0); //player yaw
		VarNumberSerializer.writeSVarInt(startgame, 0); //seed
		VarNumberSerializer.writeSVarInt(startgame, ChangeDimension.getPeDimensionId(dimension)); //world dimension
		VarNumberSerializer.writeSVarInt(startgame, 1); //world type (1 - infinite)
		VarNumberSerializer.writeSVarInt(startgame, GameMode.SURVIVAL.getId()); //world gamemode
		VarNumberSerializer.writeSVarInt(startgame, 1); //world difficulty //TODO: set PE difficulty using... packet?
		PositionSerializer.writePEPosition(startgame, new Position(0, 0, 0)); //spawn position
		startgame.writeBoolean(false); //disable achievements
		VarNumberSerializer.writeSVarInt(startgame, 0); //time
		startgame.writeBoolean(false); //edu mode
		startgame.writeBoolean(false); //edu features
		startgame.writeFloatLE(0); //rain level
		startgame.writeFloatLE(0); //lighting level
		if (version.isAfterOrEq(ProtocolVersion.MINECRAFT_PE_1_9)) {
			startgame.writeBoolean(false); //???
		}
		startgame.writeBoolean(true); //is multiplayer
		startgame.writeBoolean(false); //broadcast to lan
		if (version.isAfterOrEq(ProtocolVersion.MINECRAFT_PE_1_9)) {
			VarNumberSerializer.writeSVarInt(startgame, 3); //xbox live broadcast, 3 = friends of friends
			VarNumberSerializer.writeSVarInt(startgame, 3); //platform broadcast
		} else {
			startgame.writeBoolean(true); //broadcast to xbl
		}
		startgame.writeBoolean(true); //commands enabled
		startgame.writeBoolean(false); //needs texture pack
		VarNumberSerializer.writeVarInt(startgame, 0); //game rules
		//StringSerializer.writeString(startgame, version, "dodaylightcycle");
		//VarNumberSerializer.writeVarInt(startgame, 1); //bool gamerule
		startgame.writeBoolean(false); //bonus chest
		startgame.writeBoolean(false); //player map enabled
		if (version.isBefore(ProtocolVersion.MINECRAFT_PE_1_9)) {
			startgame.writeBoolean(false); //trust players
		}
		VarNumberSerializer.writeSVarInt(startgame, PEAdventureSettings.GROUP_NORMAL); //permission level
		if (version.isBefore(ProtocolVersion.MINECRAFT_PE_1_9)) {
			VarNumberSerializer.writeSVarInt(startgame, 4); //game publish setting
		}
		startgame.writeIntLE((int) Math.ceil((Bukkit.getViewDistance() + 1) * Math.sqrt(2))); //Server chunk tick radius..
		if (version.isBefore(ProtocolVersion.MINECRAFT_PE_1_9)) {
			startgame.writeBoolean(false); //Platformbroadcast
			VarNumberSerializer.writeSVarInt(startgame, 0); //Broadcast mode
			startgame.writeBoolean(false); //Broadcast intent
		}
		startgame.writeBoolean(false); //hasLockedRes pack
		startgame.writeBoolean(false); //hasLockedBeh pack
		startgame.writeBoolean(false); //hasLocked world template.
		startgame.writeBoolean(false); //Microsoft GamerTags only. Hell no!
		startgame.writeBoolean(false); //is from world template
		startgame.writeBoolean(false); //is world template option locked
		if (version.isAfterOrEq(ProtocolVersion.MINECRAFT_PE_1_12)) {
			startgame.writeByte(1); //only spawn v1 villagers
		}
		StringSerializer.writeString(startgame, connection.getVersion(), ""); //level ID (empty string)
		StringSerializer.writeString(startgame, connection.getVersion(), ""); //world name (empty string)
		StringSerializer.writeString(startgame, connection.getVersion(), ""); //premium world template id (empty string)
		startgame.writeBoolean(false); //is trial
		startgame.writeLongLE(0); //world ticks
		VarNumberSerializer.writeSVarInt(startgame, 0); //enchantment seed FFS MOJANG
		startgame.writeBytes(PEBlocks.getPocketRuntimeDefinition(version));
		if (version.isAfterOrEq(ProtocolVersion.MINECRAFT_PE_1_12)) {
			VarNumberSerializer.writeVarInt(startgame, 0); // item list size
		}
		StringSerializer.writeString(startgame, version, ""); //Multiplayer correlation id.
		packets.add(startgame);

		//Player metadata and settings update, so it won't behave strangely until metadata update is sent by server
		packets.add(PEAdventureSettings.createPacket(cache));
		packets.add(EntityMetadata.createFaux(version, cache.getAttributesCache().getLocale(), player));

		//Send chunk radius update without waiting for request, works anyway
		//PE uses circle to calculate visible chunks, so the view distance should cover all chunks that are sent by server (pc square should fit into pe circle)
		ClientBoundPacketData chunkradius = ClientBoundPacketData.create(PEPacketIDs.CHUNK_RADIUS);
		VarNumberSerializer.writeSVarInt(chunkradius, (int) Math.ceil((Bukkit.getViewDistance() + 1) * Math.sqrt(2)));
		packets.add(chunkradius);

		ClientBoundPacketData enableCommandsPacket = ClientBoundPacketData.create(PEPacketIDs.SET_COMMANDS_ENABLED);
		enableCommandsPacket.writeBoolean(true);
		packets.add(enableCommandsPacket);

		PECreativeInventory peInv = PECreativeInventory.getInstance();
		ClientBoundPacketData creativeInventoryPacket = ClientBoundPacketData.create(PEPacketIDs.INVENTORY_CONTENT);
		VarNumberSerializer.writeVarInt(creativeInventoryPacket, PESource.POCKET_CREATIVE_INVENTORY);
		VarNumberSerializer.writeVarInt(creativeInventoryPacket, peInv.getItemCount());
		creativeInventoryPacket.writeBytes(peInv.getCreativeItems());
		packets.add(creativeInventoryPacket);

		return packets;
	}

	@Override
	public boolean postFromServerRead() {
		super.postFromServerRead();
		cache.getAttributesCache().setPEGameMode(gamemode);
		return true;
	}

}
