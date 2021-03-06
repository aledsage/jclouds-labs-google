/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jclouds.googlecloudstorage;

import static org.jclouds.Constants.PROPERTY_SESSION_INTERVAL;
import static org.jclouds.googlecloudstorage.reference.GoogleCloudStorageConstants.GCS_PROVIDER_NAME;
import static org.jclouds.googlecloudstorage.reference.GoogleCloudStorageConstants.OPERATION_COMPLETE_INTERVAL;
import static org.jclouds.googlecloudstorage.reference.GoogleCloudStorageConstants.OPERATION_COMPLETE_TIMEOUT;
import static org.jclouds.oauth.v2.config.OAuthProperties.AUDIENCE;
import static org.jclouds.oauth.v2.config.OAuthProperties.SIGNATURE_OR_MAC_ALGORITHM;
import static org.jclouds.reflect.Reflection2.typeToken;

import java.net.URI;
import java.util.Properties;
import org.jclouds.blobstore.BlobStoreContext;
import org.jclouds.googlecloudstorage.config.GoogleCloudStorageHttpApiModule;
import org.jclouds.googlecloudstorage.config.GoogleCloudStorageParserModule;
import org.jclouds.googlecloudstorage.config.OAuthModuleWithoutTypeAdapters;
import org.jclouds.oauth.v2.config.OAuthAuthenticationModule;
import org.jclouds.rest.internal.BaseHttpApiMetadata;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Module;

public class GoogleCloudStorageApiMetadata extends BaseHttpApiMetadata<GoogleCloudStorageApi> {

   @Override
   public Builder toBuilder() {
      return new Builder().fromApiMetadata(this);
   }

   public GoogleCloudStorageApiMetadata() {
      this(new Builder());
   }

   protected GoogleCloudStorageApiMetadata(Builder builder) {
      super(builder);
   }

   public static Properties defaultProperties() {
      Properties properties = BaseHttpApiMetadata.defaultProperties();
      properties.put("oauth.endpoint", "https://accounts.google.com/o/oauth2/token");
      properties.put(AUDIENCE, "https://accounts.google.com/o/oauth2/token");
      properties.put(SIGNATURE_OR_MAC_ALGORITHM, "RS256");
      properties.put(PROPERTY_SESSION_INTERVAL, 3600);
      properties.put(OPERATION_COMPLETE_INTERVAL, 2000);
      properties.put(OPERATION_COMPLETE_TIMEOUT, 600000);
      return properties;
   }

   public static class Builder extends BaseHttpApiMetadata.Builder<GoogleCloudStorageApi, Builder> {
      protected Builder() {
         id(GCS_PROVIDER_NAME)
               .name("Google Cloud Storage Api ")
               .identityName("Email associated with the Google API client_id")
               .credentialName("Private key literal associated with the Google API client_id")
               .documentation(URI.create("https://developers.google.com/storage/docs/json_api"))
               .version("v1")
               .defaultEndpoint("https://www.googleapis.com/storage/v1")
               .defaultProperties(GoogleCloudStorageApiMetadata.defaultProperties())
               .view(typeToken(BlobStoreContext.class))
               .defaultModules(
                     ImmutableSet.<Class<? extends Module>> builder().add(GoogleCloudStorageParserModule.class)
                           .add(OAuthAuthenticationModule.class).add(OAuthModuleWithoutTypeAdapters.class)
                           .add(GoogleCloudStorageHttpApiModule.class).build());
      }

      @Override
      public GoogleCloudStorageApiMetadata build() {
         return new GoogleCloudStorageApiMetadata(this);
      }

      @Override
      protected Builder self() {
         return this;
      }
   }
}
