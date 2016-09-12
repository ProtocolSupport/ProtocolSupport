package protocolsupport.api.chat.modifiers;

import org.bukkit.ChatColor;

public class Modifier {

	private ChatColor color;
	private Boolean bold;
	private Boolean italic;
	private Boolean underlined;
	private Boolean strikethrough;
	private Boolean obfuscated;

	public boolean isEmpty() {
		return color == null && bold == null && italic == null && underlined == null && strikethrough == null && obfuscated == null;
	}

	public void clear() {
		color = null;
		bold = null;
		italic = null;
		underlined = null;
		strikethrough = null;
		obfuscated = null;
	}

	public void set(ChatColor color) {
		if (color == null) {
			clear();
			return;
		}
		if (color.isColor()) {
			this.color = color;
		} else if (color.isFormat()) {
			switch (color) {
				case BOLD: {
					this.bold = true;
					break;
				}
				case ITALIC: {
					this.italic = true;
					break;
				}
				case STRIKETHROUGH: {
					this.strikethrough = true;
					break;
				}
				case UNDERLINE: {
					this.underlined = true;
					break;
				}
				case MAGIC: {
					this.obfuscated = true;
					break;
				}
				default: {
					break;
				}
			}
		}
		if (color == ChatColor.RESET) {
			this.color = ChatColor.WHITE;
			this.bold = false;
			this.italic = false;
			this.strikethrough = false;
			this.underlined = false;
			this.obfuscated = false;
		}
	}

	public boolean hasColor() {
		return color != null;
	}

	public ChatColor getColor() {
		return color;
	}

	public void setColor(ChatColor color) {
		if (color == null) {
			this.color = null;
			return;
		}
		if (color.isColor()) {
			this.color = color;
		}
	}

	public Boolean isBold() {
		return bold;
	}

	public void setBold(Boolean bold) {
		this.bold = bold;
	}

	public Boolean isItalic() {
		return italic;
	}

	public void setItalic(Boolean italic) {
		this.italic = italic;
	}

	public Boolean isUnderlined() {
		return underlined;
	}

	public void setUnderlined(Boolean underlined) {
		this.underlined = underlined;
	}

	public Boolean isStrikethrough() {
		return strikethrough;
	}

	public void setStrikethrough(Boolean strikethrough) {
		this.strikethrough = strikethrough;
	}

	public Boolean isRandom() {
		return obfuscated;
	}

	public void setRandom(Boolean random) {
		this.obfuscated = random;
	}

}
