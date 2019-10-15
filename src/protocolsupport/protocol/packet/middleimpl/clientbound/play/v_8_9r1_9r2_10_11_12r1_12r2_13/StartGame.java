package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleStartGame;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.types.Difficulty;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class StartGame extends MiddleStartGame {

	public StartGame(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<? extends IPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_START_GAME);
		serializer.writeInt(player.getId());
		serializer.writeByte(gamemode.getId() | (hardcore ? 0x8 : 0));
		if (version.isBefore(ProtocolVersion.MINECRAFT_1_9_1)) {
			serializer.writeByte(dimension.getId());
		} else {
			serializer.writeInt(dimension.getId());
		}
		MiscSerializer.writeByteEnum(serializer, Difficulty.HARD);
		serializer.writeByte(maxplayers);
		StringSerializer.writeVarIntUTF8String(serializer, leveltype);
		serializer.writeBoolean(reducedDebugInfo);
		return RecyclableSingletonList.create(serializer);
	}

}
