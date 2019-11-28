package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_12r1_12r2_13_14r1_14r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleAdvancements;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.typeremapper.legacy.chat.LegacyChatJson;

public class Advancements extends MiddleAdvancements {

	public Advancements(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData advancements = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_ADVANCEMENTS);
		advancements.writeBoolean(reset);
		ArraySerializer.writeVarIntTArray(advancements, advancementsMapping, (to, element) -> {
			StringSerializer.writeVarIntUTF8String(to, element.getObj1());
			writeAdvanvement(to, version, cache.getAttributesCache().getLocale(), element.getObj2());
		});
		ArraySerializer.writeVarIntVarIntUTF8StringArray(advancements, removeAdvancements);
		ArraySerializer.writeVarIntTArray(advancements, advancementsProgress, (to, element) -> {
			StringSerializer.writeVarIntUTF8String(to, element.getObj1());
			wrtieAdvancementProgress(to, element.getObj2());
		});
		codec.write(advancements);
	}

	protected static void writeAdvanvement(ByteBuf to, ProtocolVersion version, String locale, Advancement advancement) {
		if (advancement.parentId != null) {
			to.writeBoolean(true);
			StringSerializer.writeVarIntUTF8String(to, advancement.parentId);
		} else {
			to.writeBoolean(false);
		}
		if (advancement.display != null) {
			to.writeBoolean(true);
			writeAdvancementDisplay(to, version, locale, advancement.display);
		} else {
			to.writeBoolean(false);
		}
		ArraySerializer.writeVarIntVarIntUTF8StringArray(to, advancement.criteria);
		ArraySerializer.writeVarIntTArray(to, advancement.requirements, ArraySerializer::writeVarIntVarIntUTF8StringArray);
	}

	protected static void writeAdvancementDisplay(ByteBuf to, ProtocolVersion version, String locale, AdvancementDisplay display) {
		StringSerializer.writeVarIntUTF8String(to, ChatAPI.toJSON(LegacyChatJson.convert(version, locale, display.title)));
		StringSerializer.writeVarIntUTF8String(to, ChatAPI.toJSON(LegacyChatJson.convert(version, locale, display.description)));
		ItemStackSerializer.writeItemStack(to, version, locale, display.icon);
		MiscSerializer.writeVarIntEnum(to, display.frametype);
		to.writeInt(display.flags);
		if ((display.flags & AdvancementDisplay.flagHasBackgroundOffset) != 0) {
			StringSerializer.writeVarIntUTF8String(to, display.background);
		}
		to.writeFloat(display.x);
		to.writeFloat(display.y);
	}


	protected void wrtieAdvancementProgress(ByteBuf to, AdvancementProgress obj2) {
		ArraySerializer.writeVarIntTArray(to, obj2.criterionsProgress, (lTo, element) -> {
			StringSerializer.writeVarIntUTF8String(lTo, element.getObj1());
			if (element.getObj2() != null) {
				lTo.writeBoolean(true);
				lTo.writeLong(element.getObj2());
			} else {
				lTo.writeBoolean(false);
			}
		});
	}

}
