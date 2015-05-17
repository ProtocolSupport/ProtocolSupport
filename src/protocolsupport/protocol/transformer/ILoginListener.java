package protocolsupport.protocol.transformer;

import javax.crypto.SecretKey;

import org.apache.logging.log4j.Logger;

import net.minecraft.server.v1_8_R3.NetworkManager;

import com.mojang.authlib.GameProfile;

public interface ILoginListener {

	public Logger getLogger();

	public GameProfile getProfile();

	public void setProfile(GameProfile profile);

	public GameProfile generateOfflineProfile(GameProfile current);

	public void setLoginState(LoginState state);

	public void disconnect(String message);

	public void initUUID();

	public SecretKey getLoginKey();

	public NetworkManager getNetworkManager();

}
