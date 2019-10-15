package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_14r1_14r2;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleStartGame;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
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
		serializer.writeInt(dimension.getId());
		serializer.writeByte(maxplayers);
		StringSerializer.writeVarIntUTF8String(serializer, leveltype);
		VarNumberSerializer.writeVarInt(serializer, renderDistance);
		serializer.writeBoolean(reducedDebugInfo);
		return RecyclableSingletonList.create(serializer);
	}

}
