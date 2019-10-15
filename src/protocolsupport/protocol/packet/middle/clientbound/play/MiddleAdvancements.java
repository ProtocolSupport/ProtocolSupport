package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.utils.Any;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.utils.EnumConstantLookups;

public abstract class MiddleAdvancements extends ClientBoundMiddlePacket {

	public MiddleAdvancements(ConnectionImpl connection) {
		super(connection);
	}

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
			buf -> new Any<>(StringSerializer.readVarIntUTF8String(buf), Advancement.read(buf))
		);
		removeAdvancements = ArraySerializer.readVarIntVarIntUTF8StringArray(serverdata);
		advancementsProgress = ArraySerializer.readVarIntTArray(
			serverdata, Any.class,
			buf -> new Any<>(StringSerializer.readVarIntUTF8String(buf), AdvancementProgress.read(buf))
		);
	}

	protected static class Advancement {

		protected static Advancement read(ByteBuf from) {
			String parentId = from.readBoolean() ? StringSerializer.readVarIntUTF8String(from) : null;
			AdvancementDisplay display = from.readBoolean() ? AdvancementDisplay.read(from) : null;
			String[] criterias = ArraySerializer.readVarIntVarIntUTF8StringArray(from);
			String[][] requirments = ArraySerializer.readVarIntTArray(from, String[].class, ArraySerializer::readVarIntVarIntUTF8StringArray);
			return new Advancement(parentId, display, criterias, requirments);
		}

		public final String parentId;
		public final AdvancementDisplay display;
		public final String[] criteria;
		public final String[][] requirements;
		public Advancement(String parentId, AdvancementDisplay display, String[] criterias, String[][] requirments) {
			this.parentId = parentId;
			this.display = display;
			this.criteria = criterias;
			this.requirements = requirments;
		}
	}

	protected static class AdvancementDisplay {

		public static final int flagHasBackgroundOffset = 0x1;

		protected static AdvancementDisplay read(ByteBuf from) {
			BaseComponent title = ChatAPI.fromJSON(StringSerializer.readVarIntUTF8String(from));
			BaseComponent description = ChatAPI.fromJSON(StringSerializer.readVarIntUTF8String(from));
			NetworkItemStack icon = ItemStackSerializer.readItemStack(from);
			FrameType type = MiscSerializer.readVarIntEnum(from, FrameType.CONSTANT_LOOKUP);
			int flags = from.readInt();
			String background = (flags & flagHasBackgroundOffset) != 0 ? StringSerializer.readVarIntUTF8String(from) : null;
			float x = from.readFloat();
			float y = from.readFloat();
			return new AdvancementDisplay(title, description, icon, type, flags, background, x, y);
		}

		public final BaseComponent title;
		public final BaseComponent description;
		public final NetworkItemStack icon;
		public final FrameType frametype;
		public final int flags;
		public final String background;
		public final float x;
		public final float y;
		public AdvancementDisplay(BaseComponent title, BaseComponent description, NetworkItemStack icon, FrameType frametype, int flags, String background, float x, float y) {
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
			TASK, CHALLENGE, GOAL;
			public static final EnumConstantLookups.EnumConstantLookup<FrameType> CONSTANT_LOOKUP = new EnumConstantLookups.EnumConstantLookup<>(FrameType.class);
		}
	}

	protected static class AdvancementProgress {

		@SuppressWarnings("unchecked")
		protected static AdvancementProgress read(ByteBuf from) {
			return new AdvancementProgress(ArraySerializer.readVarIntTArray(
				from, Any.class,
				buf -> new Any<>(StringSerializer.readVarIntUTF8String(from), buf.readBoolean() ? buf.readLong() : null)
			));
		}

		public final Any<String, Long>[] criterionsProgress;
		public AdvancementProgress(Any<String, Long>[] criterionsProgress) {
			this.criterionsProgress = criterionsProgress;
		}
	}

}
