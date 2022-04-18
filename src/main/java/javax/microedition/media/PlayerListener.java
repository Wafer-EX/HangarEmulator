package javax.microedition.media;

// TODO: write interface methods
public interface PlayerListener {
    public static final String STARTED = "STARTED";
    public static final String STOPPED = "STOPPED";
    public static final String END_OF_MEDIA = "END_OF_MEDIA";
    public static final String DURATION_UPDATED = "DURATION_UPDATED";
    public static final String DEVICE_UNAVAILABLE = "DEVICE_UNAVAILABLE";
    public static final String DEVICE_AVAILABLE = "DEVICE_AVAILABLE";
    public static final String VOLUME_CHANGED = "VOLUME_CHANGED";
    public static final String ERROR = "ERROR";
    public static final String CLOSED = "CLOSED";

    public void playerUpdate(Player player, String event, Object eventData);
}