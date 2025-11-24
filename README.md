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

Name | Type | Default | Description |
|---|---|---|---|
| <addCompileSourceRoot> | boolean | true | Add the output directory to the project as a source root, so that the generated java types are compiled and included in the project artifact |
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
| <includeJsr303Annotations> | boolean | false | Whether to include JSR-303/349 annotations (for schema rules like minimum, maximum, etc) in generated Java types.
Schema rules and the annotation they produce:

maximum = @DecimalMax
minimum = @DecimalMin
minItems,maxItems = @Size
minLength,maxLength = @Size
pattern = @Pattern
required = @NotNull
Any Java fields which are an object or array of objects will be annotated with @Valid to support validation of an entire document tree.
| <includeJsr305Annotations> | boolean | false | Whether to include JSR-305 annotations (for schema rules like Nullable, NonNull, etc) in generated Java types.


<includeRequiredPropertiesConstructor>	boolean	1.0.3	The 'includeRequiredPropertiesConstructor' configuration option. This property works in collaboration with the isIncludeConstructors() configuration option and is incompatible with isConstructorsRequiredPropertiesOnly(), and will have no effect if isIncludeConstructors() is not set to true. If isIncludeConstructors() is set to true then this configuration determines whether the resulting object should include a constructor with only the required properties as parameters.
Default value is: false.
User property is: jsonschema2pojo.includeRequiredPropertiesConstructor.
<includeSetters>	boolean	-	Whether to include setters or to omit this accessor method and create public fields instead
Default value is: true.
User property is: jsonschema2pojo.includeSetters.
<includeToString>	boolean	0.3.1	Whether to include a toString method in generated Java types.
Default value is: true.
User property is: jsonschema2pojo.includeToString.
<includeTypeInfo>	boolean	1.0.2	Whether to include json type information; often required to support polymorphic type handling.
By default the type information is stored in the @class property, this can be overridden in the deserializationClassProperty of the schema.


Default value is: false.
User property is: jsonschema2pojo.includeTypeInfo.
<includes>	String[]	0.4.3	List of file patterns to include.
<inclusionLevel>	String	-	The Level of inclusion to set in the generated Java types for Jackson serializers.
Supported values

ALWAYS
NON_ABSENT
NON_DEFAULT
NON_EMPTY
NON_NULL
USE_DEFAULTS

Default value is: NON_NULL.
User property is: jsonschema2pojo.inclusionLevel.
<initializeCollections>	boolean	No version given	Whether to initialize Set and List fields as empty collections, or leave them as null.
Default value is: true.
User property is: jsonschema2pojo.initializeCollections.
<outputDirectory>	File	0.1.0	Target directory for generated Java source files.
Default value is: ${project.build.directory}/generated-sources/jsonschema2pojo.
User property is: jsonschema2pojo.outputDirectory.
<outputEncoding>	String	0.4.0	The character encoding that should be used when writing the generated Java source files.
Default value is: UTF-8.
User property is: jsonschema2pojo.outputEncoding.
<parcelable>	boolean	0.4.11	Whether to make the generated types 'parcelable' (for Android development).
Default value is: false.
User property is: jsonschema2pojo.parcelable.
<propertyWordDelimiters>	String	0.2.2	The characters that should be considered as word delimiters when creating Java Bean property names from JSON property names. If blank or not set, JSON properties will be considered to contain a single word when creating Java Bean property names.
Default value is: - _.
User property is: jsonschema2pojo.propertyWordDelimiters.
<refFragmentPathDelimiters>	String	0.4.31	A string containing any characters that should act as path delimiters when resolving $ref fragments. By default, #, / and . are used in an attempt to support JSON Pointer and JSON Path.
Default value is: #/..
User property is: jsonschema2pojo.refFragmentPathDelimiters.
<removeOldOutput>	boolean	0.3.7	Whether to empty the target directory before generation occurs, to clear out all source files that have been generated previously.
Be warned, when activated this option will cause jsonschema2pojo to indiscriminately delete the entire contents of the target directory (all files and folders) before it begins generating sources.


