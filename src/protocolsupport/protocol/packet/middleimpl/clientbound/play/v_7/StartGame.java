package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_7;

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
		serializer.writeByte(dimension.getId());
		MiscSerializer.writeByteEnum(serializer, Difficulty.HARD);
		serializer.writeByte(Math.min(maxplayers, 60));
		StringSerializer.writeString(serializer, version, leveltype);
		return RecyclableSingletonList.create(serializer);
	}

}
