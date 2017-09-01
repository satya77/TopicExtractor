package setting;

/**
 * @author (c) 2017, satya almasian
 * Exception thrown someone tries to access the logwriter without initilizing it
 */
public class LogWriterNotInitialized extends Exception {

	private static final long serialVersionUID = 3L;

	/**
	 * Constructor without a message.
	 */
	public LogWriterNotInitialized(){
		super();
	}
	
	/**
	 * Constructor with a message.
	 * @param message The message for this exception.
	 */
	public LogWriterNotInitialized(String message){
		super(message);
	}
}
