/* Copyright (c) 2018 JSONx
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * You should have received a copy of The MIT License (MIT) along with this
 * program. If not, see <http://opensource.org/licenses/MIT/>.
 */

package org.jsonx;

final class RequiredNotNullableCase extends FailureCase<PropertyTrial<? super Object>> {
  static final RequiredNotNullableCase CASE = new RequiredNotNullableCase();

  @Override
  void onEncode(final PropertyTrial<? super Object> trial, final EncodeException e) throws Exception {
    assertEquals("Property \"" + trial.name + "\" is required: " + trial.field.getDeclaringClass().getName() + "#" + trial.field.getName(), e.getMessage());
  }

  @Override
  boolean onDecode(final PropertyTrial<? super Object> trial, final DecodeException e) throws Exception {
    final String message = e.getMessage();
    final int q1 = message.indexOf('"');
    assertNotEquals(-1, q1);

    final int q2 = message.indexOf('"', q1 + 1);
    assertNotEquals(-1, q2);

    final String propertyName = message.substring(q1 + 1, q2);
    assertTrue((trial.name.equals(propertyName) || trial.name.matches(propertyName)) && message.contains("\" is required: "));
    return true;
  }

  private RequiredNotNullableCase() {
  }
}