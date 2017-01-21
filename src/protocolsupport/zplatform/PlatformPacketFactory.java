package protocolsupport.zplatform;

import java.security.PublicKey;
import java.util.List;

import org.bukkit.Material;

import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.events.ServerPingResponseEvent.ProtocolInfo;
import protocolsupport.protocol.utils.types.Position;

public interface PlatformPacketFactory {

	public Object createInboundInventoryClosePacket();

	public Object createOutboundChatPacket(String message, int position);

	public Object createTabHeaderFooterPacket(BaseComponent header, BaseComponent footer);

	public Object createTitleResetPacket();

	public Object createTitleClearPacket();

	public Object createTitleMainPacket(String title);

	public Object createTitleSubPacket(String title);

	public Object createTitleParamsPacket(int fadeIn, int stay, int fadeOut);

	public Object createLoginDisconnectPacket(String message);

	public Object createPlayDisconnectPacket(String message);

	public Object createLoginEncryptionBeginPacket(PublicKey publicKey, byte[] randomBytes);

	public Object createSetCompressionPacket(int threshold);

	public Object createBlockBreakSoundPacket(Position pos, Material type);

	public Object createStatusPongPacket(long pingId);

	public Object createStausServerInfoPacket(List<String> profiles, ProtocolInfo info, String icon, String motd, int maxPlayers);

	public Object createEmptyCustomPayloadPacket(String tag);

	public Object createFakeJoinGamePacket();

}
