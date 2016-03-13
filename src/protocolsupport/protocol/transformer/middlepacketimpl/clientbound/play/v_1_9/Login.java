package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_9;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleLogin;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class Login extends MiddleLogin<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) throws IOException {
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_LOGIN_ID, version);
		serializer.writeInt(playerEntityId);
		serializer.writeByte(gamemode);
		if (version.isAfter(ProtocolVersion.MINECRAFT_1_9)) {
			serializer.writeInt(dimension);
		} else {
			serializer.writeByte(dimension);
		}
		serializer.writeByte(difficulty);
		serializer.writeByte(maxplayers);
		serializer.writeString(leveltype);
		serializer.writeBoolean(reducedDebugInfo);
		return RecyclableSingletonList.create(serializer);
	}

	@Override
	public void handle() {
	}

}
