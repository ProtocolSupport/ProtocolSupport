package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_17r1_17r2;

import java.util.Map;

import org.bukkit.NamespacedKey;

import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_13_14r1_14r2_15_16r1_16r2_17r1_17r2.AbstractDeclareTags;

public class DeclareTags extends AbstractDeclareTags implements
IClientboundMiddlePacketV17r1,
IClientboundMiddlePacketV17r2 {

	public DeclareTags(IMiddlePacketInit init) {
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
		io.writeClientbound(declaretagsPacket);
	}

}
