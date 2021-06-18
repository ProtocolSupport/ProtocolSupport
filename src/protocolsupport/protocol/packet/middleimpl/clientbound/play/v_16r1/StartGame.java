package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_16r1;

import java.util.Map;

import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleStartGame;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.typeremapper.legacy.LegacyDimension;
import protocolsupport.protocol.types.nbt.NBT;
import protocolsupport.protocol.types.nbt.NBTByte;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTList;

public class StartGame extends MiddleStartGame {

	public StartGame(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData startgame = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_START_GAME);
		startgame.writeInt(player.getId());
		startgame.writeByte(gamemodeCurrent.getId() | (hardcore ? 0x8 : 0));
		startgame.writeByte(gamemodePrevious.getId());
		ArrayCodec.writeVarIntVarIntUTF8StringArray(startgame, worlds);
		ItemStackCodec.writeDirectTag(startgame, toLegacyDimensionRegistry(dimensions));
		StringCodec.writeVarIntUTF8String(startgame, LegacyDimension.getStringId(dimension));
		StringCodec.writeVarIntUTF8String(startgame, world);
		startgame.writeLong(hashedSeed);
		startgame.writeByte(maxplayers);
		VarNumberCodec.writeVarInt(startgame, renderDistance);
		startgame.writeBoolean(reducedDebugInfo);
		startgame.writeBoolean(respawnScreenEnabled);
		startgame.writeBoolean(worldDebug);
		startgame.writeBoolean(worldFlat);
		codec.writeClientbound(startgame);
	}

	protected static NBTCompound toLegacyDimensionRegistry(NBTCompound dimensions) {
		NBTCompound legacyRegistry = new NBTCompound();
		NBTList<NBTCompound> legacyDimensions = NBTList.createCompoundList();
		for (NBTCompound dimension : dimensions.getCompoundTagOrThrow("minecraft:dimension_type").getCompoundListTagOrThrow("value").getTags()) {
			NBTCompound dimensionSettings = dimension.getCompoundTagOrThrow("element");
			NBTCompound legacyDimension = new NBTCompound();
			legacyDimension.setTag("name", dimension.getTagOrNull("name"));
			for (Map.Entry<String, NBT> dimensionSettingsEntry : dimensionSettings.getTags().entrySet()) {
				legacyDimension.setTag(dimensionSettingsEntry.getKey(), dimensionSettingsEntry.getValue());
			}
			legacyDimension.setTag("shrunk", new NBTByte(dimensionSettings.getNumberTagOrThrow("coordinate_scale").getAsDouble() != 1.0));
			legacyDimensions.addTag(legacyDimension);
		}
		legacyRegistry.setTag("dimension", legacyDimensions);
		return legacyRegistry;
	}

}
