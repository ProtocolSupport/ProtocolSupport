package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import org.bukkit.Bukkit;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleLogin;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.login.v_pe.LoginSuccess;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEAdventureSettings;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.utils.types.Environment;
import protocolsupport.protocol.utils.types.GameMode;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class Login extends MiddleLogin {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		ClientBoundPacketData resourcepack = ClientBoundPacketData.create(PEPacketIDs.RESOURCE_PACK, version);
		resourcepack.writeBoolean(false); // required
		resourcepack.writeShortLE(0); //beh packs count
		resourcepack.writeShortLE(0); //res packs count
		packets.add(resourcepack);
		ClientBoundPacketData resourcestack = ClientBoundPacketData.create(PEPacketIDs.RESOURCE_STACK, version);
		resourcestack.writeBoolean(false); // required
		VarNumberSerializer.writeVarInt(resourcestack, 0); //beh packs count
		VarNumberSerializer.writeVarInt(resourcestack, 0); //res packs count
		packets.add(resourcestack);
		ClientBoundPacketData startgame = ClientBoundPacketData.create(PEPacketIDs.START_GAME, version);
		VarNumberSerializer.writeSVarLong(startgame, playerEntityId); //player eid
		VarNumberSerializer.writeVarLong(startgame, playerEntityId); //player eid
		VarNumberSerializer.writeSVarInt(startgame, gamemode.getId()); //player gamemode
		startgame.writeFloatLE(0); //player x
		startgame.writeFloatLE(0); //player y
		startgame.writeFloatLE(0); //player z
		startgame.writeFloatLE(0); //player yaw
		startgame.writeFloatLE(0); //player pitch
		VarNumberSerializer.writeSVarInt(startgame, 0); //seed
		VarNumberSerializer.writeSVarInt(startgame, Respawn.getPeDimensionId(dimension)); //world dimension
		VarNumberSerializer.writeSVarInt(startgame, 1); //world type (1 - infinite)
		VarNumberSerializer.writeSVarInt(startgame, GameMode.SURVIVAL.getId()); //world gamemode
		VarNumberSerializer.writeSVarInt(startgame, difficulty.getId()); //world difficulty
		PositionSerializer.writePEPosition(startgame, new Position(0, 0, 0)); //spawn position
		startgame.writeBoolean(false); //disable achievements
		VarNumberSerializer.writeSVarInt(startgame, 0); //time
		startgame.writeBoolean(false); //edu mode
		startgame.writeFloatLE(0); //rain level
		startgame.writeFloatLE(0); //lighting level
		startgame.writeBoolean(true); //is multiplayer
		startgame.writeBoolean(false); //broadcast to lan
		startgame.writeBoolean(false); //broadcast to xbl
		startgame.writeBoolean(true); //commands enabled
		startgame.writeBoolean(false); //needs texture pack
		VarNumberSerializer.writeVarInt(startgame, 0); //game rules
		startgame.writeBoolean(false); //bonus chest enabled
		startgame.writeBoolean(false); //player map enabled
		startgame.writeBoolean(false); //trust players
		VarNumberSerializer.writeSVarInt(startgame, PEAdventureSettings.GROUP_NORMAL); //permission level
		VarNumberSerializer.writeSVarInt(startgame, 4); //game publish setting
		startgame.writeIntLE((int) Math.ceil((Bukkit.getViewDistance() + 1) * Math.sqrt(2))); //Server chunk tick radius..
		StringSerializer.writeString(startgame, connection.getVersion(), ""); //level ID (empty string)
		StringSerializer.writeString(startgame, connection.getVersion(), ""); //world name (empty string)
		StringSerializer.writeString(startgame, connection.getVersion(), ""); //premium world template id (empty string)
		startgame.writeBoolean(false); //is trial
		startgame.writeLongLE(0); //world ticks
		VarNumberSerializer.writeSVarInt(startgame, 0); //enchantment seed FFS MOJANG
		packets.add(startgame);
		//player metadata and settings update, so it won't behave strangely until metadata update is sent by server
		packets.add(PEAdventureSettings.createPacket(cache));
		packets.add(EntityMetadata.createFaux(cache.getWatchedSelf(), cache.getLocale(), version));
		//can now switch to game state
		packets.add(LoginSuccess.createPlayStatus(version, 3));
		//send chunk radius update without waiting for request, works anyway
		//PE uses circle to calculate visible chunks, so the view distance should cover all chunks that are sent by server (pc square should fit into pe circle)
		ClientBoundPacketData chunkradius = ClientBoundPacketData.create(PEPacketIDs.CHUNK_RADIUS, version);
		VarNumberSerializer.writeSVarInt(chunkradius, (int) Math.ceil((Bukkit.getViewDistance() + 1) * Math.sqrt(2)));
		packets.add(chunkradius);
		//fake chunks with position, because pe doesn't like spawning in no chunk world
		Respawn.addFakeChunksAndPos(version, playerEntityId, cache.getFakeSetPositionY(), packets);
		//add two dimension switches to make sure that player ends up in right dimension even if bungee dimension switch on server switch broke stuff
		Respawn.create(version, dimension != Environment.OVERWORLD ? Environment.OVERWORLD: Environment.THE_END, playerEntityId, cache.getFakeSetPositionY(), packets);
		Respawn.create(version, dimension, playerEntityId, cache.getFakeSetPositionY(), packets);
		return packets;
	}

}
