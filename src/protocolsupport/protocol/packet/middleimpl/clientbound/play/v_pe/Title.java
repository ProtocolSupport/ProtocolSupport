package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleTitle;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class Title extends MiddleTitle {
	private static final int HIDE = 0;
	private static final int RESET = 1;
	private static final int SET_TITLE = 2;
	private static final int SET_SUBTITLE = 3;
	private static final int SET_ACTION_BAR = 4;
	private static final int SET_TIMINGS = 5;
	
	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.SET_TITLE, version);
		int action = 0;
		switch (this.action) {
		case HIDE:
			action = Title.HIDE;
			break;
		case RESET:
			action = Title.RESET;
			break;
		case SET_ACTION_BAR:
			action = Title.SET_ACTION_BAR;
			break;
		case SET_SUBTITLE:
			action = Title.SET_SUBTITLE;
			break;
		case SET_TIMES:
			action = Title.SET_TIMINGS;
			break;
		case SET_TITLE:
			action = Title.SET_TITLE;
			break;
		}
		VarNumberSerializer.writeSVarInt(serializer, action);
		String toBeSent = action == SET_SUBTITLE ? this.subtitleJson : this.titleJson;
		if (toBeSent == null) { toBeSent = ""; }; // toBeSent should never be null since we need to parse it later
		String legacyText = "";
		BaseComponent[] textComponent = ComponentSerializer.parse(toBeSent);
		for (BaseComponent bc : textComponent) {
			if (bc != null) {
				legacyText += bc.toLegacyText();
			}
		}
		StringSerializer.writeString(serializer, version, legacyText);
		VarNumberSerializer.writeSVarInt(serializer, this.fadeIn);
		VarNumberSerializer.writeSVarInt(serializer, this.stay);
		VarNumberSerializer.writeSVarInt(serializer, this.fadeOut);
		packets.add(serializer);
		return packets;
	}
}