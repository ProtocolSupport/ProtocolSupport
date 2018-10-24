//package protocolsupport.zplatform.impl.glowstone.injector.packets;
//
//import com.flowpowered.network.service.HandlerLookupService;
//
//import net.glowstone.net.message.handshake.HandshakeMessage;
//import net.glowstone.net.message.login.EncryptionKeyResponseMessage;
//import net.glowstone.net.message.login.LoginStartMessage;
//import net.glowstone.net.message.status.StatusPingMessage;
//import net.glowstone.net.message.status.StatusRequestMessage;
//import net.glowstone.net.protocol.GlowProtocol;
//import net.glowstone.net.protocol.ProtocolType;
//import protocolsupport.utils.ReflectionUtils;
//
//public class GlowStonePacketsInjector {
//
//	public static void inject() throws IllegalAccessException, InstantiationException {
//		injectInboundHandler(ProtocolType.HANDSHAKE.getProtocol(), HandshakeMessage.class, GlowStoneHandshakeStartPacketHandler.class);
//		injectInboundHandler(ProtocolType.STATUS.getProtocol(), StatusRequestMessage.class, GlowStoneStatusServerInfoRequestHandler.class);
//		injectInboundHandler(ProtocolType.STATUS.getProtocol(), StatusPingMessage.class, GlowStoneStatusServerPingHandler.class);
//		injectInboundHandler(ProtocolType.LOGIN.getProtocol(), LoginStartMessage.class, GlowStoneLoginStartHandler.class);
//		injectInboundHandler(ProtocolType.LOGIN.getProtocol(), EncryptionKeyResponseMessage.class, GlowStoneLoginEncryptionKeyResponseHandler.class);
//	}
//
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	private static void injectInboundHandler(GlowProtocol protocol, Class message, Class handler) throws IllegalAccessException, InstantiationException {
//		HandlerLookupService svc = (HandlerLookupService) ReflectionUtils.getField(GlowProtocol.class, "handlers").get(protocol);
//		svc.bind(message, handler);
//	}
//
//}
