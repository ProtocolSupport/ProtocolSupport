package protocolsupport.protocol.typeremapper.itemstack.format.from;

import protocolsupport.protocol.typeremapper.itemstack.format.ItemStackLegacyFormatOperator;
import protocolsupport.protocol.typeremapper.legacy.LegacyEntityId;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.protocol.utils.ItemSpawnEggData;

public class ItemStackLegacyFormatOperatorSpawnEggFromStringId implements ItemStackLegacyFormatOperator {

	protected final boolean legacy;

	public ItemStackLegacyFormatOperatorSpawnEggFromStringId(boolean legacyId) {
		this.legacy = legacyId;
	}

	@Override
	public NetworkItemStack apply(String locale, NetworkItemStack itemstack) {
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
