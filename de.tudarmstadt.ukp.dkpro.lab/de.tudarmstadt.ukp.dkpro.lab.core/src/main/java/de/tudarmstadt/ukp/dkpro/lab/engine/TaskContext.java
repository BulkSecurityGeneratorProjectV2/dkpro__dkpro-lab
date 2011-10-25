/*******************************************************************************
 * Copyright 2011
 * Ubiquitous Knowledge Processing (UKP) Lab
 * Technische Universität Darmstadt
 *   
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *   
 *   http://www.apache.org/licenses/LICENSE-2.0
 *   
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package de.tudarmstadt.ukp.dkpro.lab.engine;

import java.io.File;
import java.io.InputStream;
import java.net.URI;

import de.tudarmstadt.ukp.dkpro.lab.logging.LoggingService;
import de.tudarmstadt.ukp.dkpro.lab.storage.StorageService;
import de.tudarmstadt.ukp.dkpro.lab.storage.StreamReader;
import de.tudarmstadt.ukp.dkpro.lab.storage.StreamWriter;
import de.tudarmstadt.ukp.dkpro.lab.storage.StorageService.AccessMode;
import de.tudarmstadt.ukp.dkpro.lab.task.TaskContextMetadata;

/**
 * Task context. All important information about a task and convenient access to services. Direct
 * access to most important methods of the many services. In particular the storage methods should
 * be used from the context instead of directly accessing the storage service, as the context
 * provides the resolution if imports.
 *
 * @author Richard Eckart de Castilho
 */
public interface TaskContext
{
	LoggingService getLoggingService();

	/**
	 * Get the storage service.
	 */
	StorageService getStorageService();

	/**
	 * Get the life-cycle manager.
	 */
	LifeCycleManager getLifeCycleManager();

	// Context API
	String getId();

	TaskContextMetadata getMetadata();

	void destroy();

	// Convenience logging API
	void message(String msg);

	// Convenience storage API
	/**
	 * Store a resource. A resource is always stored within the context, even if originally imported.
	 */
	void storeBinary(String aKey, StreamWriter aStreamWriter);

	/**
	 * Store a resource. A resource is always stored within the context, even if originally imported.
	 */
	void storeBinary(String aKey, InputStream aStream);

	File getStorageLocation(String aKey, AccessMode aMode);

	boolean containsKey(String aKey);

	/**
	 * Retrieve a resource taking into account imports. Resources stored in the
	 * context are preferred to imported resources.
	 */
	<T extends StreamReader> T retrieveBinary(String aSearchResultKey, T aReader);

	/**
	 * Resolves the given import URI to the meta data of the task containing the addressed
	 * resource.
	 */
	TaskContextMetadata resolve(URI uri);
}
