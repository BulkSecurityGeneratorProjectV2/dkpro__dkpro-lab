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
package de.tudarmstadt.ukp.dkpro.lab.groovy.uima;

import groovy.lang.Closure;

import java.lang.reflect.Constructor;
import java.util.Map;

import org.apache.uima.fit.component.initialize.ConfigurationParameterInitializer;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.ExternalResourceLocator;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceSpecifier;
import org.apache.uima.resource.Resource_ImplBase;

public class ClosureResourceProvider
extends Resource_ImplBase
implements ExternalResourceLocator
{
	public static final String PARAM_CLOSURE_CLASS = "closureClass";
	@ConfigurationParameter(name = PARAM_CLOSURE_CLASS, mandatory = true)
	private String closureClass;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public boolean initialize(ResourceSpecifier aSpecifier, Map aAdditionalParams)
		throws ResourceInitializationException
	{
		if (!super.initialize(aSpecifier, aAdditionalParams)) {
			return false;
		}

		ConfigurationParameterInitializer.initialize(this, aSpecifier);

		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getResource()
	{
		Constructor<Closure> constructor = null;
		boolean saved = false;
		try {
			Class<Closure> clazz = (Class<Closure>) Class.forName(closureClass);
			constructor = clazz.getConstructor(Object.class, Object.class);
			saved = constructor.isAccessible();
			constructor.setAccessible(true);
			Closure closure = constructor.newInstance((Object) null, (Object) null);
			return closure;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		finally {
			if (constructor != null) {
				constructor.setAccessible(saved);
			}
		}
	}
}
