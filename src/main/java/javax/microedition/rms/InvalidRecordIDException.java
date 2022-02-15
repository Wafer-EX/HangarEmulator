package javax.microedition.rms;

public class InvalidRecordIDException extends RecordStoreException {
    public InvalidRecordIDException(String message) {
        super(message);
    }

    public InvalidRecordIDException() {
        super();
    }
}