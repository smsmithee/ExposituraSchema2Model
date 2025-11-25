# ExposituraSchema2Model
This is a fork and update to the jsonschema2pojo project found at https://github.com/joelittlejohn/jsonschema2pojo
This version only includes the maven plugin and has the following major changes:
* Strips String values sent to setters down to null using StringUtils.stripToNull from Apache Commons Lang3
* Setter sets to null if an empty collection is provided for the value
* For other model objects, setter checks if the object's isEmpty method returns true, if so then it sets field to null
* All model classes now produce an isEmpty method that checks if all fields are null
* 'Add' method is now added to builder for List and Map fields
* Removed some legacy Annotators, will eventually limit to just Jackson 2 and Gson

Generates Java types from JSON Schema (or example JSON) and can annotate those types for data-binding with Jackson 2.x 
or Gson.

An example:
```xml
<plugin>
  <groupId>io.github.smsmithee.expositura</groupId>
  <artifactId>schema2model-maven-plugin</artifactId>
  <version>0.2.0</version>
  <configuration>
    <sourceDirectory>${basedir}/src/main/resources/schema</sourceDirectory>
    <targetPackage>com.example.model</targetPackage>
    <generateBuilders>true</generateBuilders>
    <initializeCollections>false</initializeCollections>
    <useInnerClassBuilders>true</useInnerClassBuilders>
    <sourceSortOrder>SUBDIRS_FIRST</sourceSortOrder>
    <useLongIntegers>true</useLongIntegers>
  </configuration>
  <executions>
    <execution>
      <goals>
        <goal>generate</goal>
      </goals>
    </execution>
  </executions>
</plugin>
```

Configuration Options

