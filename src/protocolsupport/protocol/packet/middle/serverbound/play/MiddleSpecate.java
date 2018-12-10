package protocolsupport.protocol.packet.middle.serverbound.play;

import java.util.UUID;

import protocolsupport.api.ProtocolType;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleSpecate extends ServerBoundMiddlePacket {

	public MiddleSpecate(ConnectionImpl connection) {
		super(connection);
	}

	protected UUID entityUUID;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.PLAY_SPECTATE);
		// FIXME: Maybe we can assume PC here..? Only implemented in v_8_9r1_9r2_10_11_12r1_12r2_13
		if (connection.getVersion().getProtocolType() == ProtocolType.PE) {
			MiscSerializer.writePEUUID(creator, entityUUID);
		} else {
			MiscSerializer.writeUUID(creator, entityUUID);
		}
		return RecyclableSingletonList.create(creator);
	}

}
