package protocolsupport.protocol.typeremapper.itemstack.complex;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.utils.types.NetworkItemStack;

@FunctionalInterface
public interface ItemStackComplexRemapper {

	//TODO: All of the item remappers return a NetworkItemStack, but never make a new NetworkItemStack. they're always mutated
	public NetworkItemStack remap(ProtocolVersion version, String locale, NetworkItemStack itemstack);

}
