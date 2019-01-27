package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleStopSound;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.utils.NamespacedKeyUtils;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

import javax.xml.stream.events.Namespace;

public class StopSound extends MiddleStopSound {

	public StopSound(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		return RecyclableSingletonList.create(create(connection.getVersion(), name));
	}

	public static ClientBoundPacketData create(ProtocolVersion version, String name) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.STOP_SOUND);
		if(name != null){
			StringSerializer.writeString(serializer, version, NamespacedKeyUtils.fromString(name).getKey());
			serializer.writeBoolean(false);
		}
		else{
			StringSerializer.writeString(serializer, version, "");
			serializer.writeBoolean(true);
		}
		return serializer;
	}
}
