package protocolsupport.api.remapper;

import java.util.concurrent.ConcurrentMap;
import java.util.function.BiFunction;

import org.apache.commons.lang3.Validate;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.basic.CustomPayloadTransformerRegistry;
import protocolsupport.protocol.typeremapper.basic.CustomPayloadTransformerRegistry.CustomPayloadTransformer;
import protocolsupport.protocol.typeremapper.basic.CustomPayloadTransformerRegistry.CustomPayloadTransformerTable;

public class CustomPayloadRemapperControl {

	private final CustomPayloadTransformerTable table;

	public CustomPayloadRemapperControl(ProtocolVersion version) {
		Validate.isTrue(version.isSupported(), "Can't control custom payload remapping for unsupported version");
		table = CustomPayloadTransformerRegistry.INSTANCE.getTable(version);
	}

	/**
	 * Check if transformer that uses this server tag name is already registered
	 * @param tag server tag
	 * @return true if transformer registered
	 */
	public boolean isServerTagRegistered(String tag) {
		return table.getByServerName(tag) != null;
	}

	/**
	 * Check if transformer that uses this client tag name is already registered
	 * @param tag client tag
	 * @return true if transformer registered
	 */
	public boolean isClientTagRegistered(String tag) {
		return table.getByClientName(tag) != null;
	}

	/**
	 * Register a transformer for (server,client) tags entry<br>
	 * <br>
	 * Data transformer function description:<br>
	 * <pre>
	 *   First parameter is the metadata storage which is shared between all transformer functions for a single connection<br>
	 *   Second parameter is the data input byte array<br>
	 *   The result is the data output byte array<br>
	 *   The input array is local to the function, so it can be returned as output array if no modifications need to be done, or you can do them in place
	 * </pre>
	 * @param serverTag server tag
	 * @param clientTag client tag
	 * @param dataTransformerClientbound data transformer function for server -> client payload
	 * @param dataTransformerServerbound data transformer function for client -> server payload
	 * @throws IllegalArgumentException is transformer for that server or client tag name is already registered
	 */
	public void register(
		String serverTag, String clientTag,
		BiFunction<ConcurrentMap<Object, Object>, byte[], byte[]> dataTransformerClientbound,
		BiFunction<ConcurrentMap<Object, Object>, byte[], byte[]> dataTransformerServerbound
	) {
		Validate.notNull(serverTag, "Server tag cant be null");
		Validate.notNull(clientTag, "Client tag cant be null");
		table.set(new CustomPayloadTransformer(serverTag, clientTag, dataTransformerServerbound, dataTransformerClientbound));
	}

	/**
	 * Unregister a transformer for (server,client) tags entry<br>
	 * This method accepts both server and client tag, but it's better to use server tag name
	 * @param tag server or client tag
	 */
	public void unregister(String tag) {
		Validate.notNull(tag, "Tag cant be null");
		table.remove(tag);
	}

}
