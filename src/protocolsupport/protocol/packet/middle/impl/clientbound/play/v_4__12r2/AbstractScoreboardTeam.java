package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__12r2;

import protocolsupport.api.chat.ChatFormat;
import protocolsupport.api.chat.components.TextComponent;
import protocolsupport.api.utils.Any;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleScoreboardTeam;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleScoreboardTeam.ScoreboardTeamsTracker.TrackedScoreboardTeam;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.protocol.typeremapper.legacy.LegacyChat;

public abstract class AbstractScoreboardTeam extends MiddleScoreboardTeam {

	protected AbstractScoreboardTeam(IMiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();


	protected final Any<String, String> formatPrefixSuffix() {
		return isDisplayLineTeam() ? formatDisplayLinePrefixSuffix() : new Any<>(formatLegacyClampedPrefix(), formatLegacyClampedSuffix());
	}

	protected boolean isDisplayLineTeam() {
		if (
			suffix.isSimple() &&
			(suffix instanceof TextComponent suffixTextComponent) &&
			suffixTextComponent.getText().isEmpty()
		) {
			TrackedScoreboardTeam team = teamsTracker.getTeam(name);
			for (String player : team.getPlayers()) {
				if (!LegacyChat.isEmpty(player)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	protected static final int FORMAT_CODE_LENGTH = 2;
	protected static final int NFIX_LIMIT = 16;
	protected static final int NFIX_LIMIT_1F = NFIX_LIMIT - FORMAT_CODE_LENGTH;
	protected static final int NFIX_LIMIT_2F = NFIX_LIMIT_1F - FORMAT_CODE_LENGTH;
	protected static final int NFIX_LIMIT_FCUT = NFIX_LIMIT - 1;

	protected String formatLegacyClampedPrefix() {
		String prefix = this.prefix.toLegacyText(clientCache.getLocale());

		if ((format != ChatFormat.RESET) && !prefix.isEmpty() && !isStringFormatOverride(prefix, format)) {
			prefix = format + prefix;
		}

		String prefixLastFormatString = ChatFormat.getLastFomat(prefix);
		String formatString = format.toString();
		if (
			(prefixLastFormatString.isEmpty() && (format != ChatFormat.RESET)) ||
			!formatString.equals(prefixLastFormatString)
		) {
			if (!format.hasColor()) {
				return LegacyChat.clampLegacyText(prefix, NFIX_LIMIT_2F) + ChatFormat.RESET.toStyle() + formatString;
			} else {
				return LegacyChat.clampLegacyText(prefix, NFIX_LIMIT_1F) + formatString;
			}
		}
		return LegacyChat.clampLegacyText(prefix, NFIX_LIMIT);
	}

	protected String formatLegacyClampedSuffix() {
		String suffix = this.suffix.toLegacyText(clientCache.getLocale());

		if (!isStringFormatOverride(suffix, format)) {
			suffix = format + suffix;
			if (!format.hasColor()) {
				suffix = ChatFormat.RESET.toStyle() + suffix;
			}
		}

		return LegacyChat.clampLegacyText(suffix, NFIX_LIMIT);
	}

	protected Any<String, String> formatDisplayLinePrefixSuffix() {
		String prefix = this.prefix.toLegacyText(clientCache.getLocale());

		if ((format != ChatFormat.RESET) && !isStringFormatOverride(prefix, format)) {
			prefix = format + prefix;
		}

		if (prefix.length() <= NFIX_LIMIT) {
			return new Any<>(prefix, "");
		}

		int limit = prefix.charAt(NFIX_LIMIT_FCUT) == ChatFormat.StyleCode.CONTROL_CHAR ? NFIX_LIMIT_FCUT : NFIX_LIMIT;

		String sPrefix = prefix.substring(0, limit);

		String sSuffix = ChatFormat.getLastFomat(sPrefix) + prefix.substring(limit);
		if (!isStringFormatOverride(sSuffix, ChatFormat.RESET)) {
			sSuffix = ChatFormat.RESET.toStyle() + sSuffix;
		}
		sSuffix = LegacyChat.clampLegacyText(sSuffix, NFIX_LIMIT);

		return new Any<>(sPrefix, sSuffix);
	}

	protected static boolean isStringFormatOverride(String string, ChatFormat format) {
		if ((string.length() >= 2) && (string.charAt(0) == ChatFormat.StyleCode.CONTROL_CHAR)) {
			ChatFormat formatStringColor = ChatFormat.ofChar(string.charAt(1));
			if ((formatStringColor != null) && (formatStringColor.hasColor() || (formatStringColor == format))) {
				return true;
			}
		}
		return false;
	}

}
