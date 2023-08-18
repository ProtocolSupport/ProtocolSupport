package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_17r1__17r2;

import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleStartGame;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r2;
import protocolsupport.protocol.typeremapper.legacy.LegacyDimension;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTFloat;
import protocolsupport.protocol.types.nbt.NBTString;

public class StartGame extends MiddleStartGame implements
IClientboundMiddlePacketV17r1,
IClientboundMiddlePacketV17r2 {

	public StartGame(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData startgame = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_START_GAME);
		startgame.writeInt(player.getId());
		startgame.writeBoolean(hardcore);
		startgame.writeByte(gamemodeCurrent.getId());
		startgame.writeByte(gamemodePrevious.getId());
		ArrayCodec.writeVarIntVarIntUTF8StringArray(startgame, worlds);
		ItemStackCodec.writeDirectTag(startgame, toLegacyDimensionRegistry(registries));
		ItemStackCodec.writeDirectTag(startgame, toLegacyDimensionType(clientCache.getDimension()));
		StringCodec.writeVarIntUTF8String(startgame, worldName);
		startgame.writeLong(hashedSeed);
		VarNumberCodec.writeVarInt(startgame, maxplayers);
		VarNumberCodec.writeVarInt(startgame, renderDistance);
		startgame.writeBoolean(reducedDebugInfo);
		startgame.writeBoolean(respawnScreenEnabled);
		startgame.writeBoolean(worldDebug);
		startgame.writeBoolean(worldFlat);
		io.writeClientbound(startgame);
	}

	protected static NBTCompound toLegacyDimensionRegistry(NBTCompound dimensionsTag) {
		NBTCompound dimensionRegistryTag = dimensionsTag.getCompoundTagOrThrow("minecraft:dimension_type");
		for (NBTCompound dimensionEntryTag : dimensionRegistryTag.getCompoundListTagOrThrow("value")) {
			toLegacyDimensionType(dimensionEntryTag.getCompoundTagOrThrow("element"));
		}
		NBTCompound biomeRegistryTag = dimensionsTag.getCompoundTagOrThrow("minecraft:worldgen/biome");
		for (NBTCompound biomeEntryTag : biomeRegistryTag.getCompoundListTagOrThrow("value").getTags()) {
			NBTCompound biomeDataTag = biomeEntryTag.getCompoundTagOrThrow("element");
			biomeDataTag.setTag("depth", new NBTFloat(0F));
			biomeDataTag.setTag("scale", new NBTFloat(0F));
			biomeDataTag.setTag("category", new NBTString("none"));
		}
		dimensionsTag.removeTag("minecraft:chat_type");
		return dimensionsTag;
	}

	public static NBTCompound toLegacyDimensionType(NBTCompound dimensionDataTag) {
		dimensionDataTag.setTag("infiniburn", new NBTString(LegacyDimension.getLegacyResource(dimensionDataTag.getStringTagOrThrow("infiniburn").getValue())));
		return dimensionDataTag;
	}

}
