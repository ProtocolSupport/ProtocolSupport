package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_20;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.OptionalCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.chat.ChatCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleAdvancements;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV20;

public class Advancements extends MiddleAdvancements implements
IClientboundMiddlePacketV20 {

	public Advancements(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData advancementsPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_ADVANCEMENTS);
		advancementsPacket.writeBoolean(reset);
		ArrayCodec.writeVarIntTArray(advancementsPacket, advancementsMapping, (to, element) -> {
			StringCodec.writeVarIntUTF8String(to, element.getObj1());
			writeAdvanvement(to, version, cache.getClientCache().getLocale(), element.getObj2());
		});
		ArrayCodec.writeVarIntVarIntUTF8StringArray(advancementsPacket, removeAdvancements);
		ArrayCodec.writeVarIntTArray(advancementsPacket, advancementsProgress, (to, element) -> {
			StringCodec.writeVarIntUTF8String(to, element.getObj1());
			wrtieAdvancementProgress(to, element.getObj2());
		});
		io.writeClientbound(advancementsPacket);
	}

	protected static void writeAdvanvement(ByteBuf to, ProtocolVersion version, String locale, Advancement advancement) {
		OptionalCodec.writeOptionalVarIntUTF8String(to, advancement.parentId());
		OptionalCodec.writeOptional(to, advancement.display(), (displayTo, displayElement) -> writeAdvancementDisplay(displayTo, version, locale, displayElement));
		ArrayCodec.writeVarIntVarIntUTF8StringArray(to, advancement.criterias());
		ArrayCodec.writeVarIntTArray(to, advancement.requirments(), ArrayCodec::writeVarIntVarIntUTF8StringArray);
		to.writeBoolean(advancement.includeInTelementry());
	}

	protected static void writeAdvancementDisplay(ByteBuf to, ProtocolVersion version, String locale, AdvancementDisplay display) {
		StringCodec.writeVarIntUTF8String(to, ChatCodec.serialize(version, locale, display.title()));
		StringCodec.writeVarIntUTF8String(to, ChatCodec.serialize(version, locale, display.description()));
		ItemStackCodec.writeItemStack(to, version, locale, display.icon());
		MiscDataCodec.writeVarIntEnum(to, display.frametype());
		to.writeInt(display.flags());
		if ((display.flags() & AdvancementDisplay.flagHasBackgroundOffset) != 0) {
			StringCodec.writeVarIntUTF8String(to, display.background());
		}
		to.writeFloat(display.x());
		to.writeFloat(display.y());
	}


	protected void wrtieAdvancementProgress(ByteBuf to, AdvancementProgress obj2) {
		ArrayCodec.writeVarIntTArray(to, obj2.criterionsProgress(), (lTo, element) -> {
			StringCodec.writeVarIntUTF8String(lTo, element.getObj1());
			if (element.getObj2() != null) {
				lTo.writeBoolean(true);
				lTo.writeLong(element.getObj2());
			} else {
				lTo.writeBoolean(false);
			}
		});
	}

}
