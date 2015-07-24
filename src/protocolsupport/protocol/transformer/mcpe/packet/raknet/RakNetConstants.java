package protocolsupport.protocol.transformer.mcpe.packet.raknet;

public final class RakNetConstants {

	public static final byte[] MAGIC = new byte[] { (byte) 0x00, (byte) 0xff, (byte) 0xff, (byte) 0x00, (byte) 0xfe, (byte) 0xfe, (byte) 0xfe, (byte) 0xfe, (byte) 0xfd, (byte) 0xfd, (byte) 0xfd, (byte) 0xfd, (byte) 0x12, (byte) 0x34, (byte) 0x56, (byte) 0x78 };

	public static final int ID_PING_OPEN_CONNECTIONS = 0x01;
	public static final int ID_PONG = 0x1C;

	public static final int ID_OPEN_CONNECTION_REQUEST_1 = 0x05;
	public static final int ID_OPEN_CONNECTION_REPLY_1 = 0x06;
	public static final int ID_OPEN_CONNECTION_REQUEST_2 = 0x07;
	public static final int ID_OPEN_CONNECTION_REPLY_2 = 0x08;
	public static final int ACK = 0xC0;
	public static final int NACK = 0xA0;

}
