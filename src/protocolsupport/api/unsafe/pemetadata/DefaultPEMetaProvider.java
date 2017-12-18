package protocolsupport.api.unsafe.pemetadata;

import java.util.UUID;

import org.bukkit.entity.EntityType;

public class DefaultPEMetaProvider extends PEMetaProvider {

	@Override
	public String getUseText(UUID uuid, int id, EntityType entitytype) {
		return "Interact";
	}

	@Override
	public float getSizeScale(UUID uuid, int id, EntityType entitytype) {
		return 1f;
	}

}
