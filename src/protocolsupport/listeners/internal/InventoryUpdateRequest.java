package protocolsupport.listeners.internal;

import java.util.function.BiConsumer;

import org.bukkit.Bukkit;

import io.netty.buffer.ByteBuf;
import protocolsupport.ProtocolSupport;
import protocolsupport.api.Connection;
import protocolsupport.listeners.InternalPluginMessageRequest.PluginMessageData;

public class InventoryUpdateRequest extends PluginMessageData {

	public static final BiConsumer<Connection, InventoryUpdateRequest> handler = (connection, request) -> {
		Bukkit.getScheduler().runTaskLater(ProtocolSupport.getInstance(), () -> {
			connection.getPlayer().updateInventory();
			connection.getPlayer().setItemOnCursor(connection.getPlayer().getItemOnCursor());
		}, request.getDelayTicks());
	};

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