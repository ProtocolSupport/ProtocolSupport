package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleLogin;
import protocolsupport.protocol.packet.middleimpl.PacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class Login extends MiddleLogin<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) throws IOException {
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_LOGIN_ID, version);
		serializer.writeInt(playerEntityId);
		serializer.writeByte(gamemode);
		serializer.writeByte(dimension);
		serializer.writeByte(difficulty);
		serializer.writeByte(maxplayers);
		serializer.writeString(leveltype);
		serializer.writeBoolean(reducedDebugInfo);
		return RecyclableSingletonList.create(serializer);
	}

}