Default value is: false.
User property is: jsonschema2pojo.removeOldOutput.
<serializable>	boolean	0.4.23	Whether to make the generated types 'serializable'.
Default value is: false.
User property is: jsonschema2pojo.serializable.
<skip>	boolean	0.2.1	Skip plugin execution (don't read/validate any schema files, don't generate any java types).
Default value is: false.
User property is: jsonschema2pojo.skip.
<sourceDirectory>	String	0.1.0	Location of the JSON Schema file(s). Note: this may refer to a single file or a directory of files.
User property is: jsonschema2pojo.sourceDirectory.
<sourcePaths>	String[]	0.3.1	An array of locations of the JSON Schema file(s). Note: each item may refer to a single file or a directory of files.
User property is: jsonschema2pojo.sourcePaths.
<sourceSortOrder>	String	0.4.34	The sort order to be applied when recursively processing the source files. By default the OS can influence the processing order. Supported values:
OS (Let the OS influence the order the source files are processed.)
FILES_FIRST (Case sensitive sort, visit the files first. The source files are processed in a breadth first sort order.)
SUBDIRS_FIRST (Case sensitive sort, visit the sub-directories before the files. The source files are processed in a depth first sort order.)

Default value is: OS.
User property is: jsonschema2pojo.sourceSortOrder.
<sourceType>	String	0.3.3	The type of input documents that will be read
Supported values:

jsonschema (schema documents, containing formal rules that describe the structure of JSON data)
json (documents that represent an example of the kind of JSON data that the generated Java types will be mapped to)
yamlschema (JSON schema documents, represented as YAML)
yaml (documents that represent an example of the kind of YAML (or JSON) data that the generated Java types will be mapped to)

Default value is: jsonschema.
User property is: jsonschema2pojo.sourceType.
<targetPackage>	String	0.1.0	Package name used for generated Java classes (for types where a fully qualified name has not been supplied in the schema using the 'javaType' property).
User property is: jsonschema2pojo.targetPackage.
<targetVersion>	String	-	The target version for generated source files, used whenever decisions are made about generating source code that may be incompatible with older JVMs. Acceptable values include e.g. 1.6, 1.8, 8, 9, 10, 11.
If not set, the value of targetVersion is auto-detected. For auto-detection, the first value found in the following list will be used:
maven.compiler.source property
maven.compiler.release property
maven-compiler-plugin 'source' configuration option
maven-compiler-plugin 'release' configuration option
the current JVM version

