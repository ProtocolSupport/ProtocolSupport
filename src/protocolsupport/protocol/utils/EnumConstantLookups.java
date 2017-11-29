package protocolsupport.protocol.utils;

import org.bukkit.inventory.MainHand;

import protocolsupport.api.chat.ChatAPI.MessagePosition;

public class EnumConstantLookups {

	public static class EnumConstantLookup<T extends Enum<T>> {
		private final T[] enumConstans;
		public EnumConstantLookup(Class<T> enumClass) {
			this.enumConstans = enumClass.getEnumConstants();
		}
		public T getByOrdinal(int ordinal) {
			return enumConstans[ordinal];
		}
	}

	public static final EnumConstantLookups.EnumConstantLookup<MainHand> MAIN_HAND = new EnumConstantLookups.EnumConstantLookup<>(MainHand.class);

	public static final EnumConstantLookups.EnumConstantLookup<MessagePosition> MESSAGE_POSITION = new EnumConstantLookups.EnumConstantLookup<>(MessagePosition.class);

}
