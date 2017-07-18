package trb.tii.efe.tools;


import java.util.*;
import java.io.*;

final class Logger
{
   // Debugging flag.
   final static boolean debug = false;


   // Logger record that holds all the details pertinant to one log entry.
   // It is used internally by LogEntry class that holds all of these
   // log entry records in a hash table since they are indexed by an integer
   // record code and these can range substantially therefore a need for
   // scarsely populated array - HashMap.
   private class LogEntry
   {
	  private int code;
	  private int severity;
	  private String shortMessage;
	  private String longMessage;

	  public LogEntry(int numericCode, int severityLevel, String shortMsg, String longMsg)
	  {
		 if (debug) System.err.println("Creating NewsFeedError " + numericCode);
		 code = numericCode;
		 severity = severityLevel;
		 shortMessage = shortMsg;
		 longMessage = longMsg;
	  }

	  int getCode() { return code; }
	  public int getSeverity() { return severity; }
	  public String getShortMessage() { return shortMessage; }
	  public String getLongMessage() { return longMessage; }
   }

   // Log file details. These can be set and reset by setLogDetails()
   private static final String defaultOutputFile = "ClassifiedTool.log";
   private static String outputFile = defaultOutputFile; // File to which logging will take place.
   private static FileWriter out;  // The output stream for logging.


   private static final Hashtable logTable = new Hashtable(); // The hash table of defined log entries


   // Instantiation (construction) of this singleton class.
   // The instance object is created when the static members (the class template)
   // is created (anytime a static memeber of this class is used).
   private static final Logger logger = new Logger();


   // Log codes:
   public static final int IOException1000 = 1000;
   


   // Creates and initializes the Logger Table.
   private Logger() {
	  if (debug) System.err.println("Attempting to open default log File: " + defaultOutputFile);
	  try {
		 out = new FileWriter(defaultOutputFile, true);
		 out.write(new Date() + " : Logger starting up.\n");
		 out.write("File: '" + defaultOutputFile + "' in directory: '" + System.getProperty("user.dir") + "'.\n");
		 out.write("JVM: " + System.getProperty("java.version") + " from " + System.getProperty("java.vendor") + ".\n");
		 out.flush();
	  } catch (IOException e) {
		 System.err.println("IOException caught: " + e);
	  //} catch (FileNotFoundException e) {
		 System.err.println("File \"" + defaultOutputFile + "\" opening/creation problem. " + e);
	  }

	  if (debug) System.err.println("Creating new NewsFeedErrorTable.");
	  // Initialize the HashMap with error table
	  LogEntry err;
	  err = new LogEntry(1000, 2, "Msg 1000", "Error retrieving configuration file in main method of FeedExportProcess.");
	  logTable.put(new Integer(err.getCode()), err);

	  if (debug) System.err.println("Logger table size: " + logTable.size());
	  if (debug) System.err.println(logTable);
   }                                       


   public static String getLongMessage(int code) {
	  LogEntry e = (LogEntry) logTable.get(new Integer(code));
	  return e.getLongMessage();
   }         


   public static int getSeverity(int code) {
	  LogEntry e = (LogEntry) logTable.get(new Integer(code));
	  return e.getSeverity();
   }         


   public static String getShortMessage(int code) {
	  LogEntry e = (LogEntry) logTable.get(new Integer(code));
	  return e.getShortMessage();
   }         


   // Logs standard Logger into the log file.
   public static void log(int errCode) {
	  try {
		 out.write(new Date() + " : " + toString(errCode) + '\n');
	  	 out.write(memory());
		 out.flush();
	  } catch (IOException e) {
		 System.err.println("IOException caught: " + e);
	  }
   }     


   // Logs standard Logger and String into the log file.
   public static void log(int errCode, String s) {
	  try {
		 out.write(new Date() + " : " + toString(errCode) + " -- " + s + '\n');
	  	 out.write(memory());
		 out.flush();
	  } catch (IOException e) {
		 System.err.println("IOException caught: " + e);
	  }
   }         


   // Logs standard String into the log file.
   public static void log(String s) {
	  try {
		 out.write(new Date() + " : " + s + '\n');
	  	 out.write(memory());
		 out.flush();
	  } catch (IOException e) {
		 System.err.println("IOException caught: " + e);
	  }
   }         


   // Details of each loging sesion, like destination File, are set here.
   public static void setLogDetails(String logFileName) {
	  try {
		 if (debug) System.err.println("Closing File: " + outputFile);
		 out.write(new Date() + " : Subsequent logging redirected to file " + logFileName + ". Closing this file.\n");
		 out.flush();
		 out.close();
		 outputFile = logFileName;
		 if (debug) System.err.println("Attempting to open File: " + outputFile);
		 out = new FileWriter(outputFile, true);
		 out.write("\n" + new Date() + " : SetLogDetails called.\n");
		 out.flush();
	  } catch (IOException e) {
		 System.err.println("IOException caught: " + e);
	  }
   }         


   public static String toString(int code) {
	  LogEntry e = (LogEntry) logTable.get(new Integer(code));
	  return "Error: " + code + ", Severity: " + e.getSeverity()
		   + ", Short Message: " + e.getShortMessage() + ", Long Message: " + e.getLongMessage();
   }         

   
   // Gets memory usage of this process.
   static String memory() {
	Runtime rt = Runtime.getRuntime();
	String m = "";
	m += "Mem TOT=" + rt.totalMemory();
	m += ": FRE=" + rt.freeMemory();
	m += ';';
	//m += '\n';
	return m;
   }

}
