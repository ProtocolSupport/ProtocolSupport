package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_12r1_12r2_13_14r1_14r2_15_16r1_16r2_17r1_17r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.chat.ChatCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleAdvancements;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class Advancements extends MiddleAdvancements {

	public Advancements(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData advancements = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_ADVANCEMENTS);
		advancements.writeBoolean(reset);
		ArrayCodec.writeVarIntTArray(advancements, advancementsMapping, (to, element) -> {
			StringCodec.writeVarIntUTF8String(to, element.getObj1());
			writeAdvanvement(to, version, cache.getClientCache().getLocale(), element.getObj2());
		});
		ArrayCodec.writeVarIntVarIntUTF8StringArray(advancements, removeAdvancements);
		ArrayCodec.writeVarIntTArray(advancements, advancementsProgress, (to, element) -> {
			StringCodec.writeVarIntUTF8String(to, element.getObj1());
			wrtieAdvancementProgress(to, element.getObj2());
		});
		codec.writeClientbound(advancements);
	}

	protected static void writeAdvanvement(ByteBuf to, ProtocolVersion version, String locale, Advancement advancement) {
		if (advancement.parentId != null) {
			to.writeBoolean(true);
			StringCodec.writeVarIntUTF8String(to, advancement.parentId);
		} else {
			to.writeBoolean(false);
		}
		if (advancement.display != null) {
			to.writeBoolean(true);
			writeAdvancementDisplay(to, version, locale, advancement.display);
		} else {
			to.writeBoolean(false);
		}
		ArrayCodec.writeVarIntVarIntUTF8StringArray(to, advancement.criteria);
		ArrayCodec.writeVarIntTArray(to, advancement.requirements, ArrayCodec::writeVarIntVarIntUTF8StringArray);
	}

	protected static void writeAdvancementDisplay(ByteBuf to, ProtocolVersion version, String locale, AdvancementDisplay display) {
		StringCodec.writeVarIntUTF8String(to, ChatCodec.serialize(version, locale, display.title));
		StringCodec.writeVarIntUTF8String(to, ChatCodec.serialize(version, locale, display.description));
		ItemStackCodec.writeItemStack(to, version, locale, display.icon);
		MiscDataCodec.writeVarIntEnum(to, display.frametype);
		to.writeInt(display.flags);
		if ((display.flags & AdvancementDisplay.flagHasBackgroundOffset) != 0) {
			StringCodec.writeVarIntUTF8String(to, display.background);
		}
		to.writeFloat(display.x);
		to.writeFloat(display.y);
	}


	protected void wrtieAdvancementProgress(ByteBuf to, AdvancementProgress obj2) {
		ArrayCodec.writeVarIntTArray(to, obj2.criterionsProgress, (lTo, element) -> {
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