User property is: jsonschema2pojo.targetVersion.
<timeType>	String	0.4.22	What type to use instead of string when adding string type fields of format time (not date-time) to generated Java types.
User property is: jsonschema2pojo.timeType.
<toStringExcludes>	String[]	0.4.35	The fields to be excluded from toString generation
User property is: jsonschema2pojo.toStringExcludes.
<useBigDecimals>	boolean	0.4.22	Whether to use the java type java.math.BigDecimal instead of float (or Float) when representing the JSON Schema type 'number'. Note that this configuration overrides isUseDoubleNumbers().
Default value is: false.
User property is: jsonschema2pojo.useBigDecimals.
<useBigIntegers>	boolean	0.4.25	Whether to use the java type java.math.BigInteger instead of int (or Integer) when representing the JSON Schema type 'integer'. Note that this configuration overrides isUseLongIntegers().
Default value is: false.
User property is: jsonschema2pojo.useBigIntegers.
<useCommonsLang3>	boolean	0.4.1	Whether to use commons-lang 3.x imports instead of commons-lang 2.x imports when adding equals, hashCode and toString methods.
Default value is: false.
User property is: jsonschema2pojo.useCommonsLang3.
<useDoubleNumbers>	boolean	0.4.0	Whether to use the java type double (or Double) instead of float (or Float) when representing the JSON Schema type 'number'.
Default value is: true.
User property is: jsonschema2pojo.useDoubleNumbers.
<useInnerClassBuilders>	boolean	1.0.0	If set to true, then the gang of four builder pattern will be used to generate builders on generated classes. Note: This property works in collaboration with the isGenerateBuilders() method. If the isGenerateBuilders() is false, then this property will not do anything.
Default value is: false.
User property is: jsonschema2pojo.useInnerClassBuilders.
<useJakartaValidation>	boolean	-	Whether to use annotations from jakarta.validation package instead of javax.validation package when adding JSR-303 annotations to generated Java types. This property works in collaboration with the isIncludeJsr303Annotations() configuration option. If the isIncludeJsr303Annotations() returns false, then this configuration option will not affect anything.
Default value is: false.
User property is: jsonschema2pojo.useJakartaValidation.
<useJodaDates>	boolean	0.4.0	Whether to use org.joda.time.DateTime instead of java.util.Date when adding date type fields to generated Java types.
Default value is: false.
User property is: jsonschema2pojo.useJodaDates.
<useJodaLocalDates>	boolean	0.4.9	Whether to use org.joda.time.LocalDate instead of string when adding string type fields of format date (not date-time) to generated Java types.
Default value is: false.
User property is: jsonschema2pojo.useJodaLocalDates.
<useJodaLocalTimes>	boolean	0.4.9	Whether to use org.joda.time.LocalTime instead of string when adding string type fields of format time (not date-time) to generated Java types.
Default value is: false.
User property is: jsonschema2pojo.useJodaLocalTimes.
<useLongIntegers>	boolean	0.2.2	Whether to use the java type long (or Long) instead of int (or Integer) when representing the JSON Schema type 'integer'.
Default value is: false.
User property is: jsonschema2pojo.useLongIntegers.
<useOptionalForGetters>	boolean	-	Whether to use java.util.Optional as return type for getters of non-required fields.
Default value is: false.
User property is: jsonschema2pojo.useOptionalForGetters.
<usePrimitives>	boolean	0.2.0	Whether to use primitives (long, double, boolean) instead of wrapper types where possible when generating bean properties (has the side-effect of making those properties non-null).
Default value is: false.
User property is: jsonschema2pojo.usePrimitives.
<useTitleAsClassname>	boolean	1.0.0	Use the title as class name. Otherwise, the property and file name is used.
Default value is: false.
User property is: jsonschema2pojo.useTitleAsClassname.

<customDateTimePattern>

A custom pattern to use when formatting date-time fields during serialization. Requires support from your JSON binding library.
Type: java.lang.String
Since: 0.4.33
Required: No
User Property: jsonschema2pojo.customDatePattern
<customRuleFactory>

A fully qualified class name, referring to an class that extends org.jsonschema2pojo.rules.RuleFactory and will be used to create instances of Rules used for code generation.
Type: java.lang.String
Since: 0.4.5
Required: No
User Property: jsonschema2pojo.customRuleFactory
Default: org.jsonschema2pojo.rules.RuleFactory
<customTimePattern>

A custom pattern to use when formatting time fields during serialization. Requires support from your JSON binding library.
Type: java.lang.String
Since: 0.4.36
Required: No
User Property: jsonschema2pojo.customTimePattern
<dateTimeType>

What type to use instead of string when adding string type fields of format date-time to generated Java types.
Type: java.lang.String
Since: 0.4.22
Required: No
User Property: jsonschema2pojo.dateTimeType
<dateType>

What type to use instead of string when adding string type fields of format date (not date-time) to generated Java types.
Type: java.lang.String
Since: 0.4.22
Required: No
User Property: jsonschema2pojo.dateType
<excludes>

List of file patterns to exclude. This only applies to the initial scan of the file system and will not prevent inclusion through a "$ref" in one of the schemas.
Type: java.lang.String[]
Since: 0.4.3
Required: No
<fileExtensions>

