package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_16r1;

import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleStartGame;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV16r1;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.protocol.typeremapper.legacy.LegacyDimension;
import protocolsupport.protocol.types.nbt.NBTByte;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTInt;
import protocolsupport.protocol.types.nbt.NBTList;
import protocolsupport.protocol.types.nbt.NBTString;

public class StartGame extends MiddleStartGame implements IClientboundMiddlePacketV16r1 {

	public StartGame(IMiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = new ClientCache();

	@Override
	protected void write() {
		ClientBoundPacketData startgame = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_START_GAME);
		startgame.writeInt(player.getId());
		startgame.writeByte(gamemodeCurrent.getId() | (hardcore ? 0x8 : 0));
		startgame.writeByte(gamemodePrevious.getId());
		ArrayCodec.writeVarIntVarIntUTF8StringArray(startgame, worlds);
		ItemStackCodec.writeDirectTag(startgame, toLegacyDimensionRegistry(registries));
		StringCodec.writeVarIntUTF8String(startgame, LegacyDimension.getStringId(clientCache.getDimension()));
		StringCodec.writeVarIntUTF8String(startgame, worldName);
		startgame.writeLong(hashedSeed);
		startgame.writeByte(maxplayers);
		VarNumberCodec.writeVarInt(startgame, renderDistance);
		startgame.writeBoolean(reducedDebugInfo);
		startgame.writeBoolean(respawnScreenEnabled);
		startgame.writeBoolean(worldDebug);
		startgame.writeBoolean(worldFlat);
		io.writeClientbound(startgame);
	}

	protected static NBTCompound toLegacyDimensionRegistry(NBTCompound dimensions) {
		NBTCompound legacyRegistry = new NBTCompound();
		NBTList<NBTCompound> legacyDimensions = NBTList.createCompoundList();
		for (NBTCompound dimensionEntryTag : dimensions.getCompoundTagOrThrow("minecraft:dimension_type").getCompoundListTagOrThrow("value").getTags()) {
			NBTCompound dimensionDataTag = dimensionEntryTag.getCompoundTagOrThrow("element");
			dimensionDataTag.setTag("name", dimensionEntryTag.getTagOrThrow("name"));
			dimensionDataTag.setTag("infiniburn", new NBTString(LegacyDimension.getLegacyResource(dimensionDataTag.getStringTagOrThrow("infiniburn").getValue())));
			dimensionDataTag.setTag("logical_height", new NBTInt(Math.min(256, dimensionDataTag.getNumberTagOrThrow("logical_height").getAsInt())));
			dimensionDataTag.setTag("height", new NBTInt(Math.min(256, dimensionDataTag.getNumberTagOrThrow("height").getAsInt())));
			dimensionDataTag.setTag("shrunk", new NBTByte(dimensionDataTag.getNumberTagOrThrow("coordinate_scale").getAsDouble() != 1.0));
			legacyDimensions.addTag(dimensionDataTag);
		}
		legacyRegistry.setTag("dimension", legacyDimensions);
		return legacyRegistry;
	}

}
