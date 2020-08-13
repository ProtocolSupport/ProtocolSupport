package init;

import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.BeforeAll;

import net.minecraft.server.v1_16_R2.DispenserRegistry;
import net.minecraft.server.v1_16_R2.IRegistryCustom;
import protocolsupport.zplatform.ServerPlatform;

public class InitializePlatform {

	private static AtomicBoolean platformInit = new AtomicBoolean(false);

	@BeforeAll
	static void initPlatform() {
		if (platformInit.compareAndSet(false, true)) {
            DispenserRegistry.init();
            DispenserRegistry.c();
            IRegistryCustom.b();
			ServerPlatform.detect();
		}
	}

}
