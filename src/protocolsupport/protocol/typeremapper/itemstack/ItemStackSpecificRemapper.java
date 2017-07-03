package protocolsupport.protocol.typeremapper.itemstack;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;

@FunctionalInterface
public interface ItemStackSpecificRemapper {

	public ItemStackWrapper remap(ProtocolVersion version, String locale, ItemStackWrapper itemstack);

}
