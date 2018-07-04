package protocolsupport.zplatform.impl.pe;

import java.text.MessageFormat;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
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
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.i18n.I18NData;
import protocolsupport.protocol.utils.pingresponse.PingResponse;
import protocolsupport.utils.Utils;
import raknetserver.pipeline.raknet.RakNetPacketConnectionEstablishHandler.PingHandler;

public class PEProxyServerInfoHandler implements PingHandler {

	private static final int statusThreads = Utils.getJavaPropertyValue("pestatusthreads", 2, Integer::parseInt);
	private static final int statusThreadKeepAlive = Utils.getJavaPropertyValue("pestatusthreadskeepalive", 60, Integer::parseInt);

	static {
		ProtocolSupport.logInfo(MessageFormat.format("PE status threads max count: {0}, keep alive time: {1}", statusThreads, statusThreadKeepAlive));
	}

	protected static final Executor statusprocessor = new ThreadPoolExecutor(
		1, statusThreads,
		statusThreadKeepAlive, TimeUnit.SECONDS,
		new LinkedBlockingQueue<Runnable>(),
		r -> new Thread(r, "PEStatusProcessingThread")
	);

	public static final int PACKET_ID = 3;

	protected static AttributeKey<Boolean> sentInfoKey = AttributeKey.valueOf("___PSPEServerInfoSentInfo");

	@Override
	public String getServerInfo(Channel channel) {
		if (Utils.isTrue(channel.attr(sentInfoKey).getAndSet(Boolean.TRUE))) {
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
				String.valueOf(ping.getProtocolData().getVersion()), "1.14.1",
				String.valueOf(ping.getPlayers().getOnline()),
				String.valueOf(ping.getPlayers().getMax()),
				"1337", //TODO: find out how it is used, apprently that is some sort of id
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
				ByteBuf serverdata = (ByteBuf) msg;
				if (PEPacketDecoder.sReadPacketId(serverdata) != PACKET_ID) {
					throw new EncoderException("Unknown packet sent by server while handling internal pe ping passthrough");
				}
				response.put(PingResponse.fromJson(StringSerializer.readString(serverdata, ProtocolVersionsHelper.LATEST_PC)));
			} finally {
				ReferenceCountUtil.release(msg);
			}
		}

	}

}
