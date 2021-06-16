package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_17;

import java.util.Map;

import org.bukkit.NamespacedKey;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13_14r1_14r2_15_16r1_16r2_17.AbstractDeclareTags;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class DeclareTags extends AbstractDeclareTags {

	public DeclareTags(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData declaretagsPacket = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_DECLARE_TAGS);
		VarNumberSerializer.writeVarInt(declaretagsPacket, tagsMap.size());
		for (Map.Entry<NamespacedKey, Tag[]> tagsEntry : tagsMap.entrySet()) {
			NamespacedKey key = tagsEntry.getKey();
			StringSerializer.writeVarIntUTF8String(declaretagsPacket, key.toString());
			if (key.equals(key_block)) {
				writeBlocksTags(declaretagsPacket, tagsEntry.getValue());
			} else if (key.equals(key_item)) {
				writeItemsTags(declaretagsPacket, tagsEntry.getValue());
			} else {
				writeTags(declaretagsPacket, tagsEntry.getValue());
			}
		}
		codec.writeClientbound(declaretagsPacket);
	}

}