The strings (no preceeding dot) that should be considered as file name extensions, and therefore ignored, when creating Java class names.
Type: java.lang.String[]
Since: 0.4.23
Required: No
User Property: jsonschema2pojo.fileExtensions
<formatDateTimes>

Whether the fields of type `date` are formatted during serialization with a default pattern of yyyy-MM-dd'T'HH:mm:ss.SSSZ.
Type: boolean
Since: 0.4.29
Required: No
User Property: jsonschema2pojo.formatDateTimes
Default: false
<formatDates>

Whether the fields of type `date` are formatted during serialization with a default pattern of yyyy-MM-dd.
Type: boolean
Since: 0.4.33
Required: No
User Property: jsonschema2pojo.formatDates
Default: false
<formatTimes>

Whether the fields of type `time` are formatted during serialization with a default pattern of HH:mm:ss.SSS.
Type: boolean
Since: 0.4.36
Required: No
User Property: jsonschema2pojo.formatTimes
Default: false
<formatTypeMapping>

(no description)
Type: java.util.Map<java.lang.String, java.lang.String>
Since: 1.0.0
Required: No
User Property: jsonschema2pojo.formatTypeMapping
<generateBuilders>

Whether to generate builder-style methods of the form withXxx(value) (that return this), alongside the standard, void-return setters.
Type: boolean
Since: 0.1.2
Required: No
User Property: jsonschema2pojo.generateBuilders
Default: false
<includeAdditionalProperties>

Whether to allow 'additional properties' support in objects. Setting this to false will disable additional properties support, regardless of the input schema(s).
Type: boolean
Since: 0.4.14
Required: No
User Property: jsonschema2pojo.includeAdditionalProperties
Default: true
<includeAllPropertiesConstructor>

The 'includeAllPropertiesConstructor' configuration option. This property works in collaboration with the isIncludeConstructors() configuration option and is incompatible with isConstructorsRequiredPropertiesOnly(), and will have no effect if isIncludeConstructors() is not set to true. If isIncludeConstructors() is set to true then this configuration determines whether the resulting object should include a constructor with all listed properties as parameters.
Type: boolean
Since: 1.0.3
Required: No
User Property: jsonschema2pojo.includeAllPropertiesConstructor
Default: true
<includeConstructorPropertiesAnnotation>

(no description)
Type: boolean
Since: 1.0.2
Required: No
User Property: jsonschema2pojo.includeConstructorPropertiesAnnotation
Default: false
<includeConstructors>

Whether to generate constructors or not
Type: boolean
Since: 0.4.8
Required: No
User Property: jsonschema2pojo.includeConstructors
Default: false
<includeCopyConstructor>

The 'includeCopyConstructor' configuration option. This property works in collaboration with the isIncludeConstructors() configuration option and is incompatible with isConstructorsRequiredPropertiesOnly(), and will have no effect if isIncludeConstructors() is not set to true. If isIncludeConstructors() is set to true then this configuration determines whether the resulting object should include a constructor the class itself as a parameter, with the expectation that all properties from the originating class will assigned to the new class.
Type: boolean
Since: 1.0.3
Required: No
User Property: jsonschema2pojo.includeCopyConstructor
Default: false
<includeDynamicAccessors>

Whether to include dynamic getters, setters, and builders or to omit these methods.
Type: boolean
Since: 0.4.17
Required: No
User Property: jsonschema2pojo.includeDynamicAccessors
<includeDynamicBuilders>

Whether to include dynamic builders or to omit these methods.
Type: boolean
Required: No
User Property: jsonschema2pojo.includeDynamicBuilders
Default: false
<includeDynamicGetters>

Whether to include dynamic getters or to omit these methods.
Type: boolean
Required: No
User Property: jsonschema2pojo.includeDynamicGetters
Default: false
<includeDynamicSetters>

Whether to include dynamic setters or to omit these methods.
Type: boolean
Required: No
User Property: jsonschema2pojo.includeDynamicSetters
Default: false
<includeGeneratedAnnotation>

