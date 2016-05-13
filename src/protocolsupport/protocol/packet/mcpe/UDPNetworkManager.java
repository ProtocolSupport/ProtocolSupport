package protocolsupport.protocol.packet.mcpe;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.concurrent.GenericFutureListener;

import java.net.InetSocketAddress;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.spigotmc.SneakyThrow;

import com.google.common.base.Function;

import gnu.trove.iterator.TIntObjectIterator;
import gnu.trove.map.hash.TIntObjectHashMap;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.protocol.legacyremapper.LegacyUtils;
import protocolsupport.protocol.packet.mcpe.handler.ClientBoundPacketHandler;
import protocolsupport.protocol.packet.mcpe.handler.PELoginListener;
import protocolsupport.protocol.packet.mcpe.handler.ServerBoundPacketHandler;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.PEPacketIDs;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.both.BatchPacket;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.both.PingPacket;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.clientbound.KickPacket;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.clientbound.LoginStatusPacket;
import protocolsupport.protocol.packet.mcpe.packet.raknet.ACK;
import protocolsupport.protocol.packet.mcpe.packet.raknet.Connection1Reply;
import protocolsupport.protocol.packet.mcpe.packet.raknet.Connection2Reply;
import protocolsupport.protocol.packet.mcpe.packet.raknet.EncapsulatedPacket;
import protocolsupport.protocol.packet.mcpe.packet.raknet.NACK;
import protocolsupport.protocol.packet.mcpe.packet.raknet.RakNetConstants;
import protocolsupport.protocol.packet.mcpe.packet.raknet.RakNetDataSerializer;
import protocolsupport.protocol.packet.mcpe.packet.raknet.RakNetPacket;
import protocolsupport.protocol.packet.mcpe.packet.raknet.ServerInfoPacket;
import protocolsupport.protocol.packet.mcpe.pipeline.UDPRouter;
import protocolsupport.protocol.storage.SharedStorage;
import protocolsupport.utils.Utils;
import protocolsupport.utils.recyclable.RecyclableCollection;
import net.minecraft.server.v1_9_R2.ChatComponentText;
import net.minecraft.server.v1_9_R2.EnumProtocolDirection;
import net.minecraft.server.v1_9_R2.IChatBaseComponent;
import net.minecraft.server.v1_9_R2.ITickable;
import net.minecraft.server.v1_9_R2.MinecraftServer;
import net.minecraft.server.v1_9_R2.NetworkManager;
import net.minecraft.server.v1_9_R2.Packet;
import net.minecraft.server.v1_9_R2.IChatBaseComponent.ChatSerializer;

public class UDPNetworkManager extends NetworkManager {

	public static final byte[] defaultSkin = new Function<Object, byte[]>() {
		@Override
		public byte[] apply(Object n) {
			try {
				return IOUtils.toByteArray(UDPNetworkManager.class.getResourceAsStream("SKIN.bin"));
			} catch (Throwable t) {
				SneakyThrow.sneaky(t);
			}
			return null;
		}
	}.apply(null);

	public final static long serverID = 0x0000000012345678L;

	private final SharedStorage sharedStorage = new SharedStorage();
	private final ClientBoundPacketHandler clientboundTransforner = new ClientBoundPacketHandler(this, sharedStorage);
	private final ServerBoundPacketHandler serverboundTransformer = new ServerBoundPacketHandler(this, sharedStorage);

	private long lastReceivedPacketTime = System.currentTimeMillis();

	private int mtu;

	public UDPNetworkManager(EnumProtocolDirection enumprotocoldirection) {
		super(enumprotocoldirection);
		setPacketListener(new PELoginListener(this));
		preparing = false;
	}

	private volatile State state = State.NONE;

	private static enum State {
		NONE, CONNECTING, CONNECTED, INFO
	}

