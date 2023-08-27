package protocolsupport.protocol.types.networkentity.metadata;

import java.text.MessageFormat;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import protocolsupport.ProtocolSupport;
import protocolsupport.ProtocolSupportFileLog;
import protocolsupport.utils.CollectionsUtils.ArrayMap;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupportbuildprocessor.Preload;

@Preload
public class NetworkEntityMetadataObjectIndex<T extends NetworkEntityMetadataObject<?>> {

	protected final Class<?> entityClass;
	protected final int index;
	protected final Class<T> expectedDWObjectType;

	protected NetworkEntityMetadataObjectIndex(@Nonnull Class<?> entityClass, @Nonnegative int index, @Nonnull Class<T> expectedType) {
		this.entityClass = entityClass;
		this.index = index;
		this.expectedDWObjectType = expectedType;
	}

	public @CheckForNull T getObject(@Nonnull ArrayMap<NetworkEntityMetadataObject<?>> metadata) {
		NetworkEntityMetadataObject<?> object = metadata.get(index);
		if (object == null) {
			return null;
		}
		if (expectedDWObjectType.isInstance(object)) {
			return expectedDWObjectType.cast(object);
		}
		if (ServerPlatform.get().getMiscUtils().isDebugging()) {
			ProtocolSupport.logWarning(createInvalidMetadataObjectMessage(object));
		}
		if (ProtocolSupportFileLog.isEnabled()) {
			ProtocolSupportFileLog.logWarningMessage(createInvalidMetadataObjectMessage(object));
		}
		return null;
	}

	public void setObject(@Nonnull ArrayMap<NetworkEntityMetadataObject<?>> metadata, @Nullable T object) {
		if (object == null) {
			metadata.put(index, null);
			return;
		}
		if (expectedDWObjectType.isInstance(object)) {
			metadata.put(index, object);
			return;
		}
		if (ServerPlatform.get().getMiscUtils().isDebugging()) {
			ProtocolSupport.logWarning(createInvalidMetadataObjectMessage(object));
		}
		if (ProtocolSupportFileLog.isEnabled()) {
			ProtocolSupportFileLog.logWarningMessage(createInvalidMetadataObjectMessage(object));
		}
	}

	public boolean copy(@Nonnull ArrayMap<NetworkEntityMetadataObject<?>> source, @Nonnull ArrayMap<NetworkEntityMetadataObject<?>> target) {
		NetworkEntityMetadataObject<?> object = source.get(index);
		if (object == null) {
			return false;
		}
		if (expectedDWObjectType.isInstance(object)) {
			target.put(index, object);
			return true;
		}
		if (ServerPlatform.get().getMiscUtils().isDebugging()) {
			ProtocolSupport.logWarning(createInvalidMetadataObjectMessage(object));
		}
		if (ProtocolSupportFileLog.isEnabled()) {
			ProtocolSupportFileLog.logWarningMessage(createInvalidMetadataObjectMessage(object));
		}
		return false;
	}

	private String createInvalidMetadataObjectMessage(NetworkEntityMetadataObject<?> object) {
		return MessageFormat.format(
			"Invalid metadata type {0} (entity type: {1}, index: {2}) - expected {3}",
			object.getClass().getSimpleName(), entityClass.getSimpleName(), index, expectedDWObjectType.getSimpleName()
		);
	}

}