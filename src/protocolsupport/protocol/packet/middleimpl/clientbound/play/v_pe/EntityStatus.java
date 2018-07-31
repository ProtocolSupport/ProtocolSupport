package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.ProtocolSupport;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityStatus;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEDataValues;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.utils.types.networkentity.NetworkEntityType;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

import java.text.MessageFormat;

public class EntityStatus extends MiddleEntityStatus {
	/* The UNLEASH entity status is sent from the EntityLeash packet */
	public static final int UNLEASH = 63;

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		NetworkEntityType entityType = cache.getWatchedEntityCache().getWatchedEntity(entityId).getType();
		int peStatus = PEDataValues.getEntityStatusRemap(status, entityType);

		if (peStatus != -1) {
			ProtocolSupport.logTrace(MessageFormat.format("Entity status mapped from {0} to {1}",
				status, peStatus));
			packets.add(create(entityId, peStatus, connection.getVersion()));
		} else {
			ProtocolSupport.logTrace(MessageFormat.format("Entity status {0} ignored", status));
		}
		return packets;
	}

	public static ClientBoundPacketData create(int entityId, int status, ProtocolVersion version) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.ENTITY_EVENT);
		VarNumberSerializer.writeVarLong(serializer, entityId);
		serializer.writeByte((byte) status);
		VarNumberSerializer.writeVarInt(serializer, 0); //?
		return serializer;
	}

}
