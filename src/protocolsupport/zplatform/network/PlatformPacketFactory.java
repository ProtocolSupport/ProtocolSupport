package protocolsupport.zplatform.network;

import java.io.IOException;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.NotImplementedException;
import org.bukkit.Material;
import org.spigotmc.SpigotConfig;

import com.mojang.authlib.GameProfile;

import io.netty.buffer.Unpooled;
import net.minecraft.server.v1_11_R1.Block;
import net.minecraft.server.v1_11_R1.ChatComponentText;
import net.minecraft.server.v1_11_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_11_R1.PacketDataSerializer;
import net.minecraft.server.v1_11_R1.PacketLoginOutDisconnect;
import net.minecraft.server.v1_11_R1.PacketLoginOutEncryptionBegin;
import net.minecraft.server.v1_11_R1.PacketLoginOutSetCompression;
import net.minecraft.server.v1_11_R1.PacketPlayInCloseWindow;
import net.minecraft.server.v1_11_R1.PacketPlayOutChat;
import net.minecraft.server.v1_11_R1.PacketPlayOutKickDisconnect;
import net.minecraft.server.v1_11_R1.PacketPlayOutNamedSoundEffect;
import net.minecraft.server.v1_11_R1.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_11_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_11_R1.SoundCategory;
import net.minecraft.server.v1_11_R1.SoundEffectType;
import net.minecraft.server.v1_11_R1.PacketPlayOutTitle.EnumTitleAction;
import net.minecraft.server.v1_11_R1.PacketStatusOutPong;
import net.minecraft.server.v1_11_R1.PacketStatusOutServerInfo;
import net.minecraft.server.v1_11_R1.ServerPing;
import net.minecraft.server.v1_11_R1.ServerPing.ServerData;
import net.minecraft.server.v1_11_R1.ServerPing.ServerPingPlayerSample;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.chat.components.TextComponent;
import protocolsupport.api.events.ServerPingResponseEvent.ProtocolInfo;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.zplatform.ServerImplementationType;

public class PlatformPacketFactory {

	public static Object createInboundInventoryClosePacket() {
		switch (ServerImplementationType.get()) {
			case SPIGOT: {
				return new PacketPlayInCloseWindow();
			}
			default: {
				// TODO: implement for glowstone
				throw new NotImplementedException("Not implemented yet");
			}
		}
	}

	public static Object createOutboundChatPacket(String message, int position) {
		switch (ServerImplementationType.get()) {
			case SPIGOT: {
				return new PacketPlayOutChat(ChatSerializer.a(message), (byte) position);
			}
			default: {
				// TODO: implement for glowstone
				throw new NotImplementedException("Not implemented yet");
			}
		}
	}

	public static Object createTabHeaderFooterPacket(BaseComponent header, BaseComponent footer) {
		switch (ServerImplementationType.get()) {
			case SPIGOT: {
				PacketDataSerializer serializer = new PacketDataSerializer(Unpooled.buffer());
				serializer.a(ChatAPI.toJSON(header != null ? header : PlatformPacketFactory.empty));
				serializer.a(ChatAPI.toJSON(footer != null ? footer : PlatformPacketFactory.empty));
				PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
				try {
					packet.a(serializer);
				} catch (IOException e) {
				}
				return packet;
			}
			default: {
				// TODO: implement for glowstone
				throw new NotImplementedException("Not implemented yet");
			}
		}
	}

	public static final BaseComponent empty = new TextComponent("");

	public static Object createTitleResetPacket() {
		switch (ServerImplementationType.get()) {
			case SPIGOT: {
				return new PacketPlayOutTitle(EnumTitleAction.CLEAR, null);
			}
			default: {
				// TODO: implement for glowstone
				throw new NotImplementedException("Not implemented yet");
			}
		}
	}

	public static Object createTitleClearPacket() {
		switch (ServerImplementationType.get()) {
			case SPIGOT: {
				return new PacketPlayOutTitle(EnumTitleAction.RESET, null);
			}
			default: {
				// TODO: implement for glowstone
				throw new NotImplementedException("Not implemented yet");
			}
		}
	}

