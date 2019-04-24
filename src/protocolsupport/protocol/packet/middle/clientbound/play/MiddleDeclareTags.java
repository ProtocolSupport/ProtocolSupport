package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.StringSerializer;

public abstract class MiddleDeclareTags extends ClientBoundMiddlePacket {

	public MiddleDeclareTags(ConnectionImpl connection) {
		super(connection);
	}

	protected Tag[] blocks;
	protected Tag[] items;
	protected Tag[] fluids;
	protected Tag[] entities;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		blocks = readTags(serverdata);
		items = readTags(serverdata);
		fluids = readTags(serverdata);
		entities = readTags(serverdata);
	}

	protected static Tag[] readTags(ByteBuf from) {
		return ArraySerializer.readVarIntTArray(from, Tag.class, lFrom -> {
			return new Tag(StringSerializer.readVarIntUTF8String(lFrom), ArraySerializer.readVarIntVarIntArray(lFrom));
		});
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
