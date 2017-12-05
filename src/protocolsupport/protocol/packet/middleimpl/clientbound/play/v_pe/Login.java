package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleLogin;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.login.v_pe.LoginSuccess;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEAdventureSettings;
import protocolsupport.protocol.typeremapper.pe.PEInventory.PESource;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.utils.types.GameMode;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.zplatform.impl.pe.PECraftingManager;
import protocolsupport.zplatform.impl.pe.PECreativeInventory;

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
		packets.add(PEAdventureSettings.createPacket(cache));
		packets.add(LoginSuccess.createPlayStatus(version, 3));
		packets.add(EntityMetadata.createFaux(cache.getWatchedSelf(), cache.getLocale(), version)); //Add faux flags right on login. If something important needs to be send also, the server will take care with a metadata update.
		ClientBoundPacketData craftPacket = ClientBoundPacketData.create(PEPacketIDs.CRAFTING_DATA, version);
		craftPacket.writeBytes(PECraftingManager.getInstance().getAllRecipes());
		packets.add(craftPacket);
		PECreativeInventory peInv = PECreativeInventory.getInstance();
		ClientBoundPacketData creativeInventoryPacket = ClientBoundPacketData.create(PEPacketIDs.INVENTORY_CONTENT, version);
		VarNumberSerializer.writeVarInt(creativeInventoryPacket, PESource.POCKET_CREATIVE_INVENTORY);
		VarNumberSerializer.writeVarInt(creativeInventoryPacket, peInv.getItemCount());
		creativeInventoryPacket.writeBytes(peInv.getCreativeItems());
		packets.add(creativeInventoryPacket);
		return packets;
	}

}
