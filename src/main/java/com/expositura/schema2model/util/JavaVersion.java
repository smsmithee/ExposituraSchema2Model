/**
 * Copyright © 2010-2020 Nokia
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.expositura.schema2model.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import org.apache.commons.lang3.Strings;

/**
 * Determines the java version. In particular it handles the old way of versioning in java (1.8 for example versus 11).
 */
public class JavaVersion {

  private static final Pattern JAVA_VERSION_1_X = Pattern.compile("(^1.\\d+)");
  private static final Pattern JAVA_VERSION_X = Pattern.compile("(^\\d+)");

  /**
   * Constructs a new instance of this class. All methods are static so not necessary to construct this class.
   */
  public JavaVersion() {
  }
  
  /**
   * Parse java version in either 1.x format or X (aka 9, 11, etc) format.
   * @param version The raw java version.
   * @return The cleaned up java version (removes '1.' if necessary).
   */
  public static String parse(String version) {
    if (Strings.CS.startsWith(version, "1.")) {
      Matcher m = JAVA_VERSION_1_X.matcher(version);
      m.find();
      return m.group();
    } else {
      Matcher m = JAVA_VERSION_X.matcher(version);
      m.find();
      return m.group();
    }
  }

  /**
   * Determine if this java version is Java 9 or later.
   * @param targetVersion The java version.
   * @return True if the version is Java 9+, otherwise false.
   */
  public static boolean is9OrLater(final String targetVersion) {
    if (isNotBlank(targetVersion)) {
      final Double v = Double.valueOf(targetVersion);
      return (v >= 9) || (v < 2 && v >= 1.9);
    } else {
      return false;
    }
  }

}
