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

import static org.libj.lang.Assertions.*;

import java.io.Serializable;

import org.libj.lang.Classes;

public class Settings implements Serializable {
  public static final Settings DEFAULT = new Settings("", 1, true, long.class, Long.class, double.class, Double.class);

  public static class Builder {
    private String prefix = "";

    /**
     * Sets the class name prefix to be prepended to the names of generated bindings, and returns {@code this} builder.
     *
     * @param prefix The class name prefix to be prepended to the names of generated bindings.
     * @return {@code this} builder.
     */
    public Builder withPrefix(final String prefix) {
      this.prefix = assertNotNull(prefix);
      return this;
    }

    private int templateThreshold = 1;

    public Builder withTemplateThreshold(final int templateThreshold) {
      this.templateThreshold = templateThreshold;
      return this;
    }

    private boolean setBuilder = true;

    public Builder withSetBuilder(final boolean setBuilder) {
      this.setBuilder = setBuilder;
      return this;
    }

    private Class<?> integerPrimitive = long.class;

    /**
     * Sets the name of the primitive {@link Class} to be used as default type binding for \"number\" types with scale=0 &&
     * Use=REQUIRED && nullable=false, and returns {@code this} builder.
     *
     * @param integerPrimitive The primitive {@link Class} to be used as default type binding for \"number\" types with scale=0 &&
     *          Use=REQUIRED && nullable=false.
     * @return {@code this} builder.
     * @throws IllegalArgumentException If the provided {@code integerPrimitive} class name does not resolve to a {@link Class}, or
     *           if the resolved {@link Class} is not primitive.
     */
    public Builder withIntegerPrimitive(final String integerPrimitive) {
      if (integerPrimitive == null) {
        this.integerPrimitive = null;
      }
      else {
        this.integerPrimitive = assertNotNull(Classes.forNamePrimitiveOrNull(integerPrimitive));
        if (!this.integerPrimitive.isPrimitive())
          throw new IllegalArgumentException("integerPrimitive must be a primitive type: " + this.integerPrimitive.getCanonicalName());
      }

      return this;
    }

    private Class<?> integerObject = Long.class;

    /**
     * Sets the name of the non-primitive {@link Class} to be used as default type binding for \"number\" types with scale=0 &&
     * (Use=OPTIONAL || nullable=true), and returns {@code this} builder.
     *
     * @param integerObject The non-primitive {@link Class} to be used as default type binding for \"number\" types with scale=0 &&
     *          (Use=OPTIONAL || nullable=true).
     * @return {@code this} builder.
     * @throws IllegalArgumentException If the provided {@code integerObject} class name does not resolve to a {@link Class}, or if
     *           the resolved {@link Class} is primitive.
     */
    public Builder withIntegerObject(final String integerObject) {
      try {
        this.integerObject = Class.forName(integerObject, false, ClassLoader.getSystemClassLoader());
      }
      catch (final ClassNotFoundException e) {
        throw new IllegalArgumentException(e);
      }

      if (this.integerObject.isPrimitive())
        throw new IllegalArgumentException("integerObject must be a non-primitive type: " + this.integerObject.getCanonicalName());

      return this;
    }

    private Class<?> realPrimitive = double.class;

    /**
     * Sets the name of the primitive {@link Class} to be used as default type binding for \"number\" types with scale>0 &&
     * Use=REQUIRED && nullable=false, and returns {@code this} builder.
     *
     * @param realPrimitive The primitive {@link Class} to be used as default type binding for \"number\" types with scale>0 &&
     *          Use=REQUIRED && nullable=false.
     * @return {@code this} builder.
     * @throws IllegalArgumentException If the provided {@code realPrimitive} class name does not resolve to a {@link Class}, or if
     *           the resolved {@link Class} is not primitive.
     */
    public Builder withRealPrimitive(final String realPrimitive) {
      if (realPrimitive == null) {
        this.realPrimitive = null;
      }
      else {
        this.integerPrimitive = assertNotNull(Classes.forNamePrimitiveOrNull(realPrimitive));
        if (!this.realPrimitive.isPrimitive())
          throw new IllegalArgumentException("realPrimitive must be a primitive type: " + this.realPrimitive.getCanonicalName());
      }

      return this;
    }

    private Class<?> realObject = Double.class;

    /**
     * Sets the name of the non-primitive {@link Class} to be used as default type binding for \"number\" types with scale>0 &&
     * (Use=OPTIONAL || nullable=true), and returns {@code this} builder.
     *
     * @param realObject The non-primitive {@link Class} to be used as default type binding for \"number\" types with scale>0 &&
     *          (Use=OPTIONAL || nullable=true).
     * @return {@code this} builder.
     * @throws IllegalArgumentException If the provided {@code realObject} class name does not resolve to a {@link Class}, or if the
     *           resolved {@link Class} is primitive.
     */
    public Builder withRealObject(final String realObject) {
      try {
        this.realObject = Class.forName(realObject, false, ClassLoader.getSystemClassLoader());
      }
      catch (final ClassNotFoundException e) {
        throw new IllegalArgumentException(e);
      }

      if (this.realObject.isPrimitive())
        throw new IllegalArgumentException("realObject must be a non-primitive type: " + this.realObject.getCanonicalName());

      return this;
    }

    public Settings build() {
      return new Settings(prefix, templateThreshold, setBuilder, integerPrimitive, integerObject, realPrimitive, realObject);
    }
  }

  private final String prefix;
  private final int templateThreshold;
  private final boolean setBuilder;
  private final Class<?> integerPrimitive;
  private final Class<?> integerObject;
  private final Class<?> realPrimitive;
  private final Class<?> realObject;

  Settings(final String prefix, final int templateThreshold, final boolean setBuilder, final Class<?> integerPrimitive, final Class<?> integerObject, final Class<?> realPrimitive, final Class<?> realObject) {
    this.prefix = assertNotNull(prefix);
    this.templateThreshold = assertNotNegative(templateThreshold, "templateThreshold (" + templateThreshold + ") must be non-negative");
    this.setBuilder = setBuilder;
    this.integerPrimitive = integerPrimitive;
    if (integerPrimitive != null && !integerPrimitive.isPrimitive())
      throw new IllegalArgumentException("integerPrimitive must be a primitive type: " + integerPrimitive.getCanonicalName());

    this.integerObject = assertNotNull(integerObject);
    if (integerObject.isPrimitive())
      throw new IllegalArgumentException("integerObject must be a non-primitive type: " + integerObject.getCanonicalName());

    this.realPrimitive = realPrimitive;
    if (realPrimitive != null && !realPrimitive.isPrimitive())
      throw new IllegalArgumentException("realPrimitive must be a primitive type: " + realPrimitive.getCanonicalName());

    this.realObject = assertNotNull(realObject);
    if (realObject.isPrimitive())
      throw new IllegalArgumentException("realObject must be a non-primitive type: " + realObject.getCanonicalName());
  }

  public String getPrefix() {
    return prefix;
  }

  /**
   * Returns the non-negative number of referrers needed for a {@link Model} to be declared as a template root member of the jsonx
   * element.
   *
   * @return The non-negative number of referrers needed for a {@link Model} to be declared as a template root member of the jsonx
   *         element.
   */
  public int getTemplateThreshold() {
    return templateThreshold;
  }

  public boolean getSetBuilder() {
    return setBuilder;
  }

  public Class<?> getIntegerPrimitive() {
    return integerPrimitive;
  }

  public Class<?> getIntegerObject() {
    return integerObject;
  }

  public Class<?> getRealPrimitive() {
    return realPrimitive;
  }

  public Class<?> getRealObject() {
    return realObject;
  }
}