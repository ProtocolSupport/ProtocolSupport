package protocolsupport.zplatform.network;

import java.io.IOException;
import java.security.PublicKey;

import org.apache.commons.lang3.NotImplementedException;

import io.netty.buffer.Unpooled;
import net.minecraft.server.v1_11_R1.ChatComponentText;
import net.minecraft.server.v1_11_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_11_R1.PacketDataSerializer;
import net.minecraft.server.v1_11_R1.PacketLoginOutDisconnect;
import net.minecraft.server.v1_11_R1.PacketLoginOutEncryptionBegin;
import net.minecraft.server.v1_11_R1.PacketLoginOutSetCompression;
import net.minecraft.server.v1_11_R1.PacketPlayInCloseWindow;
import net.minecraft.server.v1_11_R1.PacketPlayOutChat;
import net.minecraft.server.v1_11_R1.PacketPlayOutKickDisconnect;
import net.minecraft.server.v1_11_R1.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_11_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_11_R1.PacketPlayOutTitle.EnumTitleAction;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.chat.components.TextComponent;
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

}
