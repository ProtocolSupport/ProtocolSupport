package protocolsupport.zmcpe.packetsimpl.clientbound;

import org.bukkit.Bukkit;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleLogin;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.zmcpe.packetsimpl.PEPacketIDs;

public class Login extends MiddleLogin<RecyclableCollection<ClientBoundPacketData>> {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		ClientBoundPacketData resourcepack = ClientBoundPacketData.create(PEPacketIDs.RESOURCE_PACK, version);
		resourcepack.writeBoolean(false); // required
		resourcepack.writeShort(0); //res packs count
		resourcepack.writeShort(0); //beh packs count
		packets.add(resourcepack);
		ClientBoundPacketData startgame = ClientBoundPacketData.create(PEPacketIDs.START_GAME, version);
		startgame.writeSVarLong(0); //player entity unique id
		startgame.writeSVarLong(0); //player entity runtime id
		startgame.writeLFloat(0); //x
		startgame.writeLFloat(0); //y
		startgame.writeLFloat(0); //z
		startgame.writeLFloat(0); //yaw
		startgame.writeLFloat(0); //pitch
		startgame.writeSVarInt(0); //seed
		startgame.writeSVarInt(dimension);
		startgame.writeSVarInt(1); //world type (1 - infinite)
		startgame.writeSVarInt(gamemode);
		startgame.writeSVarInt(difficulty);
		startgame.writeSVarInt(0); //spawn x
		startgame.writeVarInt(0); //spawn y
		startgame.writeSVarInt(0); //spawn z
		startgame.writeBoolean(false); //?
		startgame.writeSVarInt(-1); //time stop
		startgame.writeBoolean(false); //edu mode
		startgame.writeLFloat(0); //rain level
		startgame.writeLFloat(0); //lighting level
		startgame.writeBoolean(true); //commands enabled
		startgame.writeBoolean(false); //needs texture pack
		startgame.writeString("");
		startgame.writeString("");
		packets.add(startgame);
		ClientBoundPacketData playstatus = ClientBoundPacketData.create(PEPacketIDs.PLAY_STATUS, version);
		playstatus.writeInt(3);
		packets.add(playstatus);
		ClientBoundPacketData chunkradius = ClientBoundPacketData.create(PEPacketIDs.CHUNK_RADIUS, version);
		chunkradius.writeSVarInt(Bukkit.getViewDistance());
		packets.add(chunkradius);
		return packets;
	}

}
