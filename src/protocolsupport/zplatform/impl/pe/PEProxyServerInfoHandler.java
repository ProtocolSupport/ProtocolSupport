package protocolsupport.zplatform.impl.pe;

import java.text.MessageFormat;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.EncoderException;
import io.netty.util.AttributeKey;
import io.netty.util.ReferenceCountUtil;
import protocolsupport.ProtocolSupport;
import protocolsupport.protocol.pipeline.version.v_pe.PEPacketDecoder;
import protocolsupport.protocol.pipeline.version.v_pe.PEPacketEncoder;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.types.pingresponse.PingResponse;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.i18n.I18NData;
import protocolsupport.utils.JavaSystemProperty;
import raknetserver.pipeline.raknet.RakNetPacketConnectionEstablishHandler.PingHandler;

public class PEProxyServerInfoHandler implements PingHandler {

	private static final int statusThreads = JavaSystemProperty.getValue("pestatusthreads", 2, Integer::parseInt);
	private static final int statusThreadKeepAlive = JavaSystemProperty.getValue("pestatusthreadskeepalive", 60, Integer::parseInt);
	private static final String randomId = String.valueOf(ThreadLocalRandom.current().nextInt());

	static {
		ProtocolSupport.logInfo(MessageFormat.format("PE status threads max count: {0}, keep alive time: {1}", statusThreads, statusThreadKeepAlive));
	}

	protected static final Executor statusprocessor = new ThreadPoolExecutor(
		1, statusThreads,
		statusThreadKeepAlive, TimeUnit.SECONDS,
		new LinkedBlockingQueue<Runnable>(),
		r -> new Thread(r, "PEStatusProcessingThread")
	);

	public static final int PACKET_ID = PEPacketIDs.SERVER_TO_CLIENT_HANDSHAKE;

	private static final AttributeKey<Boolean> SENT_INFO_KEY = AttributeKey.valueOf("___PSPEServerInfoSentInfo");

	@Override
	public String getServerInfo(Channel channel) {
		if (Boolean.TRUE.equals(channel.attr(SENT_INFO_KEY).getAndSet(Boolean.TRUE))) {
			return "";
		}
		try {
			ByteBuf request = Unpooled.buffer();
			PEPacketEncoder.sWritePacketId(request, PACKET_ID);
			PingResponseInterceptor interceptor = new PingResponseInterceptor();
			channel.pipeline().addBefore(PEProxyNetworkManager.NAME, "peproxy-serverinfo", interceptor);
			channel.pipeline().context(interceptor).fireChannelRead(request);
			PingResponse ping = interceptor.response.take();
			return String.join(";",
				"MCPE",
				ping.getMotd().toLegacyText(I18NData.DEFAULT_LOCALE).replace(";", ":"),
				String.valueOf(ping.getProtocolData().getVersion()), ProtocolVersionsHelper.LATEST_PE.getName().replaceFirst("PE-", ""),
				String.valueOf(ping.getPlayers().getOnline()),
				String.valueOf(ping.getPlayers().getMax()),
				randomId, // Needs to be unique per server (on a LAN?)
				"ProtocolSupportPE",
				"Survival"
			);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void executeHandler(Runnable runnable) {
		statusprocessor.execute(runnable);
	}


	protected static class PingResponseInterceptor extends ChannelOutboundHandlerAdapter {

		protected final LinkedBlockingQueue<PingResponse> response = new LinkedBlockingQueue<>();

		@Override
		public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
			try {
				if (msg instanceof ByteBuf) {
					ByteBuf serverdata = (ByteBuf) msg;
					if (PEPacketDecoder.sReadPacketId(serverdata) != PACKET_ID) {
						throw new EncoderException("Unknown packet sent by server while handling internal pe ping passthrough");
					}
					response.put(PingResponse.fromJson(StringSerializer.readString(serverdata, ProtocolVersionsHelper.LATEST_PC)));
				}
			} finally {
				ReferenceCountUtil.release(msg);
			}
		}

	}

}
