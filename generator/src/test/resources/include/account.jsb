{
  "@ns": "http://www.jsonx.org/binding-0.5.jsd",
  "@schemaLocation": "http://www.jsonx.org/binding-0.5.jsd http://www.jsonx.org/binding.jsd",
  "@schema": {
    "@ns": "http://www.jsonx.org/include-0.5.jsd",
    "href": "account.jsd"
  },
  "id": {
    "@": "string",
    "java": {
      "@decode": "org.libj.lang.Strings.toUuidOrNull",
      "@type": "java.util.UUID"
    }
  }
}