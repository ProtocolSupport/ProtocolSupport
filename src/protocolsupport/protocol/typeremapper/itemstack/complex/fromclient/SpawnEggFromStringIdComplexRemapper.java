package protocolsupport.protocol.typeremapper.itemstack.complex.fromclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackComplexRemapper;
import protocolsupport.protocol.typeremapper.legacy.LegacyEntityId;
import protocolsupport.protocol.utils.ItemSpawnEggData;
import protocolsupport.protocol.utils.networkentity.NetworkEntityType;
import protocolsupport.protocol.utils.types.NetworkItemStack;
import protocolsupport.protocol.utils.types.nbt.NBTCompound;
import protocolsupport.protocol.utils.types.nbt.NBTString;
import protocolsupport.protocol.utils.types.nbt.NBTType;

public class SpawnEggFromStringIdComplexRemapper implements ItemStackComplexRemapper {

	protected final boolean legacy;
	public SpawnEggFromStringIdComplexRemapper(boolean legacyId) {
		this.legacy = legacyId;
	}

	@Override
	public NetworkItemStack remap(ProtocolVersion version, String locale, NetworkItemStack itemstack) {
		NBTCompound rootTag = itemstack.getNBT();
		if (rootTag != null) {
			NBTCompound entityTag = rootTag.getTagOfType("EntityTag", NBTType.COMPOUND);
			if (entityTag != null) {
				rootTag.removeTag("EntityTag");
				String stype = NBTString.getValueOrNull(entityTag.getTagOfType("id", NBTType.STRING));
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
