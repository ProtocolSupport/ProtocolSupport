package protocolsupport.api.remapper;

import org.bukkit.Effect;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.protocol.typeremapper.id.RemappingTable.HashMapBasedIdRemappingTable;

public class EffectRemapperControl {

	private final HashMapBasedIdRemappingTable table;

	public EffectRemapperControl(ProtocolVersion version) {
		switch (version) {
			case UNKNOWN: {
				throw new IllegalArgumentException(version+" is not a valid protocol version");
			}
			default: {
				table = IdRemapper.EFFECT.getTable(version);
				break;
			}
		}
	}

	@SuppressWarnings("deprecation")
	public void setRemap(Effect from, Effect to) {
		if (from.getType() != to.getType()) {
			throw new IllegalArgumentException("Effect types differ");
		}
		setRemap(from.getId(), to.getId());
	}

	public void setRemap(int from, int to) {
		table.setRemap(from, to);
	}

	@SuppressWarnings("deprecation")
	public Effect getRemap(Effect from) {
		return Effect.getById(getRemap(from.getId()));
	}

	public int getRemap(int from) {
		return table.getRemap(from);
	}

}
