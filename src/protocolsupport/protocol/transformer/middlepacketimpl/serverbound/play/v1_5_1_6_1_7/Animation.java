package protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v1_5_1_6_1_7;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.serverbound.play.MiddleAnimation;
import protocolsupport.protocol.transformer.middlepacketimpl.SupportedVersions;

@SupportedVersions({ProtocolVersion.MINECRAFT_1_7_10, ProtocolVersion.MINECRAFT_1_7_5, ProtocolVersion.MINECRAFT_1_6_4, ProtocolVersion.MINECRAFT_1_6_2, ProtocolVersion.MINECRAFT_1_5_2})
public class Animation extends MiddleAnimation {

	@Override
	public void readFromClientData(PacketDataSerializer serializer) {
		serializer.readInt();
		serializer.readByte();
	}

}
