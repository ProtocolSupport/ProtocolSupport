package protocolsupport.protocol.packet.v_1_9;

import java.io.IOException;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.server.v1_9_R2.EnumProtocol;
import net.minecraft.server.v1_9_R2.EnumProtocolDirection;
import net.minecraft.server.v1_9_R2.Packet;
import net.minecraft.server.v1_9_R2.PacketDataSerializer;
import net.minecraft.server.v1_9_R2.PacketListener;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.PacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8_1_9.BlockTileUpdate;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_9.Chunk;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_9.Login;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_9.WorldSound;
import protocolsupport.protocol.packet.middleimpl.clientbound.status.v_1_7_1_8_1_9.ServerInfo;
import protocolsupport.protocol.pipeline.IPacketEncoder;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.protocol.utils.registry.MiddleTransformerRegistry;
import protocolsupport.protocol.utils.registry.PacketIdTransformerRegistry;
import protocolsupport.utils.netty.Allocator;
import protocolsupport.utils.netty.ChannelUtils;
import protocolsupport.utils.recyclable.RecyclableCollection;

//TODO: Full remapping rewrite table
public class PacketEncoder implements IPacketEncoder {

	private static final PacketIdTransformerRegistry packetIdRegistry = new PacketIdTransformerRegistry();
	static {
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.LEGACY_PLAY_UPDATE_SIGN_ID, 0x46);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_WORLD_SOUND_ID, 0x47);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_PLAYER_LIST_HEADER_FOOTER, 0x48);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_COLLECT_EFFECT_ID, 0x49);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_TELEPORT_ID, 0x4A);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_ATTRIBUTES_ID, 0x4B);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_EFFECT_ADD_ID, 0x4C);
	}

	private final MiddleTransformerRegistry<ClientBoundMiddlePacket<RecyclableCollection<PacketData>>> dataRemapperRegistry = new MiddleTransformerRegistry<>();
	{
		dataRemapperRegistry.register(EnumProtocol.STATUS, ClientBoundPacket.STATUS_SERVER_INFO_ID, ServerInfo.class);
		dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_UPDATE_TILE_ID, BlockTileUpdate.class);
		dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_CHUNK_SINGLE_ID, Chunk.class);
		dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_WORLD_SOUND_ID, WorldSound.class);
		dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_LOGIN_ID, Login.class);
	}

	private final ProtocolVersion version;
	private final PacketDataSerializer serverdata = new PacketDataSerializer(Unpooled.buffer());
	private final ProtocolSupportPacketDataSerializer middlebuffer = new ProtocolSupportPacketDataSerializer(serverdata, ProtocolVersion.getLatest());
	public PacketEncoder(ProtocolVersion version) {
		this.version = version;
	}

	@Override
	public void encode(ChannelHandlerContext ctx, Packet<PacketListener> packet, ByteBuf output) throws Exception {
		Channel channel = ctx.channel();
		EnumProtocol currentProtocol = channel.attr(ChannelUtils.CURRENT_PROTOCOL_KEY).get();
		final Integer packetId = currentProtocol.a(EnumProtocolDirection.CLIENTBOUND, packet);
		if (packetId == null) {
			throw new IOException("Can't serialize unregistered packet");
		}
		serverdata.clear();
		packet.b(serverdata);
		ClientBoundMiddlePacket<RecyclableCollection<PacketData>> packetTransformer = dataRemapperRegistry.getTransformer(currentProtocol, packetId);
		if (version != ProtocolVersion.getLatest() && packetTransformer != null) {
			if (packetTransformer.needsPlayer()) {
				packetTransformer.setPlayer(ChannelUtils.getBukkitPlayer(channel));
			}
			packetTransformer.readFromServerData(middlebuffer);
			packetTransformer.handle();
			RecyclableCollection<PacketData> data = packetTransformer.toData(version);
			try {
				for (PacketData packetdata : data) {
					ByteBuf senddata = Allocator.allocateBuffer();
					ChannelUtils.writeVarInt(senddata, getPacketId(currentProtocol, packetdata.getPacketId(), version));
					senddata.writeBytes(packetdata);
					ctx.write(senddata);
				}
				ctx.flush();
			} finally {
				for (PacketData packetdata : data) {
					packetdata.recycle();
				}
				data.recycle();
			}
		} else {
			ByteBuf senddata = Allocator.allocateBuffer();
			ChannelUtils.writeVarInt(senddata, getPacketId(currentProtocol, packetId, version));
			senddata.writeBytes(serverdata);
			ctx.writeAndFlush(senddata);
		}
	}

	private int getPacketId(EnumProtocol protocol, int oldId, ProtocolVersion version) {
		if (version == ProtocolVersion.getLatest()) {
			return oldId;
		} else {
			int newId = packetIdRegistry.getNewPacketId(protocol, oldId);
			if (newId == -1) {
				newId = oldId;
			}
			return newId;
		}
	}

}
