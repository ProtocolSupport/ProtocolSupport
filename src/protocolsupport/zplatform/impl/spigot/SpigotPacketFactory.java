package protocolsupport.zplatform.impl.spigot;

import java.io.IOException;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.bukkit.Material;
import org.spigotmc.SpigotConfig;

import com.mojang.authlib.GameProfile;

import io.netty.buffer.Unpooled;
import net.minecraft.server.v1_11_R1.Block;
import net.minecraft.server.v1_11_R1.ChatComponentText;
import net.minecraft.server.v1_11_R1.EnumDifficulty;
import net.minecraft.server.v1_11_R1.EnumGamemode;
import net.minecraft.server.v1_11_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_11_R1.PacketDataSerializer;
import net.minecraft.server.v1_11_R1.PacketLoginOutDisconnect;
import net.minecraft.server.v1_11_R1.PacketLoginOutEncryptionBegin;
import net.minecraft.server.v1_11_R1.PacketLoginOutSetCompression;
import net.minecraft.server.v1_11_R1.PacketPlayInCloseWindow;
import net.minecraft.server.v1_11_R1.PacketPlayOutChat;
import net.minecraft.server.v1_11_R1.PacketPlayOutCustomPayload;
import net.minecraft.server.v1_11_R1.PacketPlayOutKickDisconnect;
import net.minecraft.server.v1_11_R1.PacketPlayOutLogin;
import net.minecraft.server.v1_11_R1.PacketPlayOutNamedSoundEffect;
import net.minecraft.server.v1_11_R1.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_11_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_11_R1.PacketPlayOutTitle.EnumTitleAction;
import net.minecraft.server.v1_11_R1.PacketStatusOutPong;
import net.minecraft.server.v1_11_R1.PacketStatusOutServerInfo;
import net.minecraft.server.v1_11_R1.ServerPing;
import net.minecraft.server.v1_11_R1.ServerPing.ServerData;
import net.minecraft.server.v1_11_R1.ServerPing.ServerPingPlayerSample;
import net.minecraft.server.v1_11_R1.SoundCategory;
import net.minecraft.server.v1_11_R1.SoundEffectType;
import net.minecraft.server.v1_11_R1.WorldType;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.chat.components.TextComponent;
import protocolsupport.api.events.ServerPingResponseEvent.ProtocolInfo;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.zplatform.PlatformPacketFactory;

public class SpigotPacketFactory implements PlatformPacketFactory {

	public Object createInboundInventoryClosePacket() {
		return new PacketPlayInCloseWindow();
	}

	public Object createOutboundChatPacket(String message, int position) {
		return new PacketPlayOutChat(ChatSerializer.a(message), (byte) position);
	}

	private static final BaseComponent empty = new TextComponent("");

	public Object createTabHeaderFooterPacket(BaseComponent header, BaseComponent footer) {
		PacketDataSerializer serializer = new PacketDataSerializer(Unpooled.buffer());
		serializer.a(ChatAPI.toJSON(header != null ? header : empty));
		serializer.a(ChatAPI.toJSON(footer != null ? footer : empty));
		PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
		try {
			packet.a(serializer);
		} catch (IOException e) {
		}
		return packet;
	}

	public Object createTitleResetPacket() {
		return new PacketPlayOutTitle(EnumTitleAction.CLEAR, null);
	}

	public Object createTitleClearPacket() {
		return new PacketPlayOutTitle(EnumTitleAction.RESET, null);
	}

	public Object createTitleMainPacket(String title) {
		return new PacketPlayOutTitle(EnumTitleAction.TITLE, ChatSerializer.a(title));
	}

	public Object createTitleSubPacket(String title) {
		return new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, ChatSerializer.a(title));
	}

	public Object createTitleParamsPacket(int fadeIn, int stay, int fadeOut) {
		return new PacketPlayOutTitle(fadeIn, stay, fadeOut);
	}

	public Object createLoginDisconnectPacket(String message) {
		return new PacketLoginOutDisconnect(new ChatComponentText(message));
	}

	public Object createPlayDisconnectPacket(String message) {
		return new PacketPlayOutKickDisconnect(new ChatComponentText(message));
	}

	public Object createLoginEncryptionBeginPacket(PublicKey publicKey, byte[] randomBytes) {
		return new PacketLoginOutEncryptionBegin("", publicKey, randomBytes);
	}

	public Object createSetCompressionPacket(int threshold) {
		return new PacketLoginOutSetCompression(threshold);
	}

	@SuppressWarnings("deprecation")
	public Object createBlockBreakSoundPacket(Position pos, Material type) {
		SoundEffectType blocksound = Block.getById(type.getId()).getStepSound();
		return new PacketPlayOutNamedSoundEffect(
			blocksound.e(), SoundCategory.BLOCKS,
			pos.getX(), pos.getY(), pos.getZ(),
			(blocksound.a() + 1.0F) / 2.0F,
			blocksound.b() * 0.8F
		);
	}

	public Object createStatusPongPacket(long pingId) {
		return new PacketStatusOutPong(pingId);
	}

	private final UUID profileUUID = UUID.randomUUID();
	public Object createStausServerInfoPacket(List<String> profiles, ProtocolInfo info, String icon, String motd, int maxPlayers) {
		ServerPingPlayerSample playerSample = new ServerPingPlayerSample(maxPlayers, profiles.size());

		Collections.shuffle(profiles);
		GameProfile[] gprofiles = new GameProfile[profiles.size()];
		for (int i = 0; i < profiles.size(); i++) {
			gprofiles[i] = new GameProfile(profileUUID, profiles.get(i));
		}
		gprofiles = Arrays.copyOfRange(gprofiles, 0, Math.min(gprofiles.length, SpigotConfig.playerSample));
		playerSample.a(gprofiles);

		ServerPing serverping = new ServerPing();
		serverping.setFavicon(icon);
		serverping.setMOTD(new ChatComponentText(motd));
		serverping.setPlayerSample(playerSample);
		serverping.setServerInfo(new ServerData(info.getName(), info.getId()));

		return new PacketStatusOutServerInfo(serverping);
	}

	private static final PacketDataSerializer emptyPDS = new PacketDataSerializer(Unpooled.EMPTY_BUFFER);
	@Override
	public Object createEmptyCustomPayloadPacket(String tag) {
		return new PacketPlayOutCustomPayload(tag, emptyPDS);
	}

	@Override
	public Object createFakeJoinGamePacket() {
		return new PacketPlayOutLogin(0, EnumGamemode.NOT_SET, false, 0, EnumDifficulty.EASY, 60, WorldType.NORMAL, false);
	}

}
