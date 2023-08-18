package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_18;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleStartGame;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV18;
import protocolsupport.protocol.typeremapper.legacy.LegacyDimension;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTString;

public class StartGame extends MiddleStartGame implements IClientboundMiddlePacketV18 {

	public StartGame(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		NBTCompound dimension = clientCache.getDimension();
		boolean legacyResource = version.isBeforeOrEq(ProtocolVersion.MINECRAFT_1_18);

		ClientBoundPacketData startgame = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_START_GAME);
		startgame.writeInt(player.getId());
		startgame.writeBoolean(hardcore);
		startgame.writeByte(gamemodeCurrent.getId());
		startgame.writeByte(gamemodePrevious.getId());
		ArrayCodec.writeVarIntVarIntUTF8StringArray(startgame, worlds);
		ItemStackCodec.writeDirectTag(startgame, toLegacyDimensionRegistry(registries, legacyResource));
		ItemStackCodec.writeDirectTag(startgame, toLegacyDimensionType(dimension, legacyResource));
		StringCodec.writeVarIntUTF8String(startgame, worldName);
		startgame.writeLong(hashedSeed);
		VarNumberCodec.writeVarInt(startgame, maxplayers);
		VarNumberCodec.writeVarInt(startgame, renderDistance);
		VarNumberCodec.writeVarInt(startgame, simulationDistance);
		startgame.writeBoolean(reducedDebugInfo);
		startgame.writeBoolean(respawnScreenEnabled);
		startgame.writeBoolean(worldDebug);
		startgame.writeBoolean(worldFlat);
		io.writeClientbound(startgame);
	}

	protected static NBTCompound toLegacyDimensionRegistry(NBTCompound dimensionsTag, boolean legacyResource) {
		NBTCompound dimensionRegistryTag = dimensionsTag.getCompoundTagOrThrow("minecraft:dimension_type");
		for (NBTCompound dimensionEntryTag : dimensionRegistryTag.getCompoundListTagOrThrow("value")) {
			toLegacyDimensionType(dimensionEntryTag.getCompoundTagOrThrow("element"), legacyResource);
		}
		NBTCompound biomeRegistryTag = dimensionsTag.getCompoundTagOrThrow("minecraft:worldgen/biome");
		for (NBTCompound biomeEntryTag : biomeRegistryTag.getCompoundListTagOrThrow("value").getTags()) {
			NBTCompound biomeDataTag = biomeEntryTag.getCompoundTagOrThrow("element");
			biomeDataTag.setTag("category", new NBTString("none"));
			biomeDataTag.setTag("precipitation", new NBTString("none"));
		}
		dimensionsTag.removeTag("minecraft:chat_type");
		dimensionsTag.removeTag("minecraft:trim_material");
		dimensionsTag.removeTag("minecraft:trim_pattern");
		dimensionsTag.removeTag("minecraft:damage_type");
		return dimensionsTag;
	}

	public static NBTCompound toLegacyDimensionType(NBTCompound dimensionDataTag, boolean legacyResource) {
		if (legacyResource) {
			dimensionDataTag.setTag("infiniburn", new NBTString(LegacyDimension.getLegacyResource(dimensionDataTag.getStringTagOrThrow("infiniburn").getValue())));
		}
		return dimensionDataTag;
	}

}
