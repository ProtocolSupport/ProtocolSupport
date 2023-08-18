package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__18;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import protocolsupport.ProtocolSupport;
import protocolsupport.ProtocolSupportFileLog;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.chat.components.TextComponent;
import protocolsupport.api.chat.components.TranslateComponent;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddlePlayerMessage;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.protocol.storage.netcache.ClientCache.NetworkChatType;
import protocolsupport.zplatform.ServerPlatform;

public abstract class AbstractUnsignedPlayerMessage extends MiddlePlayerMessage {

	protected AbstractUnsignedPlayerMessage(IMiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	protected BaseComponent getMessage() {
		NetworkChatType chattype = clientCache.getChatType(chatType);
		if (chattype == null) {
			String logMessage = MessageFormat.format("Unknown chat type id {0}", chatType);
			if (ServerPlatform.get().getMiscUtils().isDebugging()) {
				ProtocolSupport.logWarning(logMessage);
			}
			if (ProtocolSupportFileLog.isEnabled()) {
				ProtocolSupportFileLog.logWarningMessage(logMessage);
			}

			TextComponent root = new TextComponent("");
			root.addSibling(new TextComponent("<"));
			root.addSibling(senderDisplayName);
			root.addSibling(new TextComponent("> "));
			root.addSibling(unsignedMessage != null ? unsignedMessage : new TextComponent(message));
			return root;
		}

		List<BaseComponent> parameters = new ArrayList<>();
		for (String parameterName : chattype.translationParameters()) {
			parameters.add(switch (parameterName) {
				case NetworkChatType.PARAMETER_SENDER -> senderDisplayName;
				case NetworkChatType.PARAMETER_TARGET -> targetDisplayName != null ? targetDisplayName : new TextComponent("");
				case NetworkChatType.PARAMETER_CONTENT -> unsignedMessage != null ? unsignedMessage : new TextComponent(message);
				default -> new TextComponent("");
			});
		}
		return new TranslateComponent(chattype.translationKey(), parameters);
	}

}
