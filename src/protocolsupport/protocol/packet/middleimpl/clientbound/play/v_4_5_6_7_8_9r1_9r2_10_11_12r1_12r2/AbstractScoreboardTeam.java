package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2;

import org.bukkit.ChatColor;

import protocolsupport.api.chat.components.TextComponent;
import protocolsupport.api.utils.Any;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleScoreboardTeam;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleScoreboardTeam.ScoreboardTeamsTracker.TrackedScoreboardTeam;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.protocol.typeremapper.legacy.LegacyChat;

public abstract class AbstractScoreboardTeam extends MiddleScoreboardTeam {

	public AbstractScoreboardTeam(MiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();


	protected final Any<String, String> formatPrefixSuffix() {
		return isDisplayLineTeam() ? formatDisplayLinePrefixSuffix() : new Any<>(formatLegacyClampedPrefix(), formatLegacyClampedSuffix());
	}

	protected boolean isDisplayLineTeam() {
		if (
			suffix.isSimple() &&
			(suffix instanceof TextComponent) &&
			((TextComponent) suffix).getText().isEmpty()
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

		if ((format != ChatColor.RESET) && !prefix.isEmpty() && !isStringFormatOverride(prefix, format)) {
			prefix = format + prefix;
		}

		String prefixLastFormatString = ChatColor.getLastColors(prefix);
		String formatString = format.toString();
		if (
			(format != ChatColor.RESET) && (prefixLastFormatString.isEmpty() ||
			!formatString.equals(prefixLastFormatString))
		) {
			if (format.isColor()) {
				return LegacyChat.clampLegacyText(prefix, NFIX_LIMIT_1F) + formatString;
			} else {
				return LegacyChat.clampLegacyText(prefix, NFIX_LIMIT_2F) + ChatColor.RESET + formatString;
			}
		}
		return LegacyChat.clampLegacyText(prefix, NFIX_LIMIT);
	}

	protected String formatLegacyClampedSuffix() {
		String suffix = this.suffix.toLegacyText(clientCache.getLocale());

		if (!isStringFormatOverride(suffix, format)) {
			suffix = format + suffix;
			if (format.isFormat()) {
				suffix = ChatColor.RESET + suffix;
			}
		}

		return LegacyChat.clampLegacyText(suffix, NFIX_LIMIT);
	}

	protected Any<String, String> formatDisplayLinePrefixSuffix() {
		String prefix = this.prefix.toLegacyText(clientCache.getLocale());

		if ((format != ChatColor.RESET) && !isStringFormatOverride(prefix, format)) {
			prefix = format + prefix;
		}

		if (prefix.length() <= NFIX_LIMIT) {
			return new Any<>(prefix, "");
		}

		int limit = prefix.charAt(NFIX_LIMIT_FCUT) == ChatColor.COLOR_CHAR ? NFIX_LIMIT_FCUT : NFIX_LIMIT;

		String sPrefix = prefix.substring(0, limit);

		String sSuffix = ChatColor.getLastColors(sPrefix) + prefix.substring(limit);
		if (!isStringFormatOverride(sSuffix, ChatColor.RESET)) {
			sSuffix = ChatColor.RESET + sSuffix;
		}
		sSuffix = LegacyChat.clampLegacyText(sSuffix, NFIX_LIMIT);

		return new Any<>(sPrefix, sSuffix);
	}

	protected static boolean isStringFormatOverride(String string, ChatColor format) {
		if ((string.length() >= 2) && (string.charAt(0) == ChatColor.COLOR_CHAR)) {
			ChatColor formatStringColor = ChatColor.getByChar(string.charAt(1));
			if ((formatStringColor != null) && (formatStringColor.isColor() || (formatStringColor == format))) {
				return true;
			}
		}
		return false;
	}

}
