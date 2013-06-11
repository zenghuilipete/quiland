package creative.fire.xmlparse.jibx;

public class Set_Recording_Status_Request {
	private String requestID;
	private RecordingStatus recordingStatus;

	public String getRequestID() {
		return requestID;
	}

	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}

	public RecordingStatus getRecordingStatus() {
		return recordingStatus;
	}

	public void setRecordingStatus(RecordingStatus recordingStatus) {
		this.recordingStatus = recordingStatus;
	}
}