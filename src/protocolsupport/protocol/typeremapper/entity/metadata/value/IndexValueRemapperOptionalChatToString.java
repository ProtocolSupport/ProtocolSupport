package protocolsupport.protocol.typeremapper.entity.metadata.value;

import org.bukkit.ChatColor;

import protocolsupport.protocol.typeremapper.legacy.chat.LegacyChat;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectOptionalChat;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectString;

public class IndexValueRemapperOptionalChatToString extends IndexValueRemapper<DataWatcherObjectOptionalChat> {

	protected final int limit;

	public IndexValueRemapperOptionalChatToString(DataWatcherObjectIndex<DataWatcherObjectOptionalChat> fromIndex, int toIndex, int limit) {
		super(fromIndex, toIndex);
		this.limit = limit;
	}

	@Override
	public DataWatcherObject<?> remapValue(DataWatcherObjectOptionalChat object) {
		//TODO: pass locale
		if (object.getValue() != null) {
			String text = LegacyChat.clampLegacyText(object.getValue().toLegacyText(), limit);
			return new DataWatcherObjectString(!text.isEmpty() ? text : ChatColor.BLACK.toString());
		} else {
			return new DataWatcherObjectString("");
		}
	}

}
