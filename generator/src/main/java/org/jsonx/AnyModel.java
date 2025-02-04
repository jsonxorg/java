/* Copyright (c) 2019 JSONx
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

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.RandomAccess;

import org.jsonx.www.binding_0_5.xL1gluGCXAA.$FieldBinding;
import org.jsonx.www.schema_0_5.xL0gluGCXAA.$Any;
import org.jsonx.www.schema_0_5.xL0gluGCXAA.$AnyMember;
import org.jsonx.www.schema_0_5.xL0gluGCXAA.$Array;
import org.jsonx.www.schema_0_5.xL0gluGCXAA.$ArrayMember;
import org.jsonx.www.schema_0_5.xL0gluGCXAA.$Documented;
import org.jsonx.www.schema_0_5.xL0gluGCXAA.$Member;
import org.jsonx.www.schema_0_5.xL0gluGCXAA.$ObjectMember;
import org.jsonx.www.schema_0_5.xL0gluGCXAA.$Reference;
import org.jsonx.www.schema_0_5.xL0gluGCXAA.$Reference.Name$;
import org.libj.lang.Classes;
import org.libj.lang.IllegalAnnotationException;
import org.libj.lang.Strings;
import org.openjax.json.JsonUtil;
import org.openjax.xml.api.XmlElement;
import org.w3.www._2001.XMLSchema.yAA.$AnyType;

final class AnyModel extends Referrer<AnyModel> {
  private static $Any property(final schema.AnyProperty jsd, final String name) {
    final $Any xsb = new $Any() {
      @Override
      protected $Member inherits() {
        return new $ObjectMember.Property();
      }
    };

    if (name != null)
      xsb.setNames$(new $Any.Names$(JsonUtil.unescape(name).toString()));

    final String types = jsd.get40types();
    if (types != null)
      xsb.setTypes$(new $Any.Types$(Strings.split(types, ' ')));

    final Boolean nullable = jsd.get40nullable();
    if (nullable != null)
      xsb.setNullable$(new $Any.Nullable$(nullable));

    final String use = jsd.get40use();
    if (use != null)
      xsb.setUse$(new $Any.Use$($Any.Use$.Enum.valueOf(use)));

    return xsb;
  }

  private static $ArrayMember.Any element(final schema.AnyElement jsd) {
    final $ArrayMember.Any xsb = new $ArrayMember.Any();

    final String types = jsd.get40types();
    if (types != null)
      xsb.setTypes$(new $ArrayMember.Any.Types$(Strings.split(types, ' ')));

    final Boolean nullable = jsd.get40nullable();
    if (nullable != null)
      xsb.setNullable$(new $ArrayMember.Any.Nullable$(nullable));

    final String minOccurs = jsd.get40minOccurs();
    if (minOccurs != null)
      xsb.setMinOccurs$(new $ArrayMember.Any.MinOccurs$(new BigInteger(minOccurs)));

    final String maxOccurs = jsd.get40maxOccurs();
    if (maxOccurs != null)
      xsb.setMaxOccurs$(new $ArrayMember.Any.MaxOccurs$(maxOccurs));

    // There is no element binding for "any"

    return xsb;
  }

  static $AnyMember jsdToXsb(final schema.Any jsd, final String name) {
    final $AnyMember xsb;
    if (jsd instanceof schema.AnyProperty)
      xsb = property((schema.AnyProperty)jsd, name);
    else if (jsd instanceof schema.AnyElement)
      xsb = element((schema.AnyElement)jsd);
    else
      throw new UnsupportedOperationException("Unsupported type: " + jsd.getClass().getName());

    final String doc = jsd.get40doc();
    if (doc != null && doc.length() > 0)
      xsb.setDoc$(new $Documented.Doc$(doc));

    return xsb;
  }

  static Reference referenceOrDeclare(final Registry registry, final Referrer<?> referrer, final AnyProperty property, final Method getMethod, final String fieldName) {
    final AnyModel model = new AnyModel(registry, referrer, property, getMethod, fieldName);
    final Id id = model.id();
    final AnyModel registered = (AnyModel)registry.getModel(id);
    return new Reference(registry, referrer, property.name(), property.nullable(), property.use(), fieldName, model.typeBinding, registered == null ? registry.declare(id).value(model, referrer) : registry.reference(registered, referrer));
  }

  static Reference referenceOrDeclare(final Registry registry, final Referrer<?> referrer, final AnyElement element) {
    final AnyModel model = new AnyModel(registry, referrer, element);
    final Id id = model.id();
    final AnyModel registered = (AnyModel)registry.getModel(id);
    return new Reference(registry, referrer, element.nullable(), element.minOccurs(), element.maxOccurs(), registered == null ? registry.declare(id).value(model, referrer) : registry.reference(registered, referrer));
  }

  static AnyModel reference(final Registry registry, final Referrer<?> referrer, final $Array.Any xsb, final IdentityHashMap<$AnyType<?>,$FieldBinding> xsbToBinding) {
    return registry.reference(new AnyModel(registry, referrer, xsb, xsbToBinding), referrer);
  }

  static AnyModel reference(final Registry registry, final Referrer<?> referrer, final $Any xsb, final IdentityHashMap<$AnyType<?>,$FieldBinding> xsbToBinding) {
    return registry.reference(new AnyModel(registry, referrer, xsb, xsbToBinding), referrer);
  }

  private static final Name$ anonymousReferenceName = new Name$("");

  private static $Reference newRnonymousReference(final Boolean nullable, Use use) {
    return new $Reference() {
      @Override
      protected $Member inherits() {
        return null;
      }

      @Override
      public Name$ getName$() {
        return anonymousReferenceName;
      }

      @Override
      public Nullable$ getNullable$() {
        return nullable == null ? null : new Nullable$(nullable);
      }

      @Override
      public Use$ getUse$() {
        return use == null ? null : new Use$(use == Use.REQUIRED ? Use$.required : Use$.optional);
      }
    };
  }

  private final Member[] types;

  private AnyModel(final Registry registry, final Declarer declarer, final $Any xsb, final IdentityHashMap<$AnyType<?>,$FieldBinding> xsbToBinding) {
    super(registry, declarer, xsb.getDoc$(), xsb.getNames$(), xsb.getNullable$(), xsb.getUse$(), null, getField(xsbToBinding, xsb), getType(registry, xsbToBinding, xsb));
    this.types = getTypes(nullable.get, use.get, xsb.getTypes$(), xsbToBinding == null ? null : xsbToBinding.get(xsb));
    validateTypeBinding();
  }

  private AnyModel(final Registry registry, final Declarer declarer, final $Array.Any xsb, final IdentityHashMap<$AnyType<?>,$FieldBinding> xsbToBinding) {
    super(registry, declarer, xsb.getDoc$(), xsb.getNullable$(), xsb.getMinOccurs$(), xsb.getMaxOccurs$(), null);
    this.types = getTypes(nullable.get, use.get, xsb.getTypes$(), xsbToBinding == null ? null : ($FieldBinding)xsbToBinding.get(xsb));
    validateTypeBinding();
  }

  private AnyModel(final Registry registry, final Declarer declarer, final AnyProperty property, final Method getMethod, final String fieldName) {
    super(registry, declarer, property.nullable(), property.use(), null, null, Bind.Type.from(registry, getMethod, property.nullable(), property.use(), null, null, Void.class));
    final t[] types = property.types();
    this.types = getMemberTypes(types);
    final boolean isRegex = isMultiRegex(property.name());
    if (isRegex && !Map.class.isAssignableFrom(getMethod.getReturnType()))
      throw new IllegalAnnotationException(property, getMethod.getDeclaringClass().getName() + "." + fieldName + ": @" + AnyProperty.class.getSimpleName() + " of type " + Bind.Type.getClassName(getMethod, property.nullable(), property.use()) + " with regex name=\"" + property.name() + "\" must be of type that extends " + Map.class.getName());

    if (!isAssignable(getMethod, true, isRegex, property.nullable(), property.use(), false, getFieldClasses(types)))
      throw new IllegalAnnotationException(property, getMethod.getDeclaringClass().getName() + "." + fieldName + ": @" + AnyProperty.class.getSimpleName() + " of type " + Bind.Type.getClassName(getMethod, property.nullable(), property.use()) + " is not assignable for the specified types attribute");

    validateTypeBinding();
  }

  private AnyModel(final Registry registry, final Declarer declarer, final AnyElement element) {
    super(registry, declarer, element.nullable(), null, null, null, null);
    this.types = getMemberTypes(element.types());
    validateTypeBinding();
  }

  private Member[] getTypes(final Boolean nullable, final Use use, final $AnyMember.Types$ refs, final $FieldBinding binding) {
    final List<String> idrefs;
    final int size;
    if (refs == null || (size = (idrefs = refs.text()).size()) == 0)
      return null;

    final Member[] types = new Member[size];
    int i = 0;
    if (idrefs instanceof RandomAccess) {
      do // [RA]
        types[i] = addReference(idrefs.get(i), nullable, use, binding);
      while (++i < size);
    }
    else {
      final Iterator<String> it = idrefs.iterator();
      do // [I]
        types[i] = addReference(it.next(), nullable, use, binding);
      while (++i < size);
    }

    return types;
  }

  private Member addReference(final String idref, final Boolean nullable, final Use use, final $FieldBinding binding) {
    final Id id = Id.hashed(idref);
    return Reference.defer(registry, this, newRnonymousReference(nullable, use), binding, () -> {
      final Member model = registry.getModel(id);
      if (model == null)
        throw new IllegalStateException("Type id=\"" + id + "\" in <any> not found");

      return (Model)registry.reference(model, this);
    });
  }

  private Class<?>[] getFieldClasses(final t[] types) {
    final int len = types.length;
    if (len == 0)
      return new Class[] { defaultClass() };

    final Class<?>[] classes = new Class<?>[len];
    int i = 0;
    do { // [A]
      final t type = types[i];
      if (AnyType.isEnabled(type.arrays()))
        classes[i] = List.class;
      else if (AnyType.isEnabled(type.booleans()))
        classes[i] = type.booleans().type();
      else if (AnyType.isEnabled(type.numbers()))
        classes[i] = type.numbers().type();
      else if (AnyType.isEnabled(type.objects()))
        classes[i] = type.objects();
      else if (AnyType.isEnabled(type.strings()))
        classes[i] = type.strings().type();
    }
    while (++i < len);

    return classes;
  }

  private Member[] getMemberTypes(final t[] types) {
    final int len = types.length;
    if (len == 0)
      return null;

    final Member[] members = new Member[len];
    int i = 0;
    do { // [A]
      final t type = types[i];
      Member member = null;
      if (AnyType.isEnabled(type.arrays()))
        member = ArrayModel.referenceOrDeclare(registry, this, type.arrays());

      if (AnyType.isEnabled(type.booleans())) {
        if (member != null)
          throw new ValidationException("@" + t.class.getName() + " specifies 2 types: \"booleans\" and \"" + member.elementName() + "s\"");

        member = BooleanModel.referenceOrDeclare(registry, this, type.booleans());
      }

      if (AnyType.isEnabled(type.numbers())) {
        if (member != null)
          throw new ValidationException("@" + t.class.getName() + " specifies 2 types: \"numbers\" and \"" + member.elementName() + "s\"");

        member = NumberModel.referenceOrDeclare(registry, this, type.numbers());
      }

      if (AnyType.isEnabled(type.objects())) {
        if (member != null)
          throw new ValidationException("@" + t.class.getName() + " specifies 2 types: \"objects\" and \"" + member.elementName() + "s\"");

        member = ObjectModel.referenceOrDeclare(registry, this, new ObjectElement() {
          @Override
          public Class<? extends Annotation> annotationType() {
            return ObjectElement.class;
          }

          @Override
          public Class<? extends JxObject> type() {
            return type.objects();
          }

          @Override
          public boolean nullable() {
            return true;
          }

          @Override
          public int minOccurs() {
            return 1;
          }

          @Override
          public int maxOccurs() {
            return Integer.MAX_VALUE;
          }

          @Override
          public int id() {
            return -1;
          }
        });
      }

      if (AnyType.isEnabled(type.strings())) {
        if (member != null)
          throw new ValidationException("@" + t.class.getName() + " specifies 2 types: \"strings\" and \"" + member.elementName() + "s\"");

        member = StringModel.referenceOrDeclare(registry, this, new StringElement() {
          @Override
          public Class<? extends Annotation> annotationType() {
            return StringElement.class;
          }

          @Override
          public int id() {
            return -1;
          }

          @Override
          public boolean nullable() {
            return true;
          }

          @Override
          public String pattern() {
            return type.strings().pattern();
          }

          @Override
          public int minOccurs() {
            return 1;
          }

          @Override
          public int maxOccurs() {
            return Integer.MAX_VALUE;
          }

          @Override
          public Class<?> type() {
            return type.strings().type();
          }

          @Override
          public String decode() {
            return type.strings().decode();
          }

          @Override
          public String encode() {
            return type.strings().encode();
          }
        }).model;
      }

      if (member == null)
        throw new ValidationException("@" + t.class.getName() + " does not specify a type");

      members[i] = member;
    }
    while (++i < len);

    return members;
  }

  @Override
  void resolveOverrides() {
  }

  private Registry.Type type;

  @Override
  Registry.Type typeDefault(final boolean primitive) {
    return type == null ? type = (this.types == null ? registry.OBJECT : getGreatestCommonSuperType(this.types)) : type;
  }

  @Override
  String isValid(final Bind.Type typeBinding) {
    return null;
  }

  @Override
  String nameName() {
    return "names";
  }

  @Override
  public String elementName() {
    return "any";
  }

  @Override
  Class<?> defaultClass() {
    return Object.class;
  }

  @Override
  Class<? extends Annotation> propertyAnnotation() {
    return AnyProperty.class;
  }

  @Override
  Class<? extends Annotation> elementAnnotation() {
    return AnyElement.class;
  }

  @Override
  XmlElement toXml(final Element owner, final String packageName, final JsonPath.Cursor cursor, final PropertyMap<AttributeMap> pathToBinding) {
    final XmlElement element = super.toXml(owner, packageName, cursor, pathToBinding);
    if (cursor != null)
      cursor.popName();

    return element;
  }

  @Override
  PropertyMap<Object> toJson(final Element owner, final String packageName, final JsonPath.Cursor cursor, final PropertyMap<AttributeMap> pathToBinding) {
    final PropertyMap<Object> properties = super.toJson(owner, packageName, cursor, pathToBinding);
    if (cursor != null)
      cursor.popName();

    return properties;
  }

  @Override
  AttributeMap toSchemaAttributes(final Element owner, final String packageName, final boolean jsd) {
    final AttributeMap attributes = super.toSchemaAttributes(owner, packageName, jsd);
    if (types != null) {
      final StringBuilder b = new StringBuilder();
      for (int i = 0, i$ = types.length; i < i$; ++i) { // [A]
        if (i > 0)
          b.append(' ');

        b.append(Registry.getSubName(types[i].id().toString(), packageName));
      }

      attributes.put(jsd(jsd, "types"), b.toString());
    }

    return attributes;
  }

  @Override
  void toAnnotationAttributes(final AttributeMap attributes, final Member owner) {
    super.toAnnotationAttributes(attributes, owner);
    if (types == null)
      return;

    final ArrayList<String> values = new ArrayList<>();
    attributes.put("types", values);

    StringBuilder b = null;
    for (int i = 0, i$ = types.length; i < i$; ++i) { // [A]
      final Member type = types[i];
      if (type instanceof ArrayModel) {
        values.add("@" + t.class.getName() + "(arrays = " + ((ArrayModel)type).classType().canonicalName + ".class)");
      }
      else if (type instanceof ObjectModel) {
        values.add("@" + t.class.getName() + "(objects = " + ((ObjectModel)type).classType().canonicalName + ".class)");
      }
      else if (type instanceof BooleanModel || type instanceof NumberModel || type instanceof StringModel) {
        final Model model = (Model)type;

        final AttributeMap attrs = new AttributeMap();
        model.toAnnotationAttributes(attrs, this);
        if (b == null)
          b = new StringBuilder();
        else
          b.setLength(0);

        final Class<? extends Annotation> annotationType = type.typeAnnotation();
        b.append('@').append(annotationType.getName());
        if (attrs.size() > 0) {
          b.append('(');
          final Iterator<Map.Entry<String,Object>> iterator = attrs.entrySet().iterator();
          boolean addComma = false;
          while (iterator.hasNext()) { // [I]
            final Map.Entry<String,Object> entry = iterator.next();
            final String key = entry.getKey();
            final Object value = entry.getValue();
            final Object defaultValue = Classes.getMethod(annotationType, key).getDefaultValue();
            addComma = AnnotationType.appendAttribute(b, key, value, defaultValue, addComma);
          }

          b.append(')');
        }

        values.add("@" + t.class.getName() + "(" + type.elementName() + "s=" + b + ")");
      }
      else {
        throw new UnsupportedOperationException("Unsupported type: " + type.getClass().getName());
      }
    }
  }

  @Override
  ArrayList<AnnotationType> getClassAnnotation() {
    return null;
  }

  @Override
  String toSource(final Settings settings) {
    return null;
  }

  private boolean referencesResolved;

  @Override
  void resolveReferences() {
    if (referencesResolved)
      return;

    referencesResolved = true;
    if (types == null)
      return;

    for (int i = 0, i$ = types.length; i < i$; ++i) { // [A]
      Member member = types[i];
      if (member instanceof Deferred)
        member = ((Deferred<?>)member).resolve();

      if (member instanceof Reference)
        member = ((Reference)member).model;

      types[i] = member;
    }
  }
}