package protocolsupport.protocol.packet.handlers;

public enum LoginState {
	HELLO, ONLINEMODERESOLVE, KEY, AUTHENTICATING, READY_TO_ACCEPT, ACCEPTED;
}