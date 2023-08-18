package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_20;

import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.codec.OptionalCodec;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleStartGame;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV20;

public class StartGame extends MiddleStartGame implements
IClientboundMiddlePacketV20 {

	public StartGame(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData startgamePacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_START_GAME);
		startgamePacket.writeInt(player.getId());
		startgamePacket.writeBoolean(hardcore);
		startgamePacket.writeByte(gamemodeCurrent.getId());
		startgamePacket.writeByte(gamemodePrevious.getId());
		ArrayCodec.writeVarIntVarIntUTF8StringArray(startgamePacket, worlds);
		ItemStackCodec.writeDirectTag(startgamePacket, registries);
		StringCodec.writeVarIntUTF8String(startgamePacket, worldType);
		StringCodec.writeVarIntUTF8String(startgamePacket, worldName);
		startgamePacket.writeLong(hashedSeed);
		VarNumberCodec.writeVarInt(startgamePacket, maxplayers);
		VarNumberCodec.writeVarInt(startgamePacket, renderDistance);
		VarNumberCodec.writeVarInt(startgamePacket, simulationDistance);
		startgamePacket.writeBoolean(reducedDebugInfo);
		startgamePacket.writeBoolean(respawnScreenEnabled);
		startgamePacket.writeBoolean(worldDebug);
		startgamePacket.writeBoolean(worldFlat);
		OptionalCodec.writeOptional(startgamePacket, deathPosition, PositionCodec::writeWorldPosition);
		VarNumberCodec.writeVarInt(startgamePacket, portalCooldown);
		io.writeClientbound(startgamePacket);
	}

}
