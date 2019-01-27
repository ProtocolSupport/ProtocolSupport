package protocolsupport.listeners;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;

import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import protocolsupport.api.Connection;
import protocolsupport.api.ProtocolSupportAPI;
import protocolsupport.listeners.internal.BlockUpdateRequest;
import protocolsupport.listeners.internal.ChunkUpdateRequest;
import protocolsupport.listeners.internal.InventoryOpenRequest;
import protocolsupport.listeners.internal.InventoryUpdateRequest;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.zplatform.ServerPlatform;

public class InternalPluginMessageRequest implements PluginMessageListener {

	public static final UUID uuid = UUID.randomUUID();
	public static final String TAG = "ps:ir";

	protected static final Map<String, Class<? extends PluginMessageData>> subchannelToClass = new HashMap<>();
	protected static final Map<Class<? extends PluginMessageData>, BiConsumer<Connection, PluginMessageData>> handlers = new HashMap<>();

	@SuppressWarnings("unchecked")
	protected static <T extends PluginMessageData> void register(Class<T> dataclass, BiConsumer<Connection, T> handler) {
		subchannelToClass.put(dataclass.getSimpleName(), dataclass);
		handlers.put(dataclass, (BiConsumer<Connection, PluginMessageData>) handler);
	}

	//Custom internal clientbound request names:
	public static final String PESkinUpdateSuffix = "skinupdate";
	public static final String PESkinUpdateChannel = "ps:" + PESkinUpdateSuffix;

	static {
		//Register (optional) request handlers. Handlers can and often contain code that needs bukkit/server access.
		register(ChunkUpdateRequest.class, ChunkUpdateRequest.handler);
		register(BlockUpdateRequest.class, BlockUpdateRequest.handler);
		register(InventoryOpenRequest.class, InventoryOpenRequest.handler);
		register(InventoryUpdateRequest.class, InventoryUpdateRequest.handler);
	}

	public static abstract class PluginMessageData {

		protected abstract void read(ByteBuf from);

		protected abstract void write(ByteBuf to);

	}

	public static ServerBoundPacketData newPluginMessageRequest(PluginMessageData data) {
		ByteBuf buf = Unpooled.buffer();
		try {
			MiscSerializer.writeUUID(buf, uuid);
			StringSerializer.writeString(buf, ProtocolVersionsHelper.LATEST_PC, (data.getClass().getSimpleName()));
			data.write(buf);
			return protocolsupport.protocol.packet.middle.serverbound.play.MiddleCustomPayload.create(TAG, buf);
		} finally {
			buf.release();
		}
	}

	public static void receivePluginMessageRequest(Connection connection, PluginMessageData data) {
		ByteBuf buf = Unpooled.buffer();
		try {
			MiscSerializer.writeUUID(buf, uuid);
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
