package protocolsupport.protocol.packet.handler;

public enum LoginState {
	HELLO, ONLINEMODERESOLVE, KEY, AUTHENTICATING, READY_TO_ACCEPT, ACCEPTED;
}