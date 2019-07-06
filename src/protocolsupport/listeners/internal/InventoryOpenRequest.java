package protocolsupport.listeners.internal;

import java.util.function.BiConsumer;

import org.bukkit.Bukkit;

import io.netty.buffer.ByteBuf;
import protocolsupport.ProtocolSupport;
import protocolsupport.api.Connection;
import protocolsupport.listeners.InternalPluginMessageRequest.PluginMessageData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.InventoryOpen;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.WindowType;

public class InventoryOpenRequest extends PluginMessageData {

	public static final BiConsumer<Connection, InventoryOpenRequest> handler = (connection, request) -> {
		Bukkit.getScheduler().runTaskLater(ProtocolSupport.getInstance(), () -> {
			InventoryOpen.sendInventoryOpen(connection, request.getWindowId(), request.getType(), request.getPosition(), request.getHorseId());
			connection.getPlayer().updateInventory();
		}, request.getDelayTicks());
	};

	protected int windowId;
	protected WindowType type;
	protected Position position;
	protected int horseId;
	protected int delayTicks;

	public InventoryOpenRequest() {
		this(0, WindowType.PLAYER, new Position(0, 0, 0), 0, 0);
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
		type = WindowType.valueOf(StringSerializer.readString(from, ProtocolVersionsHelper.LATEST_PC));
		PositionSerializer.readPositionTo(from, position);
		horseId = from.readInt();
		delayTicks = from.readInt();
	}

	@Override
	protected void write(ByteBuf to) {
		to.writeInt(windowId);
		StringSerializer.writeString(to, ProtocolVersionsHelper.LATEST_PC, type.toString());
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