package protocolsupport.api.unsafe.peskins;

public class PESkinsProviderSPI {

	private static PESkinsProvider provider = DefaultPESkinsProvider.INSTANCE;

	public static void setProvider(PESkinsProvider provider) {
		PESkinsProviderSPI.provider = provider;
	}

	public static PESkinsProvider getProvider() {
		return PESkinsProviderSPI.provider;
	}

}
