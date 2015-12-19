package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6;

import java.util.Collection;
import java.util.Collections;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleLogin;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;

public class Login extends MiddleLogin<Collection<PacketData>> {

	@Override
	public Collection<PacketData> toData(ProtocolVersion version) {
		PacketDataSerializer serializer = PacketDataSerializer.createNew(version);
		serializer.writeInt(playerEntityId);
		serializer.writeString(leveltype);
		serializer.writeByte(gamemode);
		serializer.writeByte(dimension);
		serializer.writeByte(difficulty);
		serializer.writeByte(0);
		serializer.writeByte(Math.min(maxplayers, 60));
		return Collections.singletonList(new PacketData(ClientBoundPacket.PLAY_LOGIN_ID, serializer));
	}

}
