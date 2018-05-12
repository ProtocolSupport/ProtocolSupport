package protocolsupport.api.remapper;

import org.bukkit.Effect;

import protocolsupport.api.ProtocolVersion;

/**
 * @deprecated Isn't used, was added by mistake, and isn't useful anyway because it doesn't support remapping data along with effect id
 */
public class EffectRemapperControl {

	public EffectRemapperControl(ProtocolVersion version) {
	}

	public void setRemap(Effect from, Effect to) {
	}

	public void setRemap(int from, int to) {
	}

	public Effect getRemap(Effect from) {
		return from;
	}

	public int getRemap(int from) {
		return from;
	}

}
