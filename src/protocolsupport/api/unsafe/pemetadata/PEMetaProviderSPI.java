package protocolsupport.api.unsafe.pemetadata;

public class PEMetaProviderSPI {

	private static PEMetaProvider provider = new DefaultPEMetaProvider();

	public static void setProvider(PEMetaProvider provider) {
		PEMetaProviderSPI.provider = provider;
	}

	public static PEMetaProvider getProvider() {
		return PEMetaProviderSPI.provider;
	}

}
