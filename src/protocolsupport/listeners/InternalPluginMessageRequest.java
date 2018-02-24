package protocolsupport.listeners;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;

import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import protocolsupport.api.Connection;
import protocolsupport.api.ProtocolSupportAPI;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.utils.netty.Allocator;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupportbuildprocessor.annotations.NeedsNoArgConstructor;

public class InternalPluginMessageRequest implements PluginMessageListener {

	private static final UUID uuid = UUID.randomUUID();

	public static final String TAG = "PS|IR";

	protected static final String SUBCHANNEL_CHUNK_UPDATE_REQUEST = "ChunkUpdate";

	protected static final Map<String, Class<? extends PluginMessageData>> subchannelToClass = new HashMap<>();
	protected static final Map<Class<? extends PluginMessageData>, String> classToSubchannel = new HashMap<>();
	protected static final Map<Class<? extends PluginMessageData>, BiConsumer<Player, PluginMessageData>> handlers = new HashMap<>();

	@SuppressWarnings("unchecked")
	protected static <T extends PluginMessageData> void register(String subchannel, Class<T> dataclass, BiConsumer<Player, T> handler) {
		subchannelToClass.put(subchannel, dataclass);
		classToSubchannel.put(dataclass, subchannel);
		handlers.put(dataclass, (BiConsumer<Player, PluginMessageData>) handler);
	}

	static {
		register(SUBCHANNEL_CHUNK_UPDATE_REQUEST, ChunkUpdateRequest.class, (player, request) -> {
			Connection connection = ProtocolSupportAPI.getConnection(player);
			if (connection != null) {
				Chunk chunk = player.getWorld().getChunkAt(request.getChunkX(), request.getChunkZ());
				connection.sendPacket(ServerPlatform.get().getPacketFactory().createUpdateChunkPacket(chunk));
			}
		});
	}

	@NeedsNoArgConstructor
	public static abstract class PluginMessageData {

		protected abstract void read(ByteBuf from);

		protected abstract void write(ByteBuf to);

	}

	public static class ChunkUpdateRequest extends PluginMessageData {

		protected int chunkX;
		protected int chunkZ;

		public ChunkUpdateRequest() {
			this(0, 0);
		}

		public ChunkUpdateRequest(int chunkX, int chunkZ) {
			this.chunkX = chunkX;
			this.chunkZ = chunkZ;
		}

		@Override
		protected void read(ByteBuf from) {
			this.chunkX = from.readInt();
			this.chunkZ = from.readInt();
		}

		@Override
		protected void write(ByteBuf to) {
			to.writeInt(this.chunkX);
			to.writeInt(this.chunkZ);
		}

		public int getChunkX() {
			return chunkX;
		}

		public int getChunkZ() {
			return chunkZ;
		}

	}

	public static void receivePluginMessageRequest(Connection connection, PluginMessageData data) {
		ByteBuf buf = Allocator.allocateBuffer();
		try {
			MiscSerializer.writeUUID(buf, ProtocolVersionsHelper.LATEST_PC, uuid);
			StringSerializer.writeString(buf, ProtocolVersionsHelper.LATEST_PC, classToSubchannel.get(data.getClass()));
			data.write(buf);
			connection.receivePacket(ServerPlatform.get().getPacketFactory().createInboundPluginMessagePacket(TAG, MiscSerializer.readAllBytes(buf)));
		} finally {
			buf.release();
		}
	}

	@Override
	public void onPluginMessageReceived(String tag, Player player, byte[] data) {
		ByteBuf buf = Unpooled.wrappedBuffer(data);
		UUID luuid = MiscSerializer.readUUID(buf);
		if (!luuid.equals(uuid)) {
			return;
		}
		String subchannel = StringSerializer.readString(buf, ProtocolVersionsHelper.LATEST_PC);
		Class<? extends PluginMessageData> messagedatacl = subchannelToClass.get(subchannel);
		if (messagedatacl == null) {
			return;
		}
		try {
			PluginMessageData messagedata = messagedatacl.newInstance();
			messagedata.read(buf);
			handlers.get(messagedatacl).accept(player, messagedata);
		} catch (InstantiationException | IllegalAccessException e) {
			if (ServerPlatform.get().getMiscUtils().isDebugging()) {
				System.err.println("Exception occured while processing internal plugin message");
				e.printStackTrace();
			}
		}
	}

}
