package protocolsupport.api.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.utils.i18n.I18NData;

/**
 * This event is fired when itemstack is being written to client
 */
public abstract class ItemStackWriteEvent extends Event {

	private final ProtocolVersion version;
	private final String locale;
	private final ItemStack original;

	@Deprecated
	public ItemStackWriteEvent(ProtocolVersion version, ItemStack original) {
		this(version, I18NData.DEFAULT_LOCALE, original);
	}

	public ItemStackWriteEvent(ProtocolVersion version, String locale, ItemStack original) {
		super(true);
		this.version = version;
		this.locale = locale;
		this.original = original;
	}

	/**
	 * Returns the original itemstack clone (no remaps applied) <br>
	 * Modifying this itemstack has not effect
	 * @return original itemstack
	 */
	public ItemStack getOriginal() {
		return original.clone();
	}

	/**
	 * Returns client protocol version
	 * @return client protocol version
	 */
	public ProtocolVersion getVersion() {
		return version;
	}

	/**
	 * Returns client locale (lowercase)
	 * @return client locale
	 */
	public String getLocale() {
		return locale;
	}

	/**
	 * Returns the remapped itemstack <br>
	 * This itemstack should be modified to change the resulting sent itemstack <br>
	 * Modifying this itemstack to unsupported values (air material (0 type id), impossible data (< 0, > Short.MAX_VALUE), or impossible count (< 0, > 127)) will cause a client error
	 * @return resulting itemstack
	 */
	public abstract ItemStack getResult();

	private static final HandlerList list = new HandlerList();

	@Override
	public HandlerList getHandlers() {
		return list;
	}

	public static HandlerList getHandlerList() {
		return list;
	}

}
