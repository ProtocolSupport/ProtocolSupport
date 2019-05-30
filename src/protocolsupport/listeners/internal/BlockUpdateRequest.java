package protocolsupport.listeners.internal;

import java.util.function.BiConsumer;

import org.bukkit.block.Block;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.Connection;
import protocolsupport.listeners.InternalPluginMessageRequest.PluginMessageData;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.types.Position;
import protocolsupport.zplatform.ServerPlatform;

public class BlockUpdateRequest extends PluginMessageData {

	public static final BiConsumer<Connection, BlockUpdateRequest> handler = (connection, request) -> {
		Block block = request.getPosition().toBukkit(connection.getPlayer().getWorld()).getBlock();
		connection.sendPacket(ServerPlatform.get().getPacketFactory().createBlockUpdatePacket(
			request.getPosition(), ServerPlatform.get().getMiscUtils().getBlockDataNetworkId(block.getBlockData()))
		);
	};

	protected Position position;

	public BlockUpdateRequest() {
		this(new Position(0, 0, 0));
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