	public static Object createTitleMainPacket(String title) {
		switch (ServerImplementationType.get()) {
			case SPIGOT: {
				return new PacketPlayOutTitle(EnumTitleAction.TITLE, ChatSerializer.a(title));
			}
			default: {
				// TODO: implement for glowstone
				throw new NotImplementedException("Not implemented yet");
			}
		}
	}

	public static Object createTitleSubPacket(String title) {
		switch (ServerImplementationType.get()) {
			case SPIGOT: {
				return new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, ChatSerializer.a(title));
			}
			default: {
				// TODO: implement for glowstone
				throw new NotImplementedException("Not implemented yet");
			}
		}
	}

	public static Object createTitleParamsPacket(int fadeIn, int stay, int fadeOut) {
		switch (ServerImplementationType.get()) {
			case SPIGOT: {
				return new PacketPlayOutTitle(fadeIn, stay, fadeOut);
			}
			default: {
				// TODO: implement for glowstone
				throw new NotImplementedException("Not implemented yet");
			}
		}
	}

	public static Object createLoginDisconnectPacket(String message) {
		switch (ServerImplementationType.get()) {
			case SPIGOT: {
				return new PacketLoginOutDisconnect(new ChatComponentText(message));
			}
			default: {
				// TODO: implement for glowstone
				throw new NotImplementedException("Not implemented yet");
			}
		}
	}

	public static Object createPlayDisconnectPacket(String message) {
		switch (ServerImplementationType.get()) {
			case SPIGOT: {
				return new PacketPlayOutKickDisconnect(new ChatComponentText(message));
			}
			default: {
				// TODO: implement for glowstone
				throw new NotImplementedException("Not implemented yet");
			}
		}
	}

	public static Object createLoginEncryptionBeginPacket(PublicKey publicKey, byte[] randomBytes) {
		switch (ServerImplementationType.get()) {
			case SPIGOT: {
				return new PacketLoginOutEncryptionBegin("", publicKey, randomBytes);
			}
			default: {
				// TODO: implement for glowstone
				throw new NotImplementedException("Not implemented yet");
			}
		}
	}

	public static Object createSetCompressionPacket(int threshold) {
		switch (ServerImplementationType.get()) {
			case SPIGOT: {
				return new PacketLoginOutSetCompression(threshold);
			}
			default: {
				// TODO: implement for glowstone
				throw new NotImplementedException("Not implemented yet");
			}
		}
	}

	@SuppressWarnings("deprecation")
	public static Object createBlockBreakSoundPacket(Position pos, Material type) {
		switch (ServerImplementationType.get()) {
			case SPIGOT: {
				SoundEffectType blocksound = Block.getById(type.getId()).getStepSound();
				return new PacketPlayOutNamedSoundEffect(
					blocksound.e(), SoundCategory.BLOCKS,
					pos.getX(), pos.getY(), pos.getZ(),
					(blocksound.a() + 1.0F) / 2.0F,
					blocksound.b() * 0.8F
				);
			}
			default: {
				// TODO: implement for glowstone
				throw new NotImplementedException("Not implemented yet");
			}
		}
	}

	public static Object createStatusPongPacket(long pingId) {
		switch (ServerImplementationType.get()) {
			case SPIGOT: {
				return new PacketStatusOutPong(pingId);
			}
			default: {
				// TODO: implement for glowstone
				throw new NotImplementedException("Not implemented yet");
			}
		}
	}

	private static final UUID profileUUID = UUID.randomUUID();
	public static Object createStausServerInfoPacket(List<String> profiles, ProtocolInfo info, String icon, String motd, int maxPlayers) {
		switch (ServerImplementationType.get()) {
			case SPIGOT: {
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
			default: {
				// TODO: implement for glowstone
				throw new NotImplementedException("Not implemented yet");
			}
		}
	}

}