Whether to include a javax.annotation.Generated (Java 8 and lower) or javax.annotation.processing.Generated (Java 9+) in on generated types. See also: targetVersion.
Type: boolean
Required: No
User Property: jsonschema2pojo.includeGeneratedAnnotation
Default: true
<includeGetters>

Whether to include getters or to omit this accessor method and create public fields instead
Type: boolean
Required: No
User Property: jsonschema2pojo.includeGetters
Default: true
<includeHashcodeAndEquals>

Whether to include hashCode and equals methods in generated Java types.
Type: boolean
Since: 0.3.1
Required: No
User Property: jsonschema2pojo.includeHashcodeAndEquals
Default: true
<includeJsr303Annotations>

Whether to include JSR-303/349 annotations (for schema rules like minimum, maximum, etc) in generated Java types.
Schema rules and the annotation they produce:

maximum = @DecimalMax
minimum = @DecimalMin
minItems,maxItems = @Size
minLength,maxLength = @Size
pattern = @Pattern
required = @NotNull
Any Java fields which are an object or array of objects will be annotated with @Valid to support validation of an entire document tree.
Type: boolean
Since: 0.3.2
Required: No
User Property: jsonschema2pojo.includeJsr303Annotations
Default: false
<includeJsr305Annotations>

Whether to include JSR-305 annotations (for schema rules like Nullable, NonNull, etc) in generated Java types.
Type: boolean
Since: 0.4.8
Required: No
User Property: jsonschema2pojo.includeJsr305Annotations
Default: false
<includeRequiredPropertiesConstructor>

The 'includeRequiredPropertiesConstructor' configuration option. This property works in collaboration with the isIncludeConstructors() configuration option and is incompatible with isConstructorsRequiredPropertiesOnly(), and will have no effect if isIncludeConstructors() is not set to true. If isIncludeConstructors() is set to true then this configuration determines whether the resulting object should include a constructor with only the required properties as parameters.
Type: boolean
Since: 1.0.3
Required: No
User Property: jsonschema2pojo.includeRequiredPropertiesConstructor
Default: false
<includeSetters>

Whether to include setters or to omit this accessor method and create public fields instead
Type: boolean
Required: No
User Property: jsonschema2pojo.includeSetters
Default: true
<includeToString>

Whether to include a toString method in generated Java types.
Type: boolean
Since: 0.3.1
Required: No
User Property: jsonschema2pojo.includeToString
Default: true
<includeTypeInfo>

Whether to include json type information; often required to support polymorphic type handling.
By default the type information is stored in the @class property, this can be overridden in the deserializationClassProperty of the schema.

Type: boolean
Since: 1.0.2
Required: No
User Property: jsonschema2pojo.includeTypeInfo
Default: false
<includes>

List of file patterns to include.
Type: java.lang.String[]
Since: 0.4.3
Required: No
<inclusionLevel>

The Level of inclusion to set in the generated Java types for Jackson serializers.
Supported values

ALWAYS
NON_ABSENT
NON_DEFAULT
NON_EMPTY
NON_NULL
USE_DEFAULTS
Type: java.lang.String
Required: No
User Property: jsonschema2pojo.inclusionLevel
Default: NON_NULL
<initializeCollections>

Whether to initialize Set and List fields as empty collections, or leave them as null.
Type: boolean
Since: No version given
Required: No
User Property: jsonschema2pojo.initializeCollections
Default: true
<outputDirectory>

Target directory for generated Java source files.
Type: java.io.File
Since: 0.1.0
Required: No
User Property: jsonschema2pojo.outputDirectory
Default: ${project.build.directory}/generated-sources/jsonschema2pojo
<outputEncoding>

The character encoding that should be used when writing the generated Java source files.
Type: java.lang.String
Since: 0.4.0
Required: No
User Property: jsonschema2pojo.outputEncoding
Default: UTF-8
<parcelable>

Whether to make the generated types 'parcelable' (for Android development).
Type: boolean
Since: 0.4.11
Required: No
User Property: jsonschema2pojo.parcelable
Default: false
<propertyWordDelimiters>

The characters that should be considered as word delimiters when creating Java Bean property names from JSON property names. If blank or not set, JSON properties will be considered to contain a single word when creating Java Bean property names.
Type: java.lang.String
Since: 0.2.2
Required: No
User Property: jsonschema2pojo.propertyWordDelimiters
Default: - _
<refFragmentPathDelimiters>

A string containing any characters that should act as path delimiters when resolving $ref fragments. By default, #, / and . are used in an attempt to support JSON Pointer and JSON Path.
Type: java.lang.String
Since: 0.4.31
Required: No
User Property: jsonschema2pojo.refFragmentPathDelimiters
Default: #/.
<removeOldOutput>

Whether to empty the target directory before generation occurs, to clear out all source files that have been generated previously.
Be warned, when activated this option will cause jsonschema2pojo to indiscriminately delete the entire contents of the target directory (all files and folders) before it begins generating sources.

Type: boolean
Since: 0.3.7
Required: No
User Property: jsonschema2pojo.removeOldOutput
Default: false
<serializable>

Whether to make the generated types 'serializable'.
Type: boolean
Since: 0.4.23
Required: No
User Property: jsonschema2pojo.serializable
Default: false
<skip>

Skip plugin execution (don't read/validate any schema files, don't generate any java types).
Type: boolean
Since: 0.2.1
Required: No
User Property: jsonschema2pojo.skip
Default: false
<sourceDirectory>

Location of the JSON Schema file(s). Note: this may refer to a single file or a directory of files.
Type: java.lang.String
Since: 0.1.0
Required: No
User Property: jsonschema2pojo.sourceDirectory
<sourcePaths>

An array of locations of the JSON Schema file(s). Note: each item may refer to a single file or a directory of files.
Type: java.lang.String[]
Since: 0.3.1
Required: No
User Property: jsonschema2pojo.sourcePaths
<sourceSortOrder>

The sort order to be applied when recursively processing the source files. By default the OS can influence the processing order. Supported values:
OS (Let the OS influence the order the source files are processed.)
FILES_FIRST (Case sensitive sort, visit the files first. The source files are processed in a breadth first sort order.)
SUBDIRS_FIRST (Case sensitive sort, visit the sub-directories before the files. The source files are processed in a depth first sort order.)
Type: java.lang.String
Since: 0.4.34
Required: No
User Property: jsonschema2pojo.sourceSortOrder
Default: OS
<sourceType>

The type of input documents that will be read
Supported values:

jsonschema (schema documents, containing formal rules that describe the structure of JSON data)
json (documents that represent an example of the kind of JSON data that the generated Java types will be mapped to)
yamlschema (JSON schema documents, represented as YAML)
yaml (documents that represent an example of the kind of YAML (or JSON) data that the generated Java types will be mapped to)
Type: java.lang.String
Since: 0.3.3
Required: No
User Property: jsonschema2pojo.sourceType
Default: jsonschema
<targetPackage>

Package name used for generated Java classes (for types where a fully qualified name has not been supplied in the schema using the 'javaType' property).
Type: java.lang.String
Since: 0.1.0
Required: No
User Property: jsonschema2pojo.targetPackage
<targetVersion>

The target version for generated source files, used whenever decisions are made about generating source code that may be incompatible with older JVMs. Acceptable values include e.g. 1.6, 1.8, 8, 9, 10, 11.
If not set, the value of targetVersion is auto-detected. For auto-detection, the first value found in the following list will be used:
maven.compiler.source property
maven.compiler.release property
maven-compiler-plugin 'source' configuration option
maven-compiler-plugin 'release' configuration option
the current JVM version
Type: java.lang.String
Required: No
User Property: jsonschema2pojo.targetVersion
<timeType>

