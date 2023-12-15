package pl.ds.app;

import java.util.Scanner;

import static pl.ds.util.ConsoleMessages.*;
import static pl.ds.util.StringUtil.contains;

public class StringUtilApplication {
  public static void main(String[] args) {
    show(DOWN_ARROW + WELCOME + UP_ARROW);

    Scanner scanner = new Scanner(System.in);
    start(scanner);
    scanner.close();

    show(DOWN_ARROW + GOODBYE + UP_ARROW);
  }

  static void show(String message) {
    System.out.println(message);
  }

  static void start(Scanner scanner) {
    show(BOLD + ENTER_STRING + PLAIN);
    String string = scanner.nextLine();
    show(BOLD + ENTER_PATTERN + PLAIN);
    String pattern = scanner.nextLine();
    String answer = contains(string, pattern) ?
            String.format(BOLD + "Yes" + PLAIN + MATCHES, pattern, string) :
            String.format(BOLD + "No" + PLAIN + NOT_MATCHES, pattern, string);
    show(answer);
    repeat(scanner);
  }

  private static void repeat(Scanner scanner) {
    show(ASK_TO_CONTINUE);
    String startAgain = scanner.nextLine();
    if (startAgain.equals("y")) {
      start(scanner);
    }
  }
}
