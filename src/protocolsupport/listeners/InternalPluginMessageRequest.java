package protocolsupport.listeners;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import protocolsupport.api.Connection;
import protocolsupport.api.ProtocolSupportAPI;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.utils.netty.Allocator;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupportbuildprocessor.annotations.NeedsNoArgConstructor;

public class InternalPluginMessageRequest implements PluginMessageListener {

	private static final UUID uuid = UUID.randomUUID();

	public static final String TAG = "protocolsupport:ir";

	protected static final Map<String, Class<? extends PluginMessageData>> subchannelToClass = new HashMap<>();
	protected static final Map<Class<? extends PluginMessageData>, BiConsumer<Connection, PluginMessageData>> handlers = new HashMap<>();

	@SuppressWarnings("unchecked")
	protected static <T extends PluginMessageData> void register(Class<T> dataclass, BiConsumer<Connection, T> handler) {
		subchannelToClass.put(dataclass.getSimpleName(), dataclass);
		handlers.put(dataclass, (BiConsumer<Connection, PluginMessageData>) handler);
	}

	static {
		register(ChunkUpdateRequest.class, (connection, request) -> {
			connection.sendPacket(ServerPlatform.get().getPacketFactory().createUpdateChunkPacket(
					connection.getPlayer().getWorld().getChunkAt(request.getChunkX(), request.getChunkZ()))
			);
		});
		register(BlockUpdateRequest.class, (connection, request) -> {
			Block block = request.getPosition().toBukkit(connection.getPlayer().getWorld()).getBlock();
			connection.sendPacket(ServerPlatform.get().getPacketFactory().createBlockUpdatePacket(
					request.getPosition(), ServerPlatform.get().getMiscUtils().getBlockDataNetworkId(block.getBlockData()))
			);
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

	public static class BlockUpdateRequest extends PluginMessageData {

		protected Position position;

		public BlockUpdateRequest() {
			this(new Position(0,0,0));
		}

		public BlockUpdateRequest(Position position) {
			this.position = position;
		}

		@Override
		protected void read(ByteBuf from) {
			PositionSerializer.readPositionTo(from, position);
		}

		@Override
		protected void write(ByteBuf to) {
			PositionSerializer.writePosition(to, position);
		}

		public Position getPosition() {
			return position;
		}

	}

	public static void receivePluginMessageRequest(Connection connection, PluginMessageData data) {
		ByteBuf buf = Allocator.allocateBuffer();
		try {
			MiscSerializer.writeUUID(buf, ProtocolVersionsHelper.LATEST_PC, uuid);
			StringSerializer.writeString(buf, ProtocolVersionsHelper.LATEST_PC, (data.getClass().getSimpleName()));
			data.write(buf);
			connection.receivePacket(ServerPlatform.get().getPacketFactory().createInboundPluginMessagePacket(TAG, MiscSerializer.readAllBytes(buf)));
		} finally {
			buf.release();
		}
	}

	@Override
	public void onPluginMessageReceived(String tag, Player player, byte[] data) {
		Connection connection = ProtocolSupportAPI.getConnection(player);
		if (connection == null) {
			return;
		}
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
			handlers.get(messagedatacl).accept(connection, messagedata);
		} catch (InstantiationException | IllegalAccessException e) {
			if (ServerPlatform.get().getMiscUtils().isDebugging()) {
				System.err.println("Exception occured while processing internal plugin message");
				e.printStackTrace();
			}
		}
	}

}
