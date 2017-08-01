package protocolsupport.api.remapper;

import org.apache.commons.lang3.Validate;
import org.bukkit.Effect;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.HashMapBasedIdRemappingTable;

public class EffectRemapperControl {

	private final HashMapBasedIdRemappingTable table;

	public EffectRemapperControl(ProtocolVersion version) {
		Validate.isTrue(version.isSupported(), "Can't control effect remapping for unsupported version");
		table = IdRemapper.EFFECT.getTable(version);
	}

	/**
	 * Sets remap from one effect to another
	 * @param from {@link Effect} which will be remapped
	 * @param to {@link Effect} to which remap will occur
	 */
	@SuppressWarnings("deprecation")
	public void setRemap(Effect from, Effect to) {
		if (from.getType() != to.getType()) {
			throw new IllegalArgumentException("Effect types differ");
		}
		setRemap(from.getId(), to.getId());
	}

	/**
	 * Sets remap from one effect id to another
	 * @param from effect id which will be remapped
	 * @param to effect id to which remap will occur
	 */
	public void setRemap(int from, int to) {
		table.setRemap(from, to);
	}

	/**
	 * Returns remap for specified effect
	 * @param from {@link Effect}
	 * @return remap for specified effect
	 */
	@SuppressWarnings("deprecation")
	public Effect getRemap(Effect from) {
		return Effect.getById(getRemap(from.getId()));
	}

	/**
	 * Returns remap for specified effect id
	 * @param from effect id
	 * @return remap for specified effect id
	 */
	public int getRemap(int from) {
		return table.getRemap(from);
	}

}
