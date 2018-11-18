package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import io.netty.buffer.Unpooled;
import org.bukkit.Bukkit;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleStartGame;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.login.v_pe.LoginSuccess;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.AttributesCache;
import protocolsupport.protocol.typeremapper.pe.PEAdventureSettings;
import protocolsupport.protocol.typeremapper.pe.PEBlocks;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.typeremapper.pe.inventory.PEInventory.PESource;
import protocolsupport.protocol.utils.networkentity.NetworkEntity;
import protocolsupport.protocol.utils.types.GameMode;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.zplatform.impl.pe.PECraftingManager;
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
		resourcepack.writeShortLE(0); //res packs count
		packets.add(resourcepack);
		//Send fake resource stack for sounds.
		ClientBoundPacketData resourcestack = ClientBoundPacketData.create(PEPacketIDs.RESOURCE_STACK);
		resourcestack.writeBoolean(false); // required
		VarNumberSerializer.writeVarInt(resourcestack, 0); //beh packs count
		VarNumberSerializer.writeVarInt(resourcestack, 0); //res packs count
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
		VarNumberSerializer.writeSVarInt(startgame, difficulty.getId()); //world difficulty
		PositionSerializer.writePEPosition(startgame, new Position(0, 0, 0)); //spawn position
		startgame.writeBoolean(false); //disable achievements
		VarNumberSerializer.writeSVarInt(startgame, 0); //time
		startgame.writeBoolean(false); //edu mode
		startgame.writeBoolean(false); //edu features
		startgame.writeFloatLE(0); //rain level
		startgame.writeFloatLE(0); //lighting level
		startgame.writeBoolean(true); //is multiplayer
		startgame.writeBoolean(false); //broadcast to lan
		startgame.writeBoolean(false); //broadcast to xbl
		startgame.writeBoolean(true); //commands enabled
		startgame.writeBoolean(false); //needs texture pack
		VarNumberSerializer.writeVarInt(startgame, 0); //game rules
		//StringSerializer.writeString(startgame, version, "dodaylightcycle");
		//VarNumberSerializer.writeVarInt(startgame, 1); //bool gamerule
		startgame.writeBoolean(false); //bonus chest
		startgame.writeBoolean(false); //player map enabled
		startgame.writeBoolean(false); //trust players
		VarNumberSerializer.writeSVarInt(startgame, PEAdventureSettings.GROUP_NORMAL); //permission level
		VarNumberSerializer.writeSVarInt(startgame, 4); //game publish setting
		startgame.writeIntLE((int) Math.ceil((Bukkit.getViewDistance() + 1) * Math.sqrt(2))); //Server chunk tick radius..
		startgame.writeBoolean(false); //Platformbroadcast
		VarNumberSerializer.writeVarInt(startgame, 0); //Broadcast mode
		startgame.writeBoolean(false); //Broadcast intent
		startgame.writeBoolean(false); //hasLockedRes pack
		startgame.writeBoolean(false); //hasLockedBeh pack
		startgame.writeBoolean(false); //hasLocked world template.
		startgame.writeBoolean(false); //Microsoft GamerTags only. Hell no!
		StringSerializer.writeString(startgame, connection.getVersion(), ""); //level ID (empty string)
		StringSerializer.writeString(startgame, connection.getVersion(), ""); //world name (empty string)
		StringSerializer.writeString(startgame, connection.getVersion(), ""); //premium world template id (empty string)
		startgame.writeBoolean(false); //is trial
		startgame.writeLongLE(0); //world ticks
		VarNumberSerializer.writeSVarInt(startgame, 0); //enchantment seed FFS MOJANG
		startgame.writeBytes(PEBlocks.getPocketRuntimeDefinition());
		StringSerializer.writeString(startgame, version, ""); //Multiplayer correlation id.
		packets.add(startgame);
		//Player metadata and settings update, so it won't behave strangely until metadata update is sent by server
		packets.add(PEAdventureSettings.createPacket(cache));
		packets.add(EntityMetadata.createFaux(player, cache.getAttributesCache().getLocale(), version));
		//Can now switch to game state
		packets.add(LoginSuccess.createPlayStatus(LoginSuccess.PLAYER_SPAWN));
		//Send chunk radius update without waiting for request, works anyway
		//PE uses circle to calculate visible chunks, so the view distance should cover all chunks that are sent by server (pc square should fit into pe circle)
		ClientBoundPacketData chunkradius = ClientBoundPacketData.create(PEPacketIDs.CHUNK_RADIUS);
		VarNumberSerializer.writeSVarInt(chunkradius, (int) Math.ceil((Bukkit.getViewDistance() + 1) * Math.sqrt(2)));
		packets.add(chunkradius);
		//Send crafting recipes
		//TODO: bungeecord should also request this from servers again
		ClientBoundPacketData craftPacket = ClientBoundPacketData.create(PEPacketIDs.CRAFTING_DATA);
		craftPacket.writeBytes(PECraftingManager.getInstance().getAllRecipes());
		packets.add(craftPacket);
		//Send all creative items (from PE json)
		PECreativeInventory peInv = PECreativeInventory.getInstance(); 
		ClientBoundPacketData creativeInventoryPacket = ClientBoundPacketData.create(PEPacketIDs.INVENTORY_CONTENT);
		VarNumberSerializer.writeVarInt(creativeInventoryPacket, PESource.POCKET_CREATIVE_INVENTORY);
		VarNumberSerializer.writeVarInt(creativeInventoryPacket, peInv.getItemCount());
		creativeInventoryPacket.writeBytes(peInv.getCreativeItems());
		packets.add(creativeInventoryPacket);
		//Set PE gamemode.
		AttributesCache attrscache = cache.getAttributesCache();
		attrscache.setPEGameMode(gamemode);
		CustomPayload.create(version, "ps:clientlock", Unpooled.EMPTY_BUFFER, packets); // lock client bound packet queue until LocalPlayerInitialised or bungee confirm
		return packets;
	}

}
