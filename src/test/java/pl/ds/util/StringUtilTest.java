package pl.ds.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.ds.util.StringUtil.ASTERISK;
import static pl.ds.util.StringUtil.BACK_SLASH;

class StringUtilTest {

  @Test
  void isSubstring() {
    assertTrue(StringUtil.contains("abcd", "bc"));
  }

  @Test
  void nullStringIsFalse() {
    assertFalse(StringUtil.contains(null, "*"));
  }

  @Test
  void nullPatternIsFalse() {
    assertFalse(StringUtil.contains("*", null));
  }

  @Test
  void stringAndPatternEmptyIsTrue() {
    assertTrue(StringUtil.contains("", ""));
  }

  @Test
  void patternSmallerThanString() {
    assertTrue(StringUtil.contains("abc", "a"));
  }

  @Test
  void patternContainsRegex() {
    assertTrue(StringUtil.contains("abc", "a*"));
  }

  @Test
  void patternContainsCancelingRegexSeq() {
    assertTrue(StringUtil.contains("a*c", buildPattern('a', BACK_SLASH, ASTERISK)));
  }

  @Test
  void stringContainsSpecialChar() {
    assertFalse(StringUtil.contains("a*c", "abc"));
  }

  @Test
  void patternCaseSensitivity() {
    assertFalse(StringUtil.contains("abc", "A"));
  }

  @Test
  void patternCancelingRegexAndRegex() {
    assertTrue(StringUtil.contains("a*cd", buildPattern('a', BACK_SLASH, ASTERISK, ASTERISK)));
  }

  @Test
  void patternRegexAndCancelingRegexWithSimpleChar() {
    assertTrue(StringUtil.contains("a*cd", buildPattern('a', BACK_SLASH, ASTERISK, ASTERISK, 'd')));
  }

  @Test
  void patternWithMultipleCancelingRegex() {
    assertTrue(StringUtil.contains("a**d", buildPattern('a', BACK_SLASH, ASTERISK, BACK_SLASH, ASTERISK)));
  }

  @Test
  void patternCancelingRegexAndRegexAndSimpleChar() {
    assertTrue(StringUtil.contains("a**de", buildPattern('a', BACK_SLASH, ASTERISK, BACK_SLASH, ASTERISK, ASTERISK, 'e')));
  }

  @Test
  void stringStartsFromAsteriskAndPatternCancelingRegexAndRegexPlusSimpleChar() {
    assertTrue(StringUtil.contains("**de", buildPattern(BACK_SLASH, ASTERISK, BACK_SLASH, ASTERISK, ASTERISK, 'e')));
  }

  @Test
  void patternCancelingRegexAndSimpleCharAndRegex() {
    assertTrue(StringUtil.contains("a**de", buildPattern('a', BACK_SLASH, ASTERISK, BACK_SLASH, ASTERISK, 'd', ASTERISK)));
  }

  @Test
  void patternSlashAsSimpleCharMixWithRegex() {
    assertTrue(StringUtil.contains("a" + BACK_SLASH + "cd", buildPattern('a', BACK_SLASH, 'c', ASTERISK)));
  }

  @Test
  void patternAllPossibleCombinations() {
    assertTrue(StringUtil.contains("a**d" + BACK_SLASH + "e/1", buildPattern('a', BACK_SLASH, ASTERISK, ASTERISK, 'd', BACK_SLASH, 'e', ASTERISK, '1')));
  }

  @Test
  void patternAllPossibleCombinationsComplexSeq() {
    assertTrue(StringUtil.contains("unexpectedChars_a**d" + BACK_SLASH + "e/1_unexpectedChars", buildPattern('a', BACK_SLASH, ASTERISK, ASTERISK, 'd', BACK_SLASH, 'e', ASTERISK, '1')));
  }

  @Test
  void patternAllPossibleCombinationsComplexSeqEndOfString() {
    assertTrue(StringUtil.contains("unexpectedChars_a1*de_unexpectedChars_a**de\\", buildPattern('a', BACK_SLASH, ASTERISK, BACK_SLASH, ASTERISK, 'd', ASTERISK, BACK_SLASH)));
  }

  @Test
  void test7() {
    Assertions.assertTrue(StringUtil.contains("a*bcd\\", "a*d\\"));
  }

  @Test
  void test15() {
    Assertions.assertTrue(StringUtil.contains("\\abcde*sdaf\\*s", "\\a*\\*sda"));
  }

  @Test
  void test16() {
    Assertions.assertTrue(StringUtil.contains("test", "*t*"));
  }

  @Test
  void test19() {
    Assertions.assertTrue(StringUtil.contains("test", "*t**e**s*****t*"));
  }

  @Test
  void test20() {
    Assertions.assertTrue(StringUtil.contains("a*aaa*a", "*a\\*"));
  }

  @Test
  void test27() {
    Assertions.assertFalse(StringUtil.contains("", " "));
  }

  @Test
  void test30() {
    Assertions.assertTrue(StringUtil.contains("lorem ipsum", " i*m"));
  }

