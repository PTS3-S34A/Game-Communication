package nl.soccar.socnet.codec.handshake;

/**
 *
 */
public final class HandshakeResponse {

    private final long seed;
    private final int version;

    public HandshakeResponse(long seed, int version) {
        this.seed = seed;
        this.version = version;
    }

    public long getSeed() {
        return seed;
    }

    public int getNetworkVersion() {
        return version;
    }

}
