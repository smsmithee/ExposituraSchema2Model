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
package com.expositura.schema2model;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.codemodel.JDefinedClass;

/**
 * A partial implementation of the AbstractAnnotator interface that adds type info awareness to an annotator
 * implementations.
 */
public abstract class AbstractTypeInfoAwareAnnotator extends AbstractAnnotator {

  /**
   * Constructor that accepts a GenerationConfig for this annotator.
   * @param generationConfig The configuration for this annotator.
   */
  public AbstractTypeInfoAwareAnnotator(final GenerationConfig generationConfig) {
    super(generationConfig);
  }

  @Override
  public void typeInfo(final JDefinedClass clazz, final JsonNode node) {
    if (getGenerationConfig().isIncludeTypeInfo()) {
      // Have per-schema JavaTypeInfo configuration override what is defined in generation config; backward comparability
      if (node.has("deserializationClassProperty")) {
        final String annotationName = node.get("deserializationClassProperty").asText();
        addJsonTypeInfoAnnotation(clazz, annotationName);
      } else {
        addJsonTypeInfoAnnotation(clazz, "@class");
      }
    } else {
      // per-schema JsonTypeInfo configuration
      if (node.has("deserializationClassProperty")) {
        final String annotationName = node.get("deserializationClassProperty").asText();
        addJsonTypeInfoAnnotation(clazz, annotationName);
      }
    }
  }

  @Override
  public boolean isPolymorphicDeserializationSupported(final JsonNode node) {
    return getGenerationConfig().isIncludeTypeInfo() || node.has("deserializationClassProperty");
  }

  /**
   * Add JSON type info to the indicated property on the indicated class.
   * @param clazz The class to annotate.
   * @param propertyName The property on the class.
   */
  abstract protected void addJsonTypeInfoAnnotation(final JDefinedClass clazz, final String propertyName);
}
