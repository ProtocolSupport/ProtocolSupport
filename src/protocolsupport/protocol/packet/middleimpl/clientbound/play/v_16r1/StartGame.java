package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_16r1;

import java.util.Map;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleStartGame;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
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
	protected void writeToClient() {
		ClientBoundPacketData startgame = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_START_GAME);
		startgame.writeInt(player.getId());
		startgame.writeByte(gamemodeCurrent.getId() | (hardcore ? 0x8 : 0));
		startgame.writeByte(gamemodePrevious.getId());
		ArraySerializer.writeVarIntVarIntUTF8StringArray(startgame, worlds);
		ItemStackSerializer.writeDirectTag(startgame, toLegacyDimensionRegistry(dimensions));
		StringSerializer.writeVarIntUTF8String(startgame, LegacyDimension.getStringId(dimension));
		StringSerializer.writeVarIntUTF8String(startgame, world);
		startgame.writeLong(hashedSeed);
		startgame.writeByte(maxplayers);
		VarNumberSerializer.writeVarInt(startgame, renderDistance);
		startgame.writeBoolean(reducedDebugInfo);
		startgame.writeBoolean(respawnScreenEnabled);
		startgame.writeBoolean(worldDebug);
		startgame.writeBoolean(worldFlat);
		codec.write(startgame);
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
