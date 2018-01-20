package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import org.bukkit.Bukkit;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleLogin;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.login.v_pe.LoginSuccess;
import protocolsupport.protocol.serializer.MiscSerializer;
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
		VarNumberSerializer.writeSVarLong(startgame, playerEntityId);
		VarNumberSerializer.writeVarLong(startgame, playerEntityId);
		VarNumberSerializer.writeSVarInt(startgame, gamemode.getId());
		MiscSerializer.writeLFloat(startgame, 0); //x
		MiscSerializer.writeLFloat(startgame, 0); //y
		MiscSerializer.writeLFloat(startgame, 0); //z
		MiscSerializer.writeLFloat(startgame, 0); //yaw
		MiscSerializer.writeLFloat(startgame, 0); //pitch
		VarNumberSerializer.writeSVarInt(startgame, 0); //seed
		VarNumberSerializer.writeSVarInt(startgame, Respawn.getPeDimensionId(dimension));
		VarNumberSerializer.writeSVarInt(startgame, 1); //world type (1 - infinite)
		VarNumberSerializer.writeSVarInt(startgame, GameMode.SURVIVAL.getId()); // world gamemode
		VarNumberSerializer.writeSVarInt(startgame, difficulty.getId());
		PositionSerializer.writePEPosition(startgame, new Position(0, 0, 0));
		startgame.writeBoolean(false); //disable achievements
		VarNumberSerializer.writeSVarInt(startgame, 0); //time
		startgame.writeBoolean(false); //edu mode
		MiscSerializer.writeLFloat(startgame, 0); //rain level
		MiscSerializer.writeLFloat(startgame, 0); //lighting level
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
		StringSerializer.writeString(startgame, connection.getVersion(), ""); //level ID (empty string)
		StringSerializer.writeString(startgame, connection.getVersion(), ""); //world name (empty string)
		StringSerializer.writeString(startgame, connection.getVersion(), ""); //premium world template id (empty string)
		startgame.writeBoolean(false); //unknown bool
		startgame.writeLongLE(0); //world ticks
		VarNumberSerializer.writeSVarInt(startgame, 0); //enchantment seed
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
		for (int x = -2; x <= 2; x++) {
			for (int z = -2; z <= 2; z++) {
				packets.add(Chunk.createEmptyChunk(version, x, z));
			}
		}
		//teleport to empty world, this will also unlock packet sending that was stalled by movement confirmation
		packets.add(protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.Position.create(
			version, playerEntityId, 0, 20, 0, 0, 0, protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.Position.ANIMATION_MODE_TELEPORT
		));
		//add two dimension switches to make sure that player ends up in right dimension even if bungee dimension switch on server switch broke stuff
		Respawn.create(version, dimension != Environment.OVERWORLD ? Environment.OVERWORLD: Environment.NETHER, playerEntityId, packets);
		Respawn.create(version, dimension, playerEntityId, packets);
		return packets;
	}

}
