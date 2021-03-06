package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_17r1_17r2;

import java.util.Map;

import org.bukkit.NamespacedKey;

import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13_14r1_14r2_15_16r1_16r2_17r1_17r2.AbstractDeclareTags;

public class DeclareTags extends AbstractDeclareTags {

	public DeclareTags(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData declaretagsPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_DECLARE_TAGS);
		VarNumberCodec.writeVarInt(declaretagsPacket, tagsMap.size());
		for (Map.Entry<NamespacedKey, Tag[]> tagsEntry : tagsMap.entrySet()) {
			NamespacedKey key = tagsEntry.getKey();
			StringCodec.writeVarIntUTF8String(declaretagsPacket, key.toString());
			if (key.equals(key_block)) {
				writeBlocksTags(declaretagsPacket, tagsEntry.getValue());
			} else if (key.equals(key_item)) {
				writeItemsTags(declaretagsPacket, tagsEntry.getValue());
			} else if (key.equals(key_entity_type)) {
				writeEntityTags(declaretagsPacket, tagsEntry.getValue());
			} else {
				writeTags(declaretagsPacket, tagsEntry.getValue());
			}
		}
		codec.writeClientbound(declaretagsPacket);
	}

}
