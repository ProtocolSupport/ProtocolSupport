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
import protocolsupport.protocol.utils.types.GameMode;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class Login extends MiddleLogin {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		ClientBoundPacketData resourcepack = ClientBoundPacketData.create(PEPacketIDs.RESOURCE_PACK, version);
		resourcepack.writeBoolean(false); // required
		resourcepack.writeShortLE(0); //res packs count
		resourcepack.writeShortLE(0); //beh packs count
		packets.add(resourcepack);
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
		startgame.writeBoolean(false); //disable achievments
		VarNumberSerializer.writeSVarInt(startgame, 0); //time
		startgame.writeBoolean(false); //edu mode
		MiscSerializer.writeLFloat(startgame, 0); //rain level
		MiscSerializer.writeLFloat(startgame, 0); //lighting level
		startgame.writeBoolean(false); //commands enabled
		startgame.writeBoolean(false); //needs texture pack
		VarNumberSerializer.writeVarInt(startgame, 0); //game rules
		StringSerializer.writeString(startgame, version, ""); //level type
		StringSerializer.writeString(startgame, version, ""); //world name
		StringSerializer.writeString(startgame, version, ""); //premium world template
		startgame.writeBoolean(false); //apply level ticks
		startgame.writeLong(0); //level ticks
		packets.add(startgame);
		packets.add(PEAdventureSettings.createPacket(cache));
		ClientBoundPacketData chunkradius = ClientBoundPacketData.create(PEPacketIDs.CHUNK_RADIUS, version);
		VarNumberSerializer.writeSVarInt(chunkradius, Bukkit.getViewDistance() + 1); //should exactly match the view distance that server uses to broadcast chunks. +1 because mcpe includes the chunk client is standing in in calculations, while pc does not
		packets.add(chunkradius);
		packets.add(LoginSuccess.createPlayStatus(version, 3));
		return packets;
	}

}
