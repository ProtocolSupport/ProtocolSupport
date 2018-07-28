package protocolsupport.api.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import protocolsupport.api.ProtocolVersion;

/**
 * This event is fired when itemstack is being written to client
 */
public abstract class ItemStackWriteEvent extends Event {

	protected final ProtocolVersion version;
	protected final String locale;
	protected final ItemStack original;

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

	private static final HandlerList list = new HandlerList();

	@Override
	public HandlerList getHandlers() {
		return list;
	}

	public static HandlerList getHandlerList() {
		return list;
	}

}