  @Test
  void test33() {
    Assertions.assertTrue(StringUtil.contains("    lipsum", "* * lipsum*"));
  }

  @Test
  void test34() {
    Assertions.assertTrue(StringUtil.contains("\\lorem ipsum *", "*m \\*"));
  }

  @Test
  void test38() {
    Assertions.assertFalse(StringUtil.contains("lorem\\ipsum", "lorem\\*ipsum"));
  }

  @Test
  void test40() {
    Assertions.assertTrue(StringUtil.contains("dynamic", "***"));
  }

  @Test
  void test43() {
    Assertions.assertTrue(StringUtil.contains(" dy\\*nami c", "*dy\\\\**"));
  }

  @Test
  void test44() {
    Assertions.assertTrue(StringUtil.contains(" dy\\*nami c", "*dy*\\*nami c"));
  }

  @Test
  void test47() {
    Assertions.assertTrue(StringUtil.contains("123", "*"));
  }

  @Test
  void test51() {
    Assertions.assertTrue(StringUtil.contains("123", "*******"));
  }

  @Test
  void test53() {
    Assertions.assertTrue(StringUtil.contains("123*45", "1*5"));
  }

  @Test
  void test54() {
    Assertions.assertTrue(StringUtil.contains("123*45", "1*\\*4**"));
  }

  @Test
  void test56() {
    Assertions.assertTrue(StringUtil.contains("\\\\", "*"));
  }

  @Test
  void test57() {
    Assertions.assertFalse(StringUtil.contains("\\\\", "\\*"));
  }

  @Test
  void test58() {
    Assertions.assertTrue(StringUtil.contains("it is very long long long long long long test", "it * test"));
  }

  @Test
  void test59() {
    Assertions.assertTrue(StringUtil.contains("1", "**"));
  }

  @Test
  void test66() {
    Assertions.assertTrue(StringUtil.contains("!@# !@# #@!", "*"));
  }

  @Test
  void test68() {
    Assertions.assertTrue(StringUtil.contains("ZAQwsx@ #@", "Z*w* #@"));
  }

  @Test
  void test69() {
    Assertions.assertTrue(StringUtil.contains("!@#/#@!", "*/**"));
  }

  @Test
  void test70() {
    Assertions.assertTrue(StringUtil.contains("!@#/#@!", "*/#*"));
  }

  @Test
  void test73() {
    Assertions.assertTrue(StringUtil.contains(" *  @", " \\**@*"));
  }

  @Test
  void test78() {
    Assertions.assertTrue(StringUtil.contains("arr!", "*!"));
  }

  @Test
  void test80() {
    Assertions.assertTrue(StringUtil.contains("a * rr !", "a \\** !"));
  }

  @Test
  void test89() {
    Assertions.assertTrue(StringUtil.contains("*** ", "\\**\\*\\*"));
  }

  @Test
  void test90() {
    Assertions.assertTrue(StringUtil.contains("a*rr\\*", "a\\**\\*"));
  }

  @Test
  void test97() {
    Assertions.assertTrue(StringUtil.contains("zAq!@wSXxq\\", "q!*q\\"));
  }

  @Test
  void test98() {
    Assertions.assertTrue(StringUtil.contains("q!@wSXxq\\", "q!*q\\"));
  }

  @Test
  void test102() {
    Assertions.assertTrue(StringUtil.contains("a*bcd\\", "a*d\\"));
  }

  @Test
  void test107() {
    Assertions.assertFalse(StringUtil.contains("", "*t**e**s*****t*"));
  }

  @Test
  void test8() {
    Assertions.assertFalse(StringUtil.contains("a*bcd\\", "a\\*c"));
  }

  @Test
  void test10() {
    Assertions.assertFalse(StringUtil.contains("a*bcd\\", "\\*d"));
  }

  @Test
  void test11() {
    Assertions.assertFalse(StringUtil.contains("a*bcd\\", "a\\*d"));
  }

  @Test
  void test17() {
    Assertions.assertTrue(StringUtil.contains("", "*"));
  }

  @Test
  void test26() {
    Assertions.assertTrue(StringUtil.contains(" ", ""));
  }

  @Test
  void test42() {
    Assertions.assertTrue(StringUtil.contains("dynamic", ""));
  }

  @Test
  void test64() {
    Assertions.assertFalse(StringUtil.contains("*1", "\\*1\\*"));
  }

  @Test
  void test74() {
    Assertions.assertFalse(StringUtil.contains(" *  @", " \\**@\\*"));
  }

  @Test
  void test96() {
    Assertions.assertFalse(StringUtil.contains("zAq!@wSXxq\\", "q!*Q"));
  }

  @Test
  void test106() {
    Assertions.assertTrue(StringUtil.contains("", "*"));
  }

  String buildPattern(Character... params) {
    if (params.length == 0) {
      return "";
    }
    StringBuilder stringBuilder = new StringBuilder();
    for (Character param : params) {
      stringBuilder.append(param);
    }
    return stringBuilder.toString();
  }
}
