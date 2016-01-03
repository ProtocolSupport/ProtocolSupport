package protocolsupport.api.events;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.Validate;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import protocolsupport.api.ProtocolVersion;

public class ServerPingResponseEvent extends Event {

	private final InetSocketAddress address;

	private ProtocolInfo info;
	private String motd;
	private String icon;
	private int maxPlayers;
	private List<String> players;

	public ServerPingResponseEvent(InetSocketAddress address, ProtocolInfo info, String icon, String motd, int maxPlayers, List<String> players) {
		this.address = address;
		this.info = info;
		this.icon = icon;
		this.motd = motd;
		this.maxPlayers = maxPlayers;
		this.players = new ArrayList<String>(players);
	}

	public InetSocketAddress getAddress() {
		return address;
	}

	public ProtocolInfo getProtocolInfo() {
		return info;
	}

	public void setProtocolInfo(ProtocolInfo info) {
		this.info = info;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getMotd() {
		return motd;
	}

	public void setMotd(String motd) {
		this.motd = motd;
	}

	public int getMaxPlayers() {
		return maxPlayers;
	}

	public void setMaxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}

	public List<String> getPlayers() {
		return new ArrayList<String>(players);
	}

	public void setPlayers(List<String> players) {
		this.players = players != null ? new ArrayList<String>(players) : new ArrayList<String>();
	}

	public static class ProtocolInfo {
		private int id;
		private String name;

		public ProtocolInfo(int id, String name) {
			this.id = id;
			this.name = name;
		}

		public ProtocolInfo(ProtocolVersion version, String name) {
			this(version.getId(), name);
		}

		public int getId() {
			return id;
		}

		public String getName() {
			return name;
		}
	}

	private static final HandlerList list = new HandlerList();

	@Override
	public HandlerList getHandlers() {
		return list;
	}

	public static HandlerList getHandlerList() {
		return list;
	}

	public static String loadIcon(File file) throws IOException {
		return loadIcon(new FileInputStream(file));
	}

	public static String loadIcon(InputStream rawStream) throws IOException {
		return loadIcon(ImageIO.read(rawStream));
	}

	public static String loadIcon(BufferedImage image) throws IOException {
        Validate.isTrue(image.getWidth() == 64, "Must be 64 pixels wide");
        Validate.isTrue(image.getHeight() == 64, "Must be 64 pixels high");
		ByteArrayOutputStream data = new ByteArrayOutputStream();
		ImageIO.write(image, "PNG", data);
		return "data:image/png;base64," + Base64.encodeBase64String(data.toByteArray());
	}

}
