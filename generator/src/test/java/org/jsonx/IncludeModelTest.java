/* Copyright (c) 2023 JSONx
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

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.libj.test.JUnitUtil;

@RunWith(Parameterized.class)
public class IncludeModelTest extends ModelTest {
  private static final String path = "href/";

  private static void test(final String name) throws IOException {
    final String namespace = "urn:test:" + name;
    final String packageName = "org.jsonx.href." + name;
    final Settings settings = new Settings.Builder().withNamespacePackage(namespace, packageName + ".").build();

    if (logger.isInfoEnabled()) logger.info(name + "...");

    final SchemaElement jsbxSchema = Generator.parse(settings, ClassLoader.getSystemClassLoader().getResource(path + name + ".jsbx"))[0];
    final Map<String,String> jsbxSources = jsbxSchema.toSource();

    final SchemaElement jsbSchema = Generator.parse(settings, ClassLoader.getSystemClassLoader().getResource(path + name + ".jsb"))[0];
    final Map<String,String> jsbSources = jsbSchema.toSource();

    assertEquals(jsbxSources, jsbSources);
  }

  @Parameterized.Parameters(name = "{0}")
  public static URL[] resources() throws IOException {
    return JUnitUtil.sortBySize(JUnitUtil.getResources("include", ".*\\.js[db]x"));
  }

  @Parameterized.Parameter(0)
  public URL resource;

  @Test
  public void test() throws IOException {
    test("account");
  }
}