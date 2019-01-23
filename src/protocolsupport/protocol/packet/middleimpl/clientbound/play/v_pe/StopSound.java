package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleStopSound;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class StopSound extends MiddleStopSound {

	public StopSound(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		return RecyclableSingletonList.create(create(connection.getVersion(), name, stopAll));
	}

	public static ClientBoundPacketData create(ProtocolVersion version, String name, boolean stopAll) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.STOP_SOUND);
		StringSerializer.writeString(serializer, version, "");
		serializer.writeBoolean(true);
		return serializer;
	}
}
