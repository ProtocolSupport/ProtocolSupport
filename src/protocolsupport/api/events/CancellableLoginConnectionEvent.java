package protocolsupport.api.events;

import org.bukkit.event.Cancellable;

import protocolsupport.api.Connection;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.chat.components.TextComponent;

public abstract class CancellableLoginConnectionEvent extends ConnectionEvent implements Cancellable {

	public CancellableLoginConnectionEvent(Connection connection, boolean async) {
		super(connection, async);
	}

	public CancellableLoginConnectionEvent(Connection connection) {
		this(connection, true);
	}

	protected BaseComponent denyLoginMessage;

	/**
	 * Returns true if login is denied
	 * @return true if login is denied
	 */
	public boolean isLoginDenied() {
		return denyLoginMessage != null;
	}

	/**
	 * Returns deny login message or null if login is not denied
	 * @return deny login message or null
	 */
	public BaseComponent getDenyLoginMessageJson() {
		return denyLoginMessage;
	}

	/**
	 * Returns deny login message or null if login is not denied
	 * @return deny login message or null
	 */
	public String getDenyLoginMessage() {
		return denyLoginMessage != null ? denyLoginMessage.toLegacyText() : null;
	}

	/**
	 * Sets the login deny message
	 * If message is null, login won't be denied
	 * @param messageJson login deny message
	 */
	public void denyLogin(BaseComponent messageJson) {
		this.denyLoginMessage = messageJson;
	}

	/**
	 * Sets the login deny message
	 * If message is null, login won't be denied
	 * @param message login deny message
	 */
	public void denyLogin(String message) {
		this.denyLoginMessage = BaseComponent.fromMessage(message);
	}

	@Override
	public boolean isCancelled() {
		return isLoginDenied();
	}

	@Override
	public void setCancelled(boolean cancel) {
		if (cancel) {
			this.denyLoginMessage = new TextComponent("Login denied");
		} else {
			this.denyLoginMessage = null;
		}
	}

}
