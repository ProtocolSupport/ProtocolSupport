package protocolsupport.protocol.types;

import java.util.UUID;

public record ChatSession(UUID sessionId, long expiresAt, byte[] publicKey, byte[] signature) {
}
