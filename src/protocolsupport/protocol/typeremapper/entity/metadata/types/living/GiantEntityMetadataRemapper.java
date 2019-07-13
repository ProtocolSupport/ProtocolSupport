package protocolsupport.protocol.typeremapper.entity.metadata.types.living;

import protocolsupport.api.unsafe.pemetadata.PEMetaProviderSPI;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityMetadata.PeMetaBase;
import protocolsupport.protocol.typeremapper.entity.metadata.NetworkEntityMetadataObjectRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.InsentientEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.pe.PEDataValues;
import protocolsupport.protocol.typeremapper.pe.PEDataValues.PEEntityData;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectFloatLe;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class GiantEntityMetadataRemapper extends InsentientEntityMetadataRemapper {

	public static final GiantEntityMetadataRemapper INSTANCE = new GiantEntityMetadataRemapper();

	public GiantEntityMetadataRemapper() {
		addRemap(new NetworkEntityMetadataObjectRemapper() {
			@Override
			public void remap(NetworkEntity entity, ArrayMap<NetworkEntityMetadataObject<?>> original, ArrayMap<NetworkEntityMetadataObject<?>> remapped) {
				entity.getDataCache().setSizeModifier(6f);
				float entitySize = 6f * PEMetaProviderSPI.getProvider().getSizeScale(entity.getUUID(), entity.getId(), entity.getType().getBukkitType());
				remapped.put(PeMetaBase.SCALE, new NetworkEntityMetadataObjectFloatLe(entitySize)); //Send scale -> Giants are Giant Zombies in PE.
				PEEntityData pocketdata = PEDataValues.getEntityData(entity.getType());
				if (pocketdata.getBoundingBox() != null) {
					remapped.put(PeMetaBase.BOUNDINGBOX_WIDTH, new NetworkEntityMetadataObjectFloatLe(pocketdata.getBoundingBox().getWidth() * entitySize));
					remapped.put(PeMetaBase.BOUNDINGBOX_HEIGTH, new NetworkEntityMetadataObjectFloatLe(pocketdata.getBoundingBox().getHeight() * entitySize));
				}
			}
		}, ProtocolVersionsHelper.ALL_PE);
	}

}
