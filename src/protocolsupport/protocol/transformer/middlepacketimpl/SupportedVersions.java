package protocolsupport.protocol.transformer.middlepacketimpl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import protocolsupport.api.ProtocolVersion;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SupportedVersions {

	ProtocolVersion[] value();

}
