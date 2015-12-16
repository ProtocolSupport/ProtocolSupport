package protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v_1_5_1_6;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.serverbound.play.MiddleUseEntity;
import protocolsupport.protocol.transformer.middlepacketimpl.SupportedVersions;
import protocolsupportbuildprocessor.annotations.NeedsNoArgConstructor;

@NeedsNoArgConstructor
@SupportedVersions({ProtocolVersion.MINECRAFT_1_6_4, ProtocolVersion.MINECRAFT_1_6_2, ProtocolVersion.MINECRAFT_1_5_2})
public class UseEntity extends MiddleUseEntity {

	@Override
	public void readFromClientData(PacketDataSerializer serializer) {
		serializer.readInt();
		entityId = serializer.readInt();
		action = serializer.readBoolean() ? 1 : 0;
	}

}
