package protocolsupport.protocol.pipeline.version;

import java.io.IOException;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.server.v1_10_R1.EnumProtocol;
import net.minecraft.server.v1_10_R1.EnumProtocolDirection;
import net.minecraft.server.v1_10_R1.Packet;
import net.minecraft.server.v1_10_R1.PacketListener;
import protocolsupport.api.Connection;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.PacketData;
import protocolsupport.protocol.pipeline.IPacketEncoder;
import protocolsupport.protocol.serializer.ChainedProtocolSupportPacketDataSerializer;
import protocolsupport.protocol.storage.NetworkDataCache;
import protocolsupport.protocol.utils.registry.MiddleTransformerRegistry;
import protocolsupport.protocol.utils.registry.MiddleTransformerRegistry.InitCallBack;
import protocolsupport.protocol.utils.registry.PacketIdTransformerRegistry;
import protocolsupport.utils.netty.Allocator;
import protocolsupport.utils.netty.ChannelUtils;
import protocolsupport.utils.recyclable.RecyclableCollection;

public abstract class AbstractPacketEncoder implements IPacketEncoder {

	protected final Connection connection;
	protected final NetworkDataCache cache;
	public AbstractPacketEncoder(Connection connection, NetworkDataCache storage) {
		this.connection = connection;
		this.cache = storage;
		registry.setCallBack(new InitCallBack<ClientBoundMiddlePacket<RecyclableCollection<PacketData>>>() {
			@Override
			public void onInit(ClientBoundMiddlePacket<RecyclableCollection<PacketData>> object) {
				object.setConnection(AbstractPacketEncoder.this.connection);
				object.setSharedStorage(AbstractPacketEncoder.this.cache);
			}
		});
		varintPacketId = connection.getVersion().isAfterOrEq(ProtocolVersion.MINECRAFT_1_7_5);
	}

	protected final MiddleTransformerRegistry<ClientBoundMiddlePacket<RecyclableCollection<PacketData>>> registry = new MiddleTransformerRegistry<>();
	protected static final PacketIdTransformerRegistry packetIdRegistry = new PacketIdTransformerRegistry();

	private final ChainedProtocolSupportPacketDataSerializer middlebuffer = new ChainedProtocolSupportPacketDataSerializer();
	private final boolean varintPacketId;

	@Override
	public void encode(ChannelHandlerContext ctx, Packet<PacketListener> packet, ByteBuf output) throws Exception {
		Channel channel = ctx.channel();
		EnumProtocol currentProtocol = channel.attr(ChannelUtils.CURRENT_PROTOCOL_KEY).get();
		final Integer packetId = currentProtocol.a(EnumProtocolDirection.CLIENTBOUND, packet);
		if (packetId == null) {
			throw new IOException("Can't serialize unregistered packet " + packet.getClass().getName());
		}
		ClientBoundMiddlePacket<RecyclableCollection<PacketData>> packetTransformer = registry.getTransformer(currentProtocol, packetId);
		packet.b(middlebuffer.prepareNativeSerializer());
		packetTransformer.readFromServerData(middlebuffer);
		packetTransformer.handle();
		try (RecyclableCollection<PacketData> data = packetTransformer.toData(connection.getVersion())) {
			for (PacketData packetdata : data) {
				ByteBuf senddata = Allocator.allocateBuffer();
				int newPacketId = packetIdRegistry.getNewPacketId(currentProtocol, packetdata.getPacketId());
				if (varintPacketId) {
					ChannelUtils.writeVarInt(senddata, newPacketId);
				} else {
					senddata.writeByte(newPacketId);
				}
				senddata.writeBytes(packetdata);
				ctx.write(senddata);
			}
			ctx.flush();
		}
	}

}
