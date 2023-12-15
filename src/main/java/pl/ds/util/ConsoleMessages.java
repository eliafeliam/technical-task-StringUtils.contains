package pl.ds.util;

public class ConsoleMessages {

  public static final String BOLD = "\033[0;1m";
  public static final String PLAIN = "\033[0;0m";

  public static final String DOWN_ARROW = "▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼\n";
  public static final String WELCOME = "Welcome to the" + BOLD + " Dynamic Solutions(©)" + PLAIN + " StringUtil\n";
  public static final String UP_ARROW = "▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲\n";
  public static final String ENTER_STRING = "Please enter the string:";
  public static final String ENTER_PATTERN = "Please enter the pattern:";
  public static final String ASK_TO_CONTINUE =
          "\nPlease enter " + BOLD + "'y'" + PLAIN + " if you want to continue or any other key to stop";
  public static final String GOODBYE = "Thanks for using" + BOLD + " Dynamic Solutions(©)" + PLAIN + " products\n";
  public static final String MATCHES = ", the pattern '%s' is contained in the string '%s'";
  public static final String NOT_MATCHES =", the pattern '%s' and string '%s' do not match the verification conditions";
}
