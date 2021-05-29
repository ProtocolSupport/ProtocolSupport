package protocolsupport.protocol.utils;

import java.text.MessageFormat;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

import org.bukkit.ChatColor;
import org.bukkit.inventory.MainHand;

import protocolsupport.api.chat.ChatAPI.MessagePosition;

public class EnumConstantLookup<T extends Enum<T>> {

	public static final EnumConstantLookup<ChatColor> CHAT_COLOR = new EnumConstantLookup<>(ChatColor.class);
	public static final EnumConstantLookup<MessagePosition> MESSAGE_POSITION = new EnumConstantLookup<>(MessagePosition.class);
	public static final EnumConstantLookup<MainHand> MAIN_HAND = new EnumConstantLookup<>(MainHand.class);

	protected final T[] enumConstans;

	public EnumConstantLookup(Class<T> enumClass) {
		this.enumConstans = enumClass.getEnumConstants();
	}

	public @Nonnegative int getCount() {
		return enumConstans.length;
	}

	public @Nonnull T getByOrdinal(@Nonnegative int ordinal) {
		if ((ordinal >= 0) && (ordinal < enumConstans.length)) {
			return enumConstans[ordinal];
		} else {
			throw new IllegalArgumentException(MessageFormat.format("Invalid ordinal {0} for enum type {1}", ordinal, enumConstans.getClass().getComponentType()));
		}
	}

}