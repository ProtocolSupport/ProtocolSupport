package protocolsupport.api.unsafe.pemetadata;

import java.util.UUID;

import org.bukkit.entity.EntityType;

public abstract class PEMetaProvider {

	/**
	 * This method receives entity identifiers and should return the text that will be seen on use button
	 * @param uuid entity uuid
	 * @param id entity id
	 * @param entitytype entity type
	 * @return entity use text
	 */
	public abstract String getUseText(UUID uuid, int id, EntityType entitytype);

	/**
	 * This method receives entity identifiers and should return the size scale of an entity <br>
	 * The resulting entity size is: entity size * scale, so value of 1 won't change entity size
	 * @param uuid entity uuid
	 * @param id entity id
	 * @param entitytype entity type
	 * @return entity scale
	 */
	public abstract float getSizeScale(UUID uuid, int id, EntityType entitytype);

}
