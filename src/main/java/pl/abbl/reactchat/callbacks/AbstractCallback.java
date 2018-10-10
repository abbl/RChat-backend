package pl.abbl.reactchat.callbacks;

public abstract class AbstractCallback {
	protected String response;

	public AbstractCallback(String response) {
		this.response = response;
	}

	public String getResponse() {
		return response;
	}
}
