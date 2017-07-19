package protocolsupport.api.remapper;

import org.apache.commons.lang3.Validate;
import org.bukkit.Sound;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.id.RemappingTable.HashMapBasedStringRemappingTable;
import protocolsupport.protocol.typeremapper.sound.SoundRemapper;
import protocolsupport.protocol.utils.minecraftdata.SoundData;

public class SoundRemapperControl {
	private final HashMapBasedStringRemappingTable table;

	public SoundRemapperControl(ProtocolVersion version) {
		Validate.isTrue(version.isSupported(), "Can't control item remapping for unsupported version");
		table = SoundRemapper.SOUND_REMAPPING_REGISTRY.getTable(version);
	}
	
	/**
	 * Sets remap from one soundname to another for all data
	 * @param from {@link String} which will be remapped
	 * @param to {@link String} to which remap will occur
	 */
	public void setRemap(String from, String to) {
		table.setRemap(from, to);
	}
	
	/**
	 * Sets remap from a Bukkit Sound to another Sound.
	 * @param from {@link Sound} which will be remapped
	 * @param to {@link Sound} to which remap will occur
	 */
	public void setRemap(Sound from, Sound to) {
		setRemap(SoundData.getNameBySound(from), SoundData.getNameBySound(to));
	}
	
	/**
	 * Sets remap from a Bukkit Sound to another sound with specified name.
	 * @param from {@link Sound} which will be remapped
	 * @param to {@link String} to which remap will occur
	 */
	public void setRemap(Sound from, String to) {
		setRemap(SoundData.getNameBySound(from), to);
	}
	
	/**
	 * Sets remap from a sound id to another sound id.
	 * @param from {@link int} which will be remapped
	 * @param to {@link int} to which remap will occur
	 */
	public void setRemap(int from, int to) {
		setRemap(SoundData.getNameById(from), SoundData.getNameById(from));
	}
	
	/**
	 * Sets remap from a sound id to another sound with specified name.
	 * @param from {@link Sound} which will be remapped
	 * @param to {@link Sound} to which remap will occur
	 */
	public void setRemap(int from, String to) {
		setRemap(SoundData.getNameById(from), to);
	}
	
	/**
	 * Returns remap for specified soundname
	 * @param from {@link String}
	 * @return remap for specified soundname
	 */
	public String getRemap(String from) {
		return table.getRemap(from);
	}
	
	/**
	 * Returns remap for specified SoundId
	 * @param from {@link String}
	 * @return remap for specified soundname
	 */
	public String getRemap(int from) {
		return getRemap(SoundData.getNameById(from));
	}
	
	/**
	 * Returns remap for specified Sound
	 * @param from {@link String}
	 * @return remap for specified soundname
	 */
	public String getRemap(Sound from) {
		return getRemap(SoundData.getNameBySound(from));
	}
	
}