	@Override
	public void close(IChatBaseComponent comp) {
		if (channel.isOpen()) {
			try {
				sendPEPacket(new KickPacket(LegacyUtils.toText(ChatAPI.fromJSON(ChatSerializer.a(comp)))));
				super.close(comp);
			} catch (Throwable e) {
			} finally {
				channel.close();
				UDPRouter.getInstance().remove(getClientAddress());
			}
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void sendPacket(Packet packet) {
		sendPacket(packet, null);
	}

	private int lastReceivedACK = -1;
	private final TIntObjectHashMap<RakNetPacket> sentPackets = new TIntObjectHashMap<>(300);
	private final Object sentPacketsLock = new Object();

	private long lastPingTime = System.currentTimeMillis();

	@SuppressWarnings("deprecation")
	@Override
	public void a() {
		long currentTime = System.currentTimeMillis();
		if (currentTime - lastReceivedPacketTime > 30000) {
			close(new ChatComponentText("Timed out"));
			return;
		}
		try {
			synchronized (sentPacketsLock) {
				ArrayList<RakNetPacket> toResend = new ArrayList<RakNetPacket>();
				TIntObjectIterator<RakNetPacket> iterator = sentPackets.iterator();
				while (iterator.hasNext()) {
					iterator.advance();
					RakNetPacket packet = iterator.value();
					if (packet.getSeqNumber() < lastReceivedACK) {
						iterator.remove();
						toResend.add(packet);
					}
				}
				for (RakNetPacket packet : toResend) {
					sendRakNetPacket(packet);
				}
			}
			if (currentTime - lastPingTime > 1000) {
				lastPingTime = currentTime;
				sendPEPacket(new PingPacket());
			}
			if (clientboundTransforner.canSpawnPlayer()) {
				clientboundTransforner.setSpawned();
				sendPEPacket(new LoginStatusPacket(LoginStatusPacket.Status.PLAYER_SPAWN));
			}
			if (clientboundTransforner.isSpawned()) {
				if (i() instanceof ITickable) {
					((ITickable) i()).c();
				}	
			}
		} catch (Exception e) {
			if (MinecraftServer.getServer().isDebugging()) {
				e.printStackTrace();
			}
			close(new ChatComponentText(e.getMessage()));
		}
	}

	@SuppressWarnings({ "rawtypes", "deprecation" })
	@Override
	public void sendPacket(Packet packet, GenericFutureListener genericfuturelistener, GenericFutureListener... agenericfuturelistener) {
		try {
			RecyclableCollection<? extends ClientboundPEPacket> pepackets = clientboundTransforner.transform(packet);
			try {
				for (ClientboundPEPacket pepacket : pepackets) {
					sendPEPacket(pepacket);
				}
			} finally {
				pepackets.recycle();
			}
		} catch (Throwable t) {
			if (MinecraftServer.getServer().isDebugging()) {
				t.printStackTrace();
			}
			close(new ChatComponentText(t.getMessage()));
		}
	}

	@SuppressWarnings({ "rawtypes", "deprecation" })
	public void handleUDP(RakNetPacket raknetpacket) {
		if (!channel.isOpen()) {
			return;
		}
		lastReceivedPacketTime = System.currentTimeMillis();
		ByteBuf buf = raknetpacket.getData();
		try {
			switch (raknetpacket.getId()) {
				case RakNetConstants.ID_PING_OPEN_CONNECTIONS: {
					if (state != State.NONE) {
						break;
					}
					state = State.INFO;
					long pingId = buf.readLong();
					buf.skipBytes(16);
					sendRakNetPacket0(new ServerInfoPacket(getClientAddress(), pingId));
					close(new ChatComponentText(""));
					break;
				}
				case RakNetConstants.ID_OPEN_CONNECTION_REQUEST_1: {
					if (state != State.NONE) {
						break;
					}
					state = State.CONNECTING;
					sendRakNetPacket0(new Connection1Reply(getClientAddress(), ((raknetpacket.getData().readableBytes() - 20) & 0xFFFF)));
					break;
				}
				case RakNetConstants.ID_OPEN_CONNECTION_REQUEST_2: {
					if (state != State.CONNECTING) {
						break;
					}
					state = State.CONNECTED;
					buf.skipBytes(16);
					RakNetDataSerializer.readAddress(buf);
					short clientMTU = buf.readShort();
					this.mtu = clientMTU;
					sendRakNetPacket0(new Connection2Reply(getClientAddress(), clientMTU));
					break;
				}
				case RakNetConstants.ACK: {
					short count = buf.readShort();
					for (int i = 0; i < count; i++) {
						int type = buf.readByte();
						if (type == 0) {
							int idstart = RakNetDataSerializer.readTriad(buf);
							int idfinish = RakNetDataSerializer.readTriad(buf);
							removeRakNetPackets(idstart, idfinish);
						} else {
							int id = RakNetDataSerializer.readTriad(buf);
							removeRakNetPackets(id, id);
						}
					}
					break;
				}
				case RakNetConstants.NACK: {
					short count = buf.readShort();
					for (int i = 0; i < count; i++) {
						int type = buf.readByte();
						if (type == 0) {
							int idstart = RakNetDataSerializer.readTriad(buf);
							int idfinish = RakNetDataSerializer.readTriad(buf);
							resendRakNetPackets(idstart, idfinish);
						} else {
							int id = RakNetDataSerializer.readTriad(buf);
							resendRakNetPackets(id, id);
						}
					}
					break;
				}
				default: {
					if (state == State.CONNECTED) {
						raknetpacket.decodeEncapsulated();
						for (Packet nativepacket : serverboundTransformer.transform(raknetpacket)) {
							channelRead0(null, nativepacket);
						}
					}
					break;
				}
			}
		} catch (Throwable e) {
			if (MinecraftServer.getServer().isDebugging()) {
				e.printStackTrace();
			}
			close(new ChatComponentText(e.getMessage()));
		}
	}

	public void sendPEPacket(ClientboundPEPacket pepacket) throws Exception {
		if (mtu == 0) {
			return;
		}
		if (pepacket.getId() == -1) {
			pepacket.encode(null);
			return;
		}
		ByteBuf buf = Unpooled.buffer();
		//after logging in client prefixes data with 142 except for pong packets
		if (pepacket.getId() != PEPacketIDs.PONG && pepacket.getId() != PEPacketIDs.SERVER_HANDSHAKE) {
			buf.writeByte(142);
		}
		buf.writeByte(pepacket.getId());
		pepacket.encode(buf);
		if (buf.readableBytes() > 256 && !(pepacket instanceof BatchPacket)) {
			sendPEPacket(new BatchPacket(pepacket));
			return;
		}
		int splitSize = mtu - 200;
		int orderIndex = getNextOrderIndex();
		if (buf.readableBytes() > mtu - 100) {
			EncapsulatedPacket[] epackets = new EncapsulatedPacket[Utils.getSplitCount(buf.readableBytes(), splitSize)];
			int splitID = getNextSplitID();
			for (int splitIndex = 0; splitIndex < epackets.length; splitIndex++) {
				epackets[splitIndex] = new EncapsulatedPacket(buf.readBytes(buf.readableBytes() < splitSize ? buf.readableBytes() : splitSize), getNextMessageIndex(), orderIndex, splitID, epackets.length, splitIndex);
			}
			sendEncapsulatedPackets(epackets);
		} else {
			sendEncapsulatedPackets(new EncapsulatedPacket(buf, getNextMessageIndex(), orderIndex));
		}
	}

	private void sendEncapsulatedPackets(EncapsulatedPacket... epackets) {
		for (EncapsulatedPacket epacket : epackets) {
			sendRakNetPacket(new RakNetPacket(epacket, getClientAddress()));
		}
	}

	public void sendACK(int seqNumber) {
		ACK ack = new ACK(getClientAddress());
		ack.id = seqNumber;
		sendRakNetPacket0(ack);
	}

	public void sendNACK(int seqNumberStart, int seqNumberEnd) {
		NACK nack = new NACK(getClientAddress());
		nack.idstart = seqNumberStart;
		nack.idfinish = seqNumberEnd;
		sendRakNetPacket0(nack);
	}

	private void removeRakNetPackets(int idstart, int idfinish) {
		synchronized (sentPacketsLock) {
			for (int id = idstart; id <= idfinish; id++) {
				sentPackets.remove(id);
			}
			lastReceivedACK = idfinish;
		}
	}

	private void resendRakNetPackets(int idstart, int idfinish) {
		synchronized (sentPacketsLock) {
			for (int id = idstart; id <= idfinish; id++) {
				RakNetPacket packet = sentPackets.remove(id);
				if (packet != null) {
					sendRakNetPacket(packet);
				}
			}
		}
	}

	public void sendRakNetPacket(RakNetPacket packet) {
		synchronized (sentPacketsLock) {
			packet.setSeqNumber(getNextRakSeqID());
			sentPackets.put(packet.getSeqNumber(), packet);
		}
		sendRakNetPacket0(packet);
	}

	private void sendRakNetPacket0(RakNetPacket packet) {
		channel.writeAndFlush(packet);
	}

	public InetSocketAddress getClientAddress() {
		return (InetSocketAddress) l;
	}

	private int currentRakSeqID = 0;
	private int getNextRakSeqID() {
		return currentRakSeqID++ % 16777216;
	}

	private int currentSplitID = 0;
	private int getNextSplitID() {
		return currentSplitID++ % Short.MAX_VALUE;
	}

	private int currentMessageIndex = 0;
	private int getNextMessageIndex() {
		return currentMessageIndex++ % 16777216;
	}

	private int currentOrderIndex = 0;
	private int getNextOrderIndex() {
		return currentOrderIndex++ % 16777216;
	}

}
