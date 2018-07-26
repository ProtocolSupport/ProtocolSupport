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
	 * Modifying this itemstack has no effect
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
	 * Previously returned the resulting itemstack
	 * Now returns a writethrough shared stone itemstack which only copies lore and name to the result
	 * @return resulting itemstack
	 * @deprecated replaced by ability to add lore and set name for resulting itemstack
	 */
	@Deprecated
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
