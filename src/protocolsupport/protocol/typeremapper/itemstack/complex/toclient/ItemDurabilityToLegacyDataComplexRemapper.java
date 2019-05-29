package protocolsupport.protocol.typeremapper.itemstack.complex.toclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackComplexRemapper;
import protocolsupport.protocol.utils.CommonNBT;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTNumber;

public class ItemDurabilityToLegacyDataComplexRemapper implements ItemStackComplexRemapper {

	@Override
	public NetworkItemStack remap(ProtocolVersion version, String locale, NetworkItemStack itemstack) {
		NBTNumber damage;
		if ((itemstack.getNBT() != null) && ((damage = itemstack.getNBT().getNumberTag(CommonNBT.DAMAGE)) != null)) {
			itemstack.setLegacyData(damage.getAsInt());
		}
		return itemstack;
	}


}
