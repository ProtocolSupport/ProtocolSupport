package protocolsupport.protocol.pipeline.version;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import protocolsupport.api.Connection;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.NetworkDataCache;
import protocolsupport.protocol.utils.registry.MiddleTransformerRegistry;
import protocolsupport.utils.netty.Allocator;
import protocolsupport.utils.netty.MessageToMessageEncoder;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.network.NetworkState;

public abstract class AbstractPacketEncoder extends MessageToMessageEncoder<ByteBuf> {

	protected final Connection connection;
	protected final NetworkDataCache cache;

	public AbstractPacketEncoder(Connection connection, NetworkDataCache storage) {
		this.connection = connection;
		this.cache = storage;
		registry.setCallBack(object -> {
			object.setConnection(AbstractPacketEncoder.this.connection);
			object.setSharedStorage(AbstractPacketEncoder.this.cache);
		});
		varintPacketId = (connection.getVersion() != ProtocolVersion.MINECRAFT_PE) && connection.getVersion().isAfterOrEq(ProtocolVersion.MINECRAFT_1_7_5);
	}

	protected final MiddleTransformerRegistry<ClientBoundMiddlePacket> registry = new MiddleTransformerRegistry<>();

	private final boolean varintPacketId;

	@Override
	public void encode(ChannelHandlerContext ctx, ByteBuf input, List<Object> output) throws InstantiationException, IllegalAccessException  {
		if (!input.isReadable()) {
			return;
		}
		NetworkState currentProtocol = ServerPlatform.get().getMiscUtils().getNetworkStateFromChannel(ctx.channel());
		int packetId = VarNumberSerializer.readVarInt(input);
		ClientBoundMiddlePacket packetTransformer = registry.getTransformer(currentProtocol, packetId);
		packetTransformer.readFromServerData(input);
		if (packetTransformer.isValid()) {
			packetTransformer.handle();
			try (RecyclableCollection<ClientBoundPacketData> data = packetTransformer.toData(connection.getVersion())) {
				for (ClientBoundPacketData packetdata : data) {
					ByteBuf senddata = Allocator.allocateBuffer();
					int newPacketId = getNewPacketId(currentProtocol, packetdata.getPacketId());
					if (varintPacketId) {
						VarNumberSerializer.writeVarInt(senddata, newPacketId);
					} else {
						senddata.writeByte(newPacketId);
					}
					senddata.writeBytes(packetdata);
					output.add(senddata);
				}
			}
		}
	}

	protected abstract int getNewPacketId(NetworkState currentProtocol, int oldPacketId);

}
