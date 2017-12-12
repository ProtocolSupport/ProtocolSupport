package protocolsupport.api.unsafe.pemetadata;

import protocolsupport.protocol.utils.types.NetworkEntity;

public abstract class PEMetaProvider {

	/**
	 * This method receives the NetworkEntity and should return the size of the entity to send this to PE.
	 * Return 1 for default size.
	 * @param entity
	 * @return
	 */
	public abstract float getEntitySize(NetworkEntity entity);

	/**
	 * This method receives the NetworkEntity and should return what interact button text to display. Set to null to not display button at all.
	 * @param entity
	 * @return
	 */
	public abstract String getInteractText(NetworkEntity entity);
}
