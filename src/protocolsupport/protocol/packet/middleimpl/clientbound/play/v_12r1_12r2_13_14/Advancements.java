package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_12r1_12r2_13_14;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleAdvancements;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.typeremapper.legacy.chat.LegacyChatJson;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class Advancements extends MiddleAdvancements {

	public Advancements(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_ADVANCEMENTS);
		serializer.writeBoolean(reset);
		ArraySerializer.writeVarIntTArray(serializer, advancementsMapping, (to, element) -> {
			StringSerializer.writeString(to, version, element.getObj1());
			writeAdvanvement(to, version, cache.getAttributesCache().getLocale(), element.getObj2());
		});
		ArraySerializer.writeVarIntStringArray(serializer, version, removeAdvancements);
		ArraySerializer.writeVarIntTArray(serializer, advancementsProgress, (to, element) -> {
			StringSerializer.writeString(to, version, element.getObj1());
			wrtieAdvancementProgress(to, version, element.getObj2());
		});
		return RecyclableSingletonList.create(serializer);
	}

	protected static void writeAdvanvement(ByteBuf to, ProtocolVersion version, String locale, Advancement advancement) {
		if (advancement.parentId != null) {
			to.writeBoolean(true);
			StringSerializer.writeString(to, version, advancement.parentId);
		} else {
			to.writeBoolean(false);
		}
		if (advancement.display != null) {
			to.writeBoolean(true);
			writeAdvancementDisplay(to, version, locale, advancement.display);
		} else {
			to.writeBoolean(false);
		}
		ArraySerializer.writeVarIntStringArray(to, version, advancement.criteria);
		ArraySerializer.writeVarIntTArray(to, advancement.requirements, (buf, element) -> ArraySerializer.writeVarIntStringArray(to, version, element));
	}

	protected static void writeAdvancementDisplay(ByteBuf to, ProtocolVersion version, String locale, AdvancementDisplay display) {
		StringSerializer.writeString(to, version, ChatAPI.toJSON(LegacyChatJson.convert(version, locale, display.title)));
		StringSerializer.writeString(to, version, ChatAPI.toJSON(LegacyChatJson.convert(version, locale, display.description)));
		ItemStackSerializer.writeItemStack(to, version, locale, display.icon);
		MiscSerializer.writeVarIntEnum(to, display.frametype);
		to.writeInt(display.flags);
		if ((display.flags & AdvancementDisplay.flagHasBackgroundOffset) != 0) {
			StringSerializer.writeString(to, version, display.background);
		}
		to.writeFloat(display.x);
		to.writeFloat(display.y);
	}


	protected void wrtieAdvancementProgress(ByteBuf to, ProtocolVersion version, AdvancementProgress obj2) {
		ArraySerializer.writeVarIntTArray(to, obj2.criterionsProgress, (toi, element) -> {
			StringSerializer.writeString(toi, version, element.getObj1());
			if (element.getObj2() != null) {
				toi.writeBoolean(true);
				toi.writeLong(element.getObj2());
			} else {
				toi.writeBoolean(false);
			}
		});
	}

}
