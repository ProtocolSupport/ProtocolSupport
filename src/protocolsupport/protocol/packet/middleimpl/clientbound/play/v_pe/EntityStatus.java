package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import gnu.trove.set.hash.TIntHashSet;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityStatus;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.utils.types.NetworkEntity;
import protocolsupport.protocol.utils.types.NetworkEntityType;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;

public class EntityStatus extends MiddleEntityStatus {

	//TODO: Actually remap and skip the status codes. It seems that with the new update PE crashes if ID is unknown.
	TIntHashSet allowedIds = new TIntHashSet(new int[] {2, 3, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 17, 18, 31, 34, 57, 63});

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		if(allowedIds.contains(status)) {
			NetworkEntity e = cache.getWatchedEntity(entityId);
			if (e == null) { // Sometimes the server (nasty plugins?) tries to send Entity Status updates for despawned entities, if the entity isn't cached, ignore the update.
				return RecyclableEmptyList.get();
			}
			if ((status == 31) && (e.getType() == NetworkEntityType.FISHING_FLOAT)) {
				status = 13;
			}
			if ((status == 10) && (e.getType() == NetworkEntityType.MINECART_TNT)) {
				status = 31;
			}
			if ((status == 17) && (e.getType() == NetworkEntityType.FIREWORK)) {
				status = 25;
			}
			packets.add(create(e, status, connection.getVersion()));
		}
		return packets;
	}

	public static int PE_UNLEASH = 63;

	public static ClientBoundPacketData create(NetworkEntity entity, int status, ProtocolVersion version) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.ENTITY_EVENT, version);
		VarNumberSerializer.writeVarLong(serializer, entity.getId());
		serializer.writeByte((byte) status);
		VarNumberSerializer.writeVarInt(serializer, 0); //?
		return serializer;
	}

}
