package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_16r2_17;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleStartGame;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTString;

public class StartGame extends MiddleStartGame {

	public StartGame(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData startgame = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_START_GAME);
		startgame.writeInt(player.getId());
		startgame.writeBoolean(hardcore);
		startgame.writeByte(gamemodeCurrent.getId());
		startgame.writeByte(gamemodePrevious.getId());
		ArraySerializer.writeVarIntVarIntUTF8StringArray(startgame, worlds);
		ItemStackSerializer.writeDirectTag(startgame, toLegacyDimensionRegistry(dimensions));
		ItemStackSerializer.writeDirectTag(startgame, dimension);
		StringSerializer.writeVarIntUTF8String(startgame, world);
		startgame.writeLong(hashedSeed);
		VarNumberSerializer.writeVarInt(startgame, maxplayers);
		VarNumberSerializer.writeVarInt(startgame, renderDistance);
		startgame.writeBoolean(reducedDebugInfo);
		startgame.writeBoolean(respawnScreenEnabled);
		startgame.writeBoolean(worldDebug);
		startgame.writeBoolean(worldFlat);
		codec.writeClientbound(startgame);
	}

	protected static NBTCompound toLegacyDimensionRegistry(NBTCompound dimensionsTag) {
		NBTCompound biomeRegistryTag = dimensionsTag.getCompoundTagOrThrow("minecraft:worldgen/biome");
		for (NBTCompound biomeEntryTag : biomeRegistryTag.getCompoundListTagOrThrow("value").getTags()) {
			NBTCompound biomeDataTag = biomeEntryTag.getCompoundTagOrThrow("element");
			biomeDataTag.setTag("category", new NBTString(toLegacyBiomeCategory(biomeDataTag.getStringTagOrThrow("category").getValue())));
		}
		return dimensionsTag;
	}

	protected static String toLegacyBiomeCategory(String category) {
		switch (category) {
			case "underground": {
				return "none";
			}
			default: {
				return category;
			}
		}
	}

}
