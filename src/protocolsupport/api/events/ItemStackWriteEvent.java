package protocolsupport.api.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.utils.i18n.I18NData;

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

	public ItemStack getOriginal() {
		return original.clone();
	}

	public ProtocolVersion getVersion() {
		return version;
	}

	public String getLocale() {
		return locale;
	}

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
