package pl.ds.util;

import java.util.Arrays;

public final class StringUtil {

  static final char ASTERISK = '*';
  static final char BACK_SLASH = '\\';

  private StringUtil() {
  }
  /**
   * <p>Checks if a {@code string} contains a {@code pattern}.</p>
   *
   * <p>A pattern may contain single or multiple wildcard characters {@code *}.
   * Each occurrence of {@code *} in the {@code pattern} means that it can be a match for
   * zero or more characters of the {@code string}.</p>
   *
   * <p>Asterisk (*) is considered as a regular character, if it is preceded by a backslash (\)
   * in a pattern. Backslash (\) is considered as a regular character in all cases other
   * than preceding the asterisk (*).</p>
   *
   * <p>Examples:</p>
   * <pre>
   * StringUtils.contains(null, *) = false
   * StringUtils.contains(*, null) = false
   * StringUtils.contains("", "") = true
   * StringUtils.contains("abc", "") = true
   * StringUtils.contains("abc", "a") = true
   * StringUtils.contains("abc", "a*") = true
   * StringUtils.contains("a*c", "a\*") = true
   * StringUtils.contains("a*c", "abc") = false
   * StringUtils.contains("abc", "A") = false
   * StringUtils.contains("abc", "abcd") = false
   * </pre>
   *
   * @param string  string to check
   * @param pattern pattern to search in a string
   * @return true if the {@code string} contains a {@code pattern}, false otherwise.
   */
  public static boolean contains(String string, String pattern) {
    if (string == null || pattern == null) return false;
    char[] stringArray = string.toCharArray();
    char[] patternArray = pattern.toCharArray();
    if (pattern.isEmpty() || asterisksOnly(patternArray)) return true;
    boolean success = false;
    for (int stringCharNumberToStart = 0; stringCharNumberToStart < stringArray.length; stringCharNumberToStart++) {
      if (success) return true;
      success = search(stringArray, patternArray, stringCharNumberToStart);
    }
    return success;
  }

  private static boolean asterisksOnly(char[] patternArray) {
    for (char c : patternArray) {
      if (c != ASTERISK) return false;
    } return true;
  }

  private static boolean search(char[] stringArray, char[] patternArray, int stringCharNo) {
    boolean match = false;
    for (int patternCharNo = 0; patternCharNo < patternArray.length; patternCharNo++) {
      match = charMatches(stringArray, stringCharNo, patternArray, patternCharNo);
      if (!match) break;
      if (cancelingRegexSequence(patternArray, patternCharNo)) ++patternCharNo;
      if (greedyMode(patternArray, patternCharNo, stringArray, stringCharNo)) return true;
      if (patternCharShouldRemain(patternArray, patternCharNo, stringArray, stringCharNo)) --patternCharNo;
      if (stringCharNo + 1 < stringArray.length) ++stringCharNo;
      else {
        match = !patternHasUncheckedChars(patternArray, patternCharNo);
        if (!match) break;
        ++patternCharNo;
      }
    }
    return match;
  }

  private static boolean charMatches(char[] stringArray, int stringCharNumber,
                                     char[] patternArray, int patternCharNumber) {
    return (stringArray[stringCharNumber] == patternArray[patternCharNumber]
            && !stringAndPatternSlashes(stringArray, stringCharNumber, patternArray, patternCharNumber))
            || anyCharAllowed(patternArray, patternCharNumber)
            || stringArray[stringCharNumber] == ASTERISK && cancelingRegexSequence(patternArray, patternCharNumber);
  }

  private static boolean greedyMode(char[] patternArray, int patternCharNumber, char[] stringArray, int stringCharNumber) {
    return repeatAvailable(patternArray, patternCharNumber) &&
            contains(strFromArr(stringArray, stringCharNumber),
                    strFromArr(patternArray, ++patternCharNumber));
  }

  private static boolean repeatAvailable(char[] patternArray, int patternCharNumber) {
    return patternArray[patternCharNumber] == ASTERISK
            && previousCharNotSlash(patternArray, patternCharNumber)
            && patternCharNumber + 1 < patternArray.length;
  }

  private static boolean patternHasUncheckedChars(char[] patternArray, int patternCharNumber) {
    return patternCharNumber + 1 < patternArray.length
            && patternArray[++patternCharNumber] != ASTERISK;
  }

  private static boolean patternCharShouldRemain(char[] patternArray, int patternCharNumber, char[] stringArray, int stringCharNumber) {
    return anyCharAllowed(patternArray, patternCharNumber)
            && (!nextCharMatches(stringArray, stringCharNumber, patternArray, patternCharNumber)
            && !currentStringCharAllowedByNextCharInPattern(stringArray, stringCharNumber, patternArray, patternCharNumber)
            && patternArray[patternCharNumber] == ASTERISK);
  }

  private static String strFromArr(char[] initArray, int arrayCharNumber) {
    return String.valueOf(Arrays.copyOfRange(initArray, arrayCharNumber, initArray.length));
  }

  private static boolean currentStringCharAllowedByNextCharInPattern(char[] stringArray, int stringCharNumber, char[] patternArray, int patternCharNumber) {
    if (patternCharNumber + 1 < patternArray.length) {
      return stringArray[stringCharNumber] == patternArray[++patternCharNumber];
    } return false;
  }

  private static boolean nextCharMatches(char[] stringArray, int stringCharNumber, char[] patternArray, int patternCharNumber) {
    return (stringCharNumber + 1 < stringArray.length && patternCharNumber + 1 < patternArray.length)
            && stringArray[stringCharNumber + 1] == patternArray[patternCharNumber + 1]
            || (cancelingRegexSequence(patternArray, patternCharNumber + 1)
            && stringArray[stringCharNumber + 1] == ASTERISK);
  }

  private static boolean cancelingRegexSequence(char[] patternArray, int patternCharNumber) {
    return patternCharNumber + 1 < patternArray.length &&
            (patternArray[patternCharNumber] == BACK_SLASH
                    && patternArray[++patternCharNumber] == ASTERISK);
  }

  private static boolean stringAndPatternSlashes(char[] stringArray, int stringCharNumber, char[] patternArray, int patternCharNumber) {
    return stringArray[stringCharNumber] == BACK_SLASH
            && patternArray[patternCharNumber] == BACK_SLASH
            && (patternCharNumber + 1 < patternArray.length
            && patternArray[patternCharNumber + 1] == ASTERISK);
  }

  private static boolean anyCharAllowed(char[] patternArray, int patternCharNumber) {
    return patternArray[patternCharNumber] == ASTERISK
            && previousCharNotSlash(patternArray, patternCharNumber);
  }

  private static boolean previousCharNotSlash(char[] patternArray, int patternCharNumber) {
    if (patternCharNumber - 1 > -1) {
      return patternArray[patternCharNumber - 1] != BACK_SLASH;
    } return true;
  }
}
