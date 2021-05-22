package protocolsupport.protocol.typeremapper.itemstack.format;

import protocolsupport.protocol.types.NetworkItemStack;

@FunctionalInterface
public interface ItemStackLegacyFormatOperator {

	public NetworkItemStack apply(String locale, NetworkItemStack itemstack);

}
