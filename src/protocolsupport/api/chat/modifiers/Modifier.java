package protocolsupport.api.chat.modifiers;

import protocolsupport.api.chat.ChatColor;
import protocolsupport.utils.Utils;

public class Modifier {

	private ChatColor color;
	private String font;
	private Boolean bold;
	private Boolean italic;
	private Boolean underlined;
	private Boolean strikethrough;
	private Boolean obfuscated;

	public boolean isEmpty() {
		return (color == null) && (font == null) && (bold == null) && (italic == null) && (underlined == null) && (strikethrough == null) && (obfuscated == null);
	}

	public void clear() {
		color = null;
		font = null;
		bold = null;
		italic = null;
		underlined = null;
		strikethrough = null;
		obfuscated = null;
	}

	public boolean hasFont() {
		return font != null;
	}

	public String getFont() {
		return font;
	}

	public void setFont(String font) {
		this.font = font;
	}

	public Modifier withFont() {
		setFont(font);
		return this;
	}

	public boolean hasColor() {
		return color != null;
	}

	public ChatColor getRGBColor() {
		return color;
	}

	public void setRGBColor(ChatColor color) {
		this.color = color;
	}

	public Modifier withRGBColor(ChatColor color) {
		setRGBColor(color);
		return this;
	}

	public Boolean isBold() {
		return bold;
	}

	public void setBold(Boolean bold) {
		this.bold = bold;
	}

	public Modifier withBold(Boolean bold) {
		setBold(bold);
		return this;
	}

	public Boolean isItalic() {
		return italic;
	}

	public void setItalic(Boolean italic) {
		this.italic = italic;
	}

	public Modifier withItalic(Boolean italic) {
		setItalic(italic);
		return this;
	}

	public Boolean isUnderlined() {
		return underlined;
	}

	public void setUnderlined(Boolean underlined) {
		this.underlined = underlined;
	}

	public Modifier withUnderlined(Boolean underlined) {
		setUnderlined(underlined);
		return this;
	}

	public Boolean isStrikethrough() {
		return strikethrough;
	}

	public void setStrikethrough(Boolean strikethrough) {
		this.strikethrough = strikethrough;
	}

	public Modifier withStrikethrough(Boolean strikethrough) {
		setStrikethrough(strikethrough);
		return this;
	}

	public Boolean isRandom() {
		return obfuscated;
	}

	public void setRandom(Boolean random) {
		this.obfuscated = random;
	}

	public Modifier withRandom(Boolean random) {
		setRandom(random);
		return this;
	}

	@Override
	public String toString() {
		return Utils.toStringAllFields(this);
	}


	@Deprecated
	public void set(org.bukkit.ChatColor format) {
		if ((format == null) || (format == org.bukkit.ChatColor.RESET)) {
			clear();
			return;
		}
		if (format.isColor()) {
			this.color = ChatColor.ofBukkit(format);
		} else if (format.isFormat()) {
			switch (format) {
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
	}

	@Deprecated
	public Modifier with(org.bukkit.ChatColor format) {
		set(format);
		return this;
	}

	@Deprecated
	public org.bukkit.ChatColor getColor() {
		return color.asBukkit();
	}

	@Deprecated
	public void setColor(org.bukkit.ChatColor color) {
		if (color == null) {
			this.color = null;
			return;
		}
		if (color.isColor()) {
			this.color = ChatColor.ofBukkit(color);
		}
	}

	@Deprecated
	public Modifier withColor(org.bukkit.ChatColor color) {
		setColor(color);
		return this;
	}

}
