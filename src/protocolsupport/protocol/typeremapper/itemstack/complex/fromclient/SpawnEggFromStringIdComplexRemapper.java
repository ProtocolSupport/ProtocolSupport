package protocolsupport.protocol.typeremapper.itemstack.complex.fromclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackComplexRemapper;
import protocolsupport.protocol.typeremapper.legacy.LegacyEntityId;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.protocol.utils.ItemSpawnEggData;

public class SpawnEggFromStringIdComplexRemapper implements ItemStackComplexRemapper {

	protected final boolean legacy;

	public SpawnEggFromStringIdComplexRemapper(boolean legacyId) {
		this.legacy = legacyId;
	}

	@Override
	public NetworkItemStack remap(ProtocolVersion version, String locale, NetworkItemStack itemstack) {
		NBTCompound rootTag = itemstack.getNBT();
		if (rootTag != null) {
			NBTCompound entityTag = rootTag.removeTagAndReturnIfType("EntityTag", NBTCompound.class);
			if (entityTag != null) {
				String stype = entityTag.getStringTagValueOrNull("id");
				if (stype != null) {
					int eggTypeId = ItemSpawnEggData.getMaterialIdBySpawnedType(legacy ? LegacyEntityId.getTypeByStringId(stype) : NetworkEntityType.getByRegistrySTypeId(stype));
					if (eggTypeId != -1) {
						itemstack.setTypeId(eggTypeId);
					}
				}
			}
		}
		return itemstack;
	}

}
