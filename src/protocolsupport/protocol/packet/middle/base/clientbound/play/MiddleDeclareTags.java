package protocolsupport.protocol.packet.middle.base.clientbound.play;

import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.NamespacedKey;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;
import protocolsupport.protocol.utils.NamespacedKeyUtils;

public abstract class MiddleDeclareTags extends ClientBoundMiddlePacket {

	protected MiddleDeclareTags(IMiddlePacketInit init) {
		super(init);
	}

	protected static final NamespacedKey key_block = NamespacedKey.minecraft("block");
	protected static final NamespacedKey key_item = NamespacedKey.minecraft("item");
	protected static final NamespacedKey key_entity_type = NamespacedKey.minecraft("entity_type");
	protected static final NamespacedKey key_fluid = NamespacedKey.minecraft("fluid");
	protected static final NamespacedKey key_game_event = NamespacedKey.minecraft("game_event");

	protected Map<NamespacedKey, Tag[]> tagsMap;

	@Override
	protected void decode(ByteBuf serverdata) {
		tagsMap = new LinkedHashMap<>();
		int count = VarNumberCodec.readVarInt(serverdata);
		for (int index = 0; index < count; index++) {
			NamespacedKey key = NamespacedKeyUtils.fromString(StringCodec.readVarIntUTF8String(serverdata));
			Tag[] tags = ArrayCodec.readVarIntTArray(serverdata, Tag.class, tagFrom -> {
				return new Tag(StringCodec.readVarIntUTF8String(tagFrom), ArrayCodec.readVarIntVarIntArray(tagFrom));
			});
			tagsMap.put(key, tags);
		}
	}

	@Override
	protected void cleanup() {
		tagsMap = null;
	}

	protected static class Tag {

		protected final String tagId;
		protected final int[] taggedIds;

		public Tag(String tagId, int[] taggedIds) {
			this.tagId = tagId;
			this.taggedIds = taggedIds;
		}

		public String getTagId() {
			return tagId;
		}

		public int[] getTaggedIds() {
			return taggedIds;
		}

	}

}