| Name | Type | Default | Description |
|---|---|---|---|
| addCompileSourceRoot | boolean | true | Add the output directory to the project as a source root, so that the generated java types are compiled and included in the project artifact |
| <annotationStyle>| String | jackson2 | The style of annotations to use in the generated Java types. Supports jackson2, gson, and none |
| <classNamePrefix> | String | null | Whether to add a prefix to generated classes |
| <classNameSuffix> | String | null | Whether to add a suffix to generated classes |
| <constructorsRequiredPropertiesOnly> | boolean | false | The 'constructorsRequiredPropertiesOnly' configuration option. This is a legacy configuration option used to turn on isIncludeRequiredPropertiesConstructor() and off the isIncludeAllPropertiesConstructor() configuration options. It is specifically tied to the isIncludeConstructors() property, and will do nothing if that property is not enabled |
| <customAnnotator> | String | com.expositura.schema2model | A fully qualified class name, referring to a custom annotator class that implements org.jsonschema2pojo.Annotator and will be used in addition to the one chosen by annotationStyle. If you want to use the custom annotator alone, set annotationStyle to none |
| <customDatePattern> | String | null | A custom pattern to use when formatting date fields during serialization. Requires support from your JSON binding library |
| <customDateTimePattern> | String | null | A custom pattern to use when formatting date-time fields during serialization. Requires support from your JSON binding library.
| <customRuleFactory> | String | com.expositura.rules.RuleFactory | A fully qualified class name, referring to an class that extends org.jsonschema2pojo.rules.RuleFactory and will be used to create instances of Rules used for code generation |
| <customTimePattern> | String | null | A custom pattern to use when formatting time fields during serialization. Requires support from your JSON binding library |
| <dateTimeType> | String | String | What type to use instead of string when adding string type fields of format date-time to generated Java types.
| <dateType> | String | String | What type to use instead of string when adding string type fields of format date (not date-time) to generated Java types.
| <excludes> | String[] | null | List of file patterns to exclude. This only applies to the initial scan of the file system and will not prevent inclusion through a "$ref" in one of the schemas.
| <fileExtensions> | String[] | null | The strings (no preceeding dot) that should be considered as file name extensions, and therefore ignored, when creating Java class names.
| <formatDateTimes> | boolean | false | Whether the fields of type `date` are formatted during serialization with a default pattern of yyyy-MM-dd'T'HH:mm:ss.SSSZ.
| <formatDates> | boolean | false | Whether the fields of type `date` are formatted during serialization with a default pattern of yyyy-MM-dd.
| <formatTimes> | boolean | false | Whether the fields of type `time` are formatted during serialization with a default pattern of HH:mm:ss.SSS.
| <formatTypeMapping> | Map<String,String> | null | (no description)
| <generateBuilders> | boolean | false | Whether to generate builder-style methods of the form withXxx(value) (that return this), alongside the standard, void-return setters.
| <includeAdditionalProperties> | boolean | true | Whether to allow 'additional properties' support in objects. Setting this to false will disable additional properties support, regardless of the input schema(s).
| <includeAllPropertiesConstructor> | boolean | true | The 'includeAllPropertiesConstructor' configuration option. This property works in collaboration with the isIncludeConstructors() configuration option and is incompatible with isConstructorsRequiredPropertiesOnly(), and will have no effect if isIncludeConstructors() is not set to true. If isIncludeConstructors() is set to true then this configuration determines whether the resulting object should include a constructor with all listed properties as parameters.
| <includeConstructorPropertiesAnnotation> | boolean | false | (no description)
| <includeConstructors> | boolean | false | Whether to generate constructors or not
| <includeCopyConstructor> | boolean | false | The 'includeCopyConstructor' configuration option. This property works in collaboration with the isIncludeConstructors() configuration option and is incompatible with isConstructorsRequiredPropertiesOnly(), and will have no effect if isIncludeConstructors() is not set to true. If isIncludeConstructors() is set to true then this configuration determines whether the resulting object should include a constructor the class itself as a parameter, with the expectation that all properties from the originating class will assigned to the new class.
| <includeDynamicAccessors> | boolean | false | Whether to include dynamic getters, setters, and builders or to omit these methods.
| <includeDynamicBuilders> | boolean | false | Whether to include dynamic builders or to omit these methods.
| <includeDynamicGetters> | boolean | false | Whether to include dynamic getters or to omit these methods.
| <includeDynamicSetters> | boolean | false | Whether to include dynamic setters or to omit these methods.
| <includeGeneratedAnnotation> | boolean | true | Whether to include a javax.annotation.Generated (Java 8 and lower) or javax.annotation.processing.Generated (Java 9+) in on generated types. See also: targetVersion.
| <includeGetters> | boolean | true | Whether to include getters or to omit this accessor method and create public fields instead
| <includeHashcodeAndEquals> | boolean | true | Whether to include hashCode and equals methods in generated Java types.
| <includeJsr303Annotations> | boolean | false | Whether to include JSR-303/349 annotations (for schema rules like minimum, maximum, etc) in generated Java types.<br>Schema rules and the annotation they produce:<br><br>maximum = @DecimalMax<br>minimum = @DecimalMin<br>minItems,maxItems = @Size<br>minLength,maxLength = @Size<br>pattern = @Pattern<br>required = @NotNull<br>Any Java fields which are an object or array of objects will be annotated with @Valid to support validation of an entire document tree.
| <includeJsr305Annotations> | boolean | false | Whether to include JSR-305 annotations (for schema rules like Nullable, NonNull, etc) in generated Java types.
| <includeRequiredPropertiesConstructor> | boolean | false | The 'includeRequiredPropertiesConstructor' configuration option. This property works in collaboration with the isIncludeConstructors() configuration option and is incompatible with isConstructorsRequiredPropertiesOnly(), and will have no effect if isIncludeConstructors() is not set to true. If isIncludeConstructors() is set to true then this configuration determines whether the resulting object should include a constructor with only the required properties as parameters.
| <includeSetters> | boolean | true | Whether to include setters or to omit this accessor method and create public fields instead
| <includeToString> | boolean | true | Whether to include a toString method in generated Java types.
| <includeTypeInfo> | boolean | false | Whether to include json type information; often required to support polymorphic type handling.<br>By default the type information is stored in the @class property, this can be overridden in the deserializationClassProperty of the schema.
| <includes> | String[] | null | List of file patterns to include.
| <inclusionLevel> | String | NON_NULL | The Level of inclusion to set in the generated Java types for Jackson serializers.<br>Supported values<br><br>ALWAYS<br>NON_ABSENT<br>NON_DEFAULT<br>NON_EMPTY<br>NON_NULL<br>USE_DEFAULTS
| <initializeCollections> | boolean | true | Whether to initialize Set and List fields as empty collections, or leave them as null.
| <outputDirectory> | File | ${project.build.directory}/generated-sources/schema2model | Target directory for generated Java source files.
| <outputEncoding> | String | UTF-8 | The character encoding that should be used when writing the generated Java source files.
| <parcelable> | boolean | false | Whether to make the generated types 'parcelable' (for Android development).
| <propertyWordDelimiters> | String | - _. | The characters that should be considered as word delimiters when creating Java Bean property names from JSON property names. If blank or not set, JSON properties will be considered to contain a single word when creating Java Bean property names.
| <refFragmentPathDelimiters> | String | #/.. | A string containing any characters that should act as path delimiters when resolving $ref fragments. By default, #, / and . are used in an attempt to support JSON Pointer and JSON Path.
| <removeOldOutput> | boolean | false | Whether to empty the target directory before generation occurs, to clear out all source files that have been generated previously.<br>Be warned, when activated this option will cause jsonschema2pojo to indiscriminately delete the entire contents of the target directory (all files and folders) before it begins generating sources.
| <serializable> | boolean | false | Whether to make the generated types 'serializable'.
| <skip> | boolean | false | Skip plugin execution (don't read/validate any schema files, don't generate any java types).
| <sourceDirectory> | String | null | Location of the JSON Schema file(s). Note: this may refer to a single file or a directory of files.
| <sourcePaths> | String[] | null | An array of locations of the JSON Schema file(s). Note: each item may refer to a single file or a directory of files.
| <sourceSortOrder> | String | OS | The sort order to be applied when recursively processing the source files. By default the OS can influence the processing order. Supported values:<br>OS (Let the OS influence the order the source files are processed.)<br>FILES_FIRST (Case sensitive sort, visit the files first. The source files are processed in a breadth first sort order.)<br>SUBDIRS_FIRST (Case sensitive sort, visit the sub-directories before the files. The source files are processed in a depth first sort order.)<br>
| <sourceType> | String | jsonschema | The type of input documents that will be read<br>Supported values:<br><br>jsonschema (schema documents, containing formal rules that describe the structure of JSON data)<br>json (documents that represent an example of the kind of JSON data that the generated Java types will be mapped to)<br>yamlschema (JSON schema documents, represented as YAML)<br>yaml (documents that represent an example of the kind of YAML (or JSON) data that the generated Java types will be mapped to)
| <targetPackage> | String | null | Package name used for generated Java classes (for types where a fully qualified name has not been supplied in the schema using the 'javaType' property).
| <targetVersion> | String | See Description | The target version for generated source files, used whenever decisions are made about generating source code that may be incompatible with older JVMs. Acceptable values include e.g. 1.6, 1.8, 8, 9, 10, 11.<br>If not set, the value of targetVersion is auto-detected. For auto-detection, the first value found in the following list will be used:<br>maven.compiler.source property<br>maven.compiler.release property<br>maven-compiler-plugin 'source' configuration option<br>maven-compiler-plugin 'release' configuration option<br>the current JVM version
| <timeType> | String | null | What type to use instead of string when adding string type fields of format time (not date-time) to generated Java types.
| <toStringExcludes> | String[] | null | The fields to be excluded from toString generation
| <useBigDecimals> | boolean | false | Whether to use the java type java.math.BigDecimal instead of float (or Float) when representing the JSON Schema type 'number'. Note that this configuration overrides isUseDoubleNumbers().
| <useBigIntegers> | boolean | false | Whether to use the java type java.math.BigInteger instead of int (or Integer) when representing the JSON Schema type 'integer'. Note that this configuration overrides isUseLongIntegers().
| <useCommonsLang3> | boolean | false | Whether to use commons-lang 3.x imports instead of commons-lang 2.x imports when adding equals, hashCode and toString methods.
| <useDoubleNumbers> | boolean | true | Whether to use the java type double (or Double) instead of float (or Float) when representing the JSON Schema type 'number'.
| <useInnerClassBuilders> | boolean | false | If set to true, then the gang of four builder pattern will be used to generate builders on generated classes. Note: This property works in collaboration with the isGenerateBuilders() method. If the isGenerateBuilders() is false, then this property will not do anything.
| <useJakartaValidation> | boolean | false | Whether to use annotations from jakarta.validation package instead of javax.validation package when adding JSR-303 annotations to generated Java types. This property works in collaboration with the isIncludeJsr303Annotations() configuration option. If the isIncludeJsr303Annotations() returns false, then this configuration option will not affect anything.
| <useJodaDates> | boolean | false | Whether to use org.joda.time.DateTime instead of java.util.Date when adding date type fields to generated Java types.
| <useJodaLocalDates> | boolean | false | Whether to use org.joda.time.LocalDate instead of string when adding string type fields of format date (not date-time) to generated Java types.
| <useJodaLocalTimes> | boolean | false | Whether to use org.joda.time.LocalTime instead of string when adding string type fields of format time (not date-time) to generated Java types.
| <useLongIntegers> | boolean | false | Whether to use the java type long (or Long) instead of int (or Integer) when representing the JSON Schema type 'integer'.
| <useOptionalForGetters> | boolean | false | Whether to use java.util.Optional as return type for getters of non-required fields.
| <usePrimitives> | boolean | false | Whether to use primitives (long, double, boolean) instead of wrapper types where possible when generating bean properties (has the side-effect of making those properties non-null).
| <useTitleAsClassname> | boolean | false | Use the title as class name. Otherwise, the property and file name is used.
