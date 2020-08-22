package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_16r2;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleStartGame;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class StartGame extends MiddleStartGame {

	public StartGame(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeToClient() {
		ClientBoundPacketData startgame = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_START_GAME);
		startgame.writeInt(player.getId());
		startgame.writeBoolean(hardcore);
		startgame.writeByte(gamemodeCurrent.getId());
		startgame.writeByte(gamemodePrevious.getId());
		ArraySerializer.writeVarIntVarIntUTF8StringArray(startgame, worlds);
		ItemStackSerializer.writeDirectTag(startgame, dimensions);
		ItemStackSerializer.writeDirectTag(startgame, dimension);
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

}
