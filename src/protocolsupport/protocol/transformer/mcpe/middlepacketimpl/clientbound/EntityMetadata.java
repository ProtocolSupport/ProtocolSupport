package protocolsupport.protocol.transformer.mcpe.middlepacketimpl.clientbound;

import java.io.IOException;

import net.minecraft.server.v1_9_R2.ItemStack;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityMetadata;
import protocolsupport.protocol.storage.PEStorage.ItemInfo;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound.AddItemEntityPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound.SetEntityDataPacket;
import protocolsupport.protocol.typeremapper.watchedentity.WatchedDataRemapper;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityMetadata extends MiddleEntityMetadata<RecyclableCollection<? extends ClientboundPEPacket>> {

	@Override
	public RecyclableCollection<? extends ClientboundPEPacket> toData(ProtocolVersion version) throws IOException {
		ItemInfo info = storage.getPEStorage().getItemInfo(entityId);
		if (info != null) {
			if (metadata.containsKey(10)) {
				storage.getPEStorage().removeItemsInfo(entityId);
				return RecyclableSingletonList.create(new AddItemEntityPacket(
					entityId, (float) info.getX(), (float) info.getY(), (float) info.getZ(),
					info.getSpeedX(), info.getSpeedY(), info.getSpeedZ(), (ItemStack) metadata.get(5).getValue()
				));
			}
		} else {
			return RecyclableSingletonList.create(new SetEntityDataPacket(
				entityId,
				WatchedDataRemapper.transform(storage.getWatchedEntity(entityId), metadata, version)
			));
		}
		return RecyclableEmptyList.get();
	}

}