What type to use instead of string when adding string type fields of format time (not date-time) to generated Java types.
Type: java.lang.String
Since: 0.4.22
Required: No
User Property: jsonschema2pojo.timeType
<toStringExcludes>

The fields to be excluded from toString generation
Type: java.lang.String[]
Since: 0.4.35
Required: No
User Property: jsonschema2pojo.toStringExcludes
<useBigDecimals>

Whether to use the java type java.math.BigDecimal instead of float (or Float) when representing the JSON Schema type 'number'. Note that this configuration overrides isUseDoubleNumbers().
Type: boolean
Since: 0.4.22
Required: No
User Property: jsonschema2pojo.useBigDecimals
Default: false
<useBigIntegers>

Whether to use the java type java.math.BigInteger instead of int (or Integer) when representing the JSON Schema type 'integer'. Note that this configuration overrides isUseLongIntegers().
Type: boolean
Since: 0.4.25
Required: No
User Property: jsonschema2pojo.useBigIntegers
Default: false
<useCommonsLang3>

Whether to use commons-lang 3.x imports instead of commons-lang 2.x imports when adding equals, hashCode and toString methods.
Type: boolean
Since: 0.4.1
Required: No
User Property: jsonschema2pojo.useCommonsLang3
Default: false
<useDoubleNumbers>

Whether to use the java type double (or Double) instead of float (or Float) when representing the JSON Schema type 'number'.
Type: boolean
Since: 0.4.0
Required: No
User Property: jsonschema2pojo.useDoubleNumbers
Default: true
<useInnerClassBuilders>

If set to true, then the gang of four builder pattern will be used to generate builders on generated classes. Note: This property works in collaboration with the isGenerateBuilders() method. If the isGenerateBuilders() is false, then this property will not do anything.
Type: boolean
Since: 1.0.0
Required: No
User Property: jsonschema2pojo.useInnerClassBuilders
Default: false
<useJakartaValidation>

Whether to use annotations from jakarta.validation package instead of javax.validation package when adding JSR-303 annotations to generated Java types. This property works in collaboration with the isIncludeJsr303Annotations() configuration option. If the isIncludeJsr303Annotations() returns false, then this configuration option will not affect anything.
Type: boolean
Required: No
User Property: jsonschema2pojo.useJakartaValidation
Default: false
<useJodaDates>

Whether to use org.joda.time.DateTime instead of java.util.Date when adding date type fields to generated Java types.
Type: boolean
Since: 0.4.0
Required: No
User Property: jsonschema2pojo.useJodaDates
Default: false
<useJodaLocalDates>

Whether to use org.joda.time.LocalDate instead of string when adding string type fields of format date (not date-time) to generated Java types.
Type: boolean
Since: 0.4.9
Required: No
User Property: jsonschema2pojo.useJodaLocalDates
Default: false
<useJodaLocalTimes>

Whether to use org.joda.time.LocalTime instead of string when adding string type fields of format time (not date-time) to generated Java types.
Type: boolean
Since: 0.4.9
Required: No
User Property: jsonschema2pojo.useJodaLocalTimes
Default: false
<useLongIntegers>

Whether to use the java type long (or Long) instead of int (or Integer) when representing the JSON Schema type 'integer'.
Type: boolean
Since: 0.2.2
Required: No
User Property: jsonschema2pojo.useLongIntegers
Default: false
<useOptionalForGetters>

Whether to use java.util.Optional as return type for getters of non-required fields.
Type: boolean
Required: No
User Property: jsonschema2pojo.useOptionalForGetters
Default: false
<usePrimitives>

Whether to use primitives (long, double, boolean) instead of wrapper types where possible when generating bean properties (has the side-effect of making those properties non-null).
Type: boolean
Since: 0.2.0
Required: No
User Property: jsonschema2pojo.usePrimitives
Default: false
<useTitleAsClassname>

Use the title as class name. Otherwise, the property and file name is used.
Type: boolean
Since: 1.0.0
Required: No
User Property: jsonschema2pojo.useTitleAsClassname
Default: false
