package protocolsupport.listeners;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import protocolsupport.ProtocolSupport;
import protocolsupport.api.Connection;
import protocolsupport.api.ProtocolSupportAPI;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.InventoryOpen;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.minecraftdata.MinecraftData;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.protocol.utils.types.WindowType;
import protocolsupport.utils.netty.Allocator;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupportbuildprocessor.annotations.NeedsNoArgConstructor;

@SuppressWarnings("deprecation")
public class InternalPluginMessageRequest implements PluginMessageListener {

	private static final UUID uuid = UUID.randomUUID();

	public static final String TAG = "PS|IR";

	protected static final Map<String, Class<? extends PluginMessageData>> subchannelToClass = new HashMap<>();
	protected static final Map<Class<? extends PluginMessageData>, BiConsumer<Connection, PluginMessageData>> handlers = new HashMap<>();

	@SuppressWarnings("unchecked")
	protected static <T extends PluginMessageData> void register(Class<T> dataclass, BiConsumer<Connection, T> handler) {
		subchannelToClass.put(dataclass.getSimpleName(), dataclass);
		handlers.put(dataclass, (BiConsumer<Connection, PluginMessageData>) handler);
	}

	static {
		register(ChunkUpdateRequest.class, (connection, request) -> {
			Bukkit.getScheduler().runTask(ProtocolSupport.getInstance(), () -> {
				Chunk chunk = connection.getPlayer().getWorld().getChunkAt(request.getChunkX(), request.getChunkZ());
				if (chunk.isLoaded()) {
					connection.sendPacket(ServerPlatform.get().getPacketFactory().createUpdateChunkPacket(
							connection.getPlayer().getWorld().getChunkAt(request.getChunkX(), request.getChunkZ()))
					);
				}
			});
		});
		register(BlockUpdateRequest.class, (connection, request) -> {
			Bukkit.getScheduler().runTask(ProtocolSupport.getInstance(), () -> {
				Location place = request.getPosition().toBukkit(connection.getPlayer().getWorld());
				if (place.getChunk().isLoaded()) {
					Block block = place.getBlock();
					connection.sendPacket(ServerPlatform.get().getPacketFactory().createBlockUpdatePacket(
							request.getPosition(), MinecraftData.getBlockStateFromIdAndData(block.getTypeId(), block.getData()))
					);
				}
			});
		});
		register(InventoryOpenRequest.class, (connection, request) -> {
			Bukkit.getScheduler().runTaskLater(ProtocolSupport.getInstance(), () -> {
				InventoryOpen.sendInventoryOpen(connection, request.getWindowId(), request.getType(), request.getPosition(), request.getHorseId());
				connection.getPlayer().updateInventory();
			}, request.getDelayTicks());
		});
		register(InventoryUpdateRequest.class, (connection, request) -> {
			Bukkit.getScheduler().runTaskLater(ProtocolSupport.getInstance(), () -> {
					connection.getPlayer().updateInventory();
					connection.getPlayer().setItemOnCursor(connection.getPlayer().getItemOnCursor());
			}, request.getDelayTicks());
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

	public static class InventoryOpenRequest extends PluginMessageData {

		protected int windowId;
		protected WindowType type;
		protected Position position;
		protected int horseId;
		protected int delayTicks;

		public InventoryOpenRequest() {
			this(0, WindowType.PLAYER, new Position(0,0,0), 0, 0);
		}

		public InventoryOpenRequest(int windowId, WindowType type, Position position, int horseId, int delayTicks) {
			this.windowId = windowId;
			this.type = type;
			this.position = position;
			this.horseId = horseId;
			this.delayTicks = delayTicks;
		}

		@Override
		protected void read(ByteBuf from) {
			windowId = from.readInt();
			type = WindowType.getById(StringSerializer.readString(from, ProtocolVersionsHelper.LATEST_PC));
			PositionSerializer.readPositionTo(from, position);
			horseId = from.readInt();
			delayTicks = from.readInt();
		}

		@Override
		protected void write(ByteBuf to) {
			to.writeInt(windowId);
			StringSerializer.writeString(to, ProtocolVersionsHelper.LATEST_PC, type.getId());
			PositionSerializer.writePosition(to, position);
			to.writeInt(horseId);
			to.writeInt(delayTicks);
		}

		public int getWindowId() {
			return windowId;
		}

		public WindowType getType() {
			return type;
		}

		public Position getPosition() {
			return position;
		}

		public int getHorseId() {
			return horseId;
		}

		public int getDelayTicks() {
			return delayTicks;
		}

	}

	public static class InventoryUpdateRequest extends PluginMessageData {

		protected int delayTicks;

		public InventoryUpdateRequest() {
			this(0);
		}

		public InventoryUpdateRequest(int delayTicks) {
			this.delayTicks = delayTicks;
		}

		@Override
		protected void read(ByteBuf from) {
			this.delayTicks = from.readInt();
		}

		@Override
		protected void write(ByteBuf to) {
			to.writeInt(delayTicks);
		}

		public int getDelayTicks() {
			return delayTicks;
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
