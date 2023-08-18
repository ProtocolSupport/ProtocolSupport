package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.utils.Any;
import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.OptionalCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleAdvancements.AdvancementDisplay.FrameType;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.utils.EnumConstantLookup;

public abstract class MiddleAdvancements extends ClientBoundMiddlePacket {

	protected MiddleAdvancements(IMiddlePacketInit init) {
		super(init);
	}

	protected boolean reset;
	protected Any<String, Advancement>[] advancementsMapping;
	protected String[] removeAdvancements;
	protected Any<String, AdvancementProgress>[] advancementsProgress;

	@SuppressWarnings("unchecked")
	@Override
	protected void decode(ByteBuf serverdata) {
		reset = serverdata.readBoolean();
		advancementsMapping = ArrayCodec.readVarIntTArray(
			serverdata, Any.class,
			buf -> new Any<>(StringCodec.readVarIntUTF8String(buf), readAdvancement(buf))
		);
		removeAdvancements = ArrayCodec.readVarIntVarIntUTF8StringArray(serverdata);
		advancementsProgress = ArrayCodec.readVarIntTArray(
			serverdata, Any.class,
			buf -> new Any<>(StringCodec.readVarIntUTF8String(buf), readAdvancementProgress(buf))
		);
	}

	protected static Advancement readAdvancement(ByteBuf from) {
		String parentId = OptionalCodec.readOptional(from, StringCodec::readVarIntUTF8String);
		AdvancementDisplay display = OptionalCodec.readOptional(from, MiddleAdvancements::readAdvancementDisplay);
		String[] criterias = ArrayCodec.readVarIntVarIntUTF8StringArray(from);
		String[][] requirments = ArrayCodec.readVarIntTArray(from, String[].class, ArrayCodec::readVarIntVarIntUTF8StringArray);
		boolean includeInTelementry = from.readBoolean();
		return new Advancement(parentId, display, criterias, requirments, includeInTelementry);
	}

	protected static AdvancementDisplay readAdvancementDisplay(ByteBuf from) {
		BaseComponent title = ChatAPI.fromJSON(StringCodec.readVarIntUTF8String(from), true);
		BaseComponent description = ChatAPI.fromJSON(StringCodec.readVarIntUTF8String(from), true);
		NetworkItemStack icon = ItemStackCodec.readItemStack(from);
		AdvancementDisplay.FrameType type = MiscDataCodec.readVarIntEnum(from, AdvancementDisplay.FrameType.CONSTANT_LOOKUP);
		int flags = from.readInt();
		String background = (flags & AdvancementDisplay.flagHasBackgroundOffset) != 0 ? StringCodec.readVarIntUTF8String(from) : null;
		float x = from.readFloat();
		float y = from.readFloat();
		return new AdvancementDisplay(title, description, icon, type, flags, background, x, y);
	}

	@SuppressWarnings("unchecked")
	protected static AdvancementProgress readAdvancementProgress(ByteBuf from) {
		return new AdvancementProgress(ArrayCodec.readVarIntTArray(
			from, Any.class,
			buf -> new Any<>(StringCodec.readVarIntUTF8String(from), buf.readBoolean() ? buf.readLong() : null)
		));
	}

	@Override
	protected void cleanup() {
		advancementsMapping = null;
		removeAdvancements = null;
		advancementsProgress = null;
	}

	protected static record Advancement(String parentId, AdvancementDisplay display, String[] criterias, String[][] requirments, boolean includeInTelementry) {
	}

	protected static record AdvancementDisplay(BaseComponent title, BaseComponent description, NetworkItemStack icon, FrameType frametype, int flags, String background, float x, float y) {

		public static final int flagHasBackgroundOffset = 0x1;

		public enum FrameType {
			TASK, CHALLENGE, GOAL;
			public static final EnumConstantLookup<FrameType> CONSTANT_LOOKUP = new EnumConstantLookup<>(FrameType.class);
		}

	}

	protected static record AdvancementProgress(Any<String, Long>[] criterionsProgress) {
	}

}
