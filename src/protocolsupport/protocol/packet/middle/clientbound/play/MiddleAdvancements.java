package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.utils.Any;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;

public abstract class MiddleAdvancements extends ClientBoundMiddlePacket {

	protected boolean reset;
	protected Any<String, Advancement>[] advancementsMapping;
	protected String[] removeAdvancements;
	protected Any<String, AdvancementProgress>[] advancementsProgress;

	@SuppressWarnings("unchecked")
	@Override
	public void readFromServerData(ByteBuf serverdata) {
		reset = serverdata.readBoolean();
		advancementsMapping = ArraySerializer.readVarIntTArray(
			serverdata, Any.class,
			buf -> new Any<String, Advancement>(StringSerializer.readString(buf, ProtocolVersionsHelper.LATEST_PC), Advancement.read(buf, cache.getLocale()))
		);
		removeAdvancements = ArraySerializer.readVarIntStringArray(serverdata, ProtocolVersionsHelper.LATEST_PC);
		advancementsProgress = ArraySerializer.readVarIntTArray(
			serverdata, Any.class,
			buf -> new Any<String, AdvancementProgress>(StringSerializer.readString(buf, ProtocolVersionsHelper.LATEST_PC), AdvancementProgress.read(buf))
		);
	}

	protected static class Advancement {

		protected static Advancement read(ByteBuf from, String locale) {
			String parentId = from.readBoolean() ? StringSerializer.readString(from, ProtocolVersionsHelper.LATEST_PC) : null;
			AdvancementDisplay display = from.readBoolean() ? AdvancementDisplay.read(from, locale) : null;
			String[] criterias = ArraySerializer.readVarIntStringArray(from, ProtocolVersionsHelper.LATEST_PC);
			String[][] requirments = ArraySerializer.readVarIntTArray(from, String[].class, buf -> ArraySerializer.readVarIntStringArray(buf, ProtocolVersionsHelper.LATEST_PC));
			return new Advancement(parentId, display, criterias, requirments);
		}

		public final String parentId;
		public final AdvancementDisplay display;
		public final String[] criterias;
		public final String[][] requirements;
		public Advancement(String parentId, AdvancementDisplay display, String[] criterias, String[][] requirments) {
			this.parentId = parentId;
			this.display = display;
			this.criterias = criterias;
			this.requirements = requirments;
		}
	}

	protected static class AdvancementDisplay {

		public static final int flagHasBackgroundOffset = 0x1;

		protected static AdvancementDisplay read(ByteBuf from, String locale) {
			BaseComponent title = ChatAPI.fromJSON(StringSerializer.readString(from, ProtocolVersionsHelper.LATEST_PC));
			BaseComponent description = ChatAPI.fromJSON(StringSerializer.readString(from, ProtocolVersionsHelper.LATEST_PC));
			ItemStackWrapper icon = ItemStackSerializer.readItemStack(from, ProtocolVersionsHelper.LATEST_PC, locale, false);
			FrameType type = MiscSerializer.readEnum(from, FrameType.class);
			int flags = from.readInt();
			String background = (flags & flagHasBackgroundOffset) != 0 ? StringSerializer.readString(from, ProtocolVersionsHelper.LATEST_PC) : null;
			float x = from.readFloat();
			float y = from.readFloat();
			return new AdvancementDisplay(title, description, icon, type, flags, background, x, y);
		}

		public final BaseComponent title;
		public final BaseComponent description;
		public final ItemStackWrapper icon;
		public final FrameType frametype;
		public final int flags;
		public final String background;
		public final float x;
		public final float y;
		public AdvancementDisplay(BaseComponent title, BaseComponent description, ItemStackWrapper icon, FrameType frametype, int flags, String background, float x, float y) {
			this.title = title;
			this.description = description;
			this.icon = icon;
			this.frametype = frametype;
			this.flags = flags;
			this.background = background;
			this.x = x;
			this.y = y;
		}

		protected static enum FrameType {
			TASK, CHALLENGE, GOAL
		}
	}

	protected static class AdvancementProgress {

		@SuppressWarnings("unchecked")
		protected static AdvancementProgress read(ByteBuf from) {
			return new AdvancementProgress(ArraySerializer.readVarIntTArray(
				from, Any.class,
				buf -> new Any<String, Long>(StringSerializer.readString(from, ProtocolVersionsHelper.LATEST_PC), buf.readBoolean() ? buf.readLong() : null)
			));
		}

		public final Any<String, Long>[] criterionsProgress;
		public AdvancementProgress(Any<String, Long>[] criterionsProgress) {
			this.criterionsProgress = criterionsProgress;
		}
	}

}
