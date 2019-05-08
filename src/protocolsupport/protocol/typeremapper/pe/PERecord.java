package protocolsupport.protocol.typeremapper.pe;

import protocolsupport.api.TranslationAPI;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.Chat;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.i18n.I18NData;

public class PERecord {

	public static ClientBoundPacketData createPacket(String disc) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.CHAT);
		serializer.writeByte(Chat.TYPE_JUKEBOX_POPUP);
		StringSerializer.writeString(serializer, ProtocolVersionsHelper.LATEST_PE, " " + TranslationAPI.translate(I18NData.DEFAULT_LOCALE, "record.nowPlaying", TranslationAPI.getTranslationString(I18NData.DEFAULT_LOCALE, disc)));
		return serializer;
	}

	public static String getDiscName(int data) {
		switch (data) {
			case PESoundLevelEvent.RECORD_11:
				return "item.minecraft.music_disc_11.desc";
			case PESoundLevelEvent.RECORD_13:
				return "item.minecraft.music_disc_13.desc";
			case PESoundLevelEvent.RECORD_BLOCKS:
				return "item.minecraft.music_disc_blocks.desc";
			case PESoundLevelEvent.RECORD_CAT:
				return "item.minecraft.music_disc_cat.desc";
			case PESoundLevelEvent.RECORD_CHIRP:
				return "item.minecraft.music_disc_chirp.desc";
			case PESoundLevelEvent.RECORD_FAR:
				return "item.minecraft.music_disc_far.desc";
			case PESoundLevelEvent.RECORD_MALL:
				return "item.minecraft.music_disc_mall.desc";
			case PESoundLevelEvent.RECORD_MELLOHI:
				return "item.minecraft.music_disc_mellohi.desc";
			case PESoundLevelEvent.RECORD_STAL:
				return "item.minecraft.music_disc_stal.desc";
			case PESoundLevelEvent.RECORD_STRAD:
				return "item.minecraft.music_disc_strad.desc";
			case PESoundLevelEvent.RECORD_WAIT:
				return "item.minecraft.music_disc_wait.desc";
			case PESoundLevelEvent.RECORD_WARD:
				return "item.minecraft.music_disc_ward.desc";
		}
		throw new UnsupportedOperationException("Unsupported Record with id of " + data + ", disc remap in WorldEvent(PE) might be wrong.");
	}
}
