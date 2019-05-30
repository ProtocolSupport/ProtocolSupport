//TODO: how is this supposed to work now....
//package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;
//
//import protocolsupport.protocol.ConnectionImpl;
//import protocolsupport.protocol.packet.middle.clientbound.play.MiddleUseBed;
//import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
//import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectByte;
//import protocolsupport.protocol.types.networkentity.metadata.objects.DataWatcherObjectVector3vi;
//import protocolsupport.protocol.types.networkentity.NetworkEntity;
//import protocolsupport.utils.recyclable.RecyclableArrayList;
//import protocolsupport.utils.recyclable.RecyclableCollection;
//
//public class UseBed extends MiddleUseBed {
//
//	public UseBed(ConnectionImpl connection) {
//		super(connection);
//	}
//
//	@Override
//	public RecyclableCollection<ClientBoundPacketData> toData() {
//		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
//		NetworkEntity player = cache.getWatchedEntityCache().getSelfPlayer();
//
//		if (entityId == player.getId()) {
//			packets.add(EntityMetadata.createFromAttribute(version, cache.getAttributesCache().getLocale(), player, EntityMetadata.PeMetaBase.BED_POSTION, new DataWatcherObjectVector3vi(bed)));
//			// In theory, we should add the bit corresponding to PLAYER_FLAG_SLEEP to existing player flags.
//			// However, we don't track any other flags, and PE client resets this flag automatically for us.
//			// So we just consider it a value to be sent.
//			packets.add(EntityMetadata.createFromAttribute(version, cache.getAttributesCache().getLocale(), player, EntityMetadata.PeMetaBase.PLAYER_FLAGS, new DataWatcherObjectByte(EntityMetadata.PeMetaBase.PLAYER_FLAG_SLEEP)));
//			packets.add(SpawnPosition.setPlayerSpawn(bed));
//		}
//
//		return packets;
//	}
//}