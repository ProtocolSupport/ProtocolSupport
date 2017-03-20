package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import org.bukkit.Bukkit;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.legacyremapper.pe.PEPacketIDs;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleLogin;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.login.v_pe.LoginSuccess;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class Login extends MiddleLogin {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		ClientBoundPacketData resourcepack = ClientBoundPacketData.create(PEPacketIDs.RESOURCE_PACK, version);
		resourcepack.writeBoolean(false); // required
		resourcepack.writeShort(0); //res packs count
		resourcepack.writeShort(0); //beh packs count
		packets.add(resourcepack);
		ClientBoundPacketData startgame = ClientBoundPacketData.create(PEPacketIDs.START_GAME, version);
		VarNumberSerializer.writeSVarLong(startgame, playerEntityId);
		VarNumberSerializer.writeSVarLong(startgame, playerEntityId);
		MiscSerializer.writeLFloat(startgame, 0); //x
		MiscSerializer.writeLFloat(startgame, 0); //y
		MiscSerializer.writeLFloat(startgame, 0); //z
		MiscSerializer.writeLFloat(startgame, 0); //yaw
		MiscSerializer.writeLFloat(startgame, 0); //pitch
		VarNumberSerializer.writeSVarInt(startgame, 0); //seed
		VarNumberSerializer.writeSVarInt(startgame, Respawn.remapDimensionId(dimension));
		VarNumberSerializer.writeSVarInt(startgame, 1); //world type (1 - infinite)
		VarNumberSerializer.writeSVarInt(startgame, gamemode);
		VarNumberSerializer.writeSVarInt(startgame, difficulty);
		VarNumberSerializer.writeSVarInt(startgame, 0); //spawn x
		VarNumberSerializer.writeVarInt(startgame, 0); //spawn y
		VarNumberSerializer.writeSVarInt(startgame, 0); //spawn z
		startgame.writeBoolean(false); //?
		VarNumberSerializer.writeSVarInt(startgame, -1); //time stop
		startgame.writeBoolean(false); //edu mode
		MiscSerializer.writeLFloat(startgame, 0); //rain level
		MiscSerializer.writeLFloat(startgame, 0); //lighting level
		startgame.writeBoolean(true); //commands enabled
		startgame.writeBoolean(false); //needs texture pack
		StringSerializer.writeString(startgame, version, ""); //level type?
		StringSerializer.writeString(startgame, version, ""); //world name?
		packets.add(startgame);
		packets.add(LoginSuccess.createPlayStatus(version, 3));
		ClientBoundPacketData chunkradius = ClientBoundPacketData.create(PEPacketIDs.CHUNK_RADIUS, version);
		VarNumberSerializer.writeSVarInt(chunkradius, Bukkit.getViewDistance());
		packets.add(chunkradius);
		return packets;
	}

}
