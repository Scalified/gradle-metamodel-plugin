/*
 * MIT License
 *
 * Copyright (c) 2018 Scalified
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package com.scalified.plugins.gradle.metamodel

import groovy.transform.PackageScope
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.compile.JavaCompile
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * @author shell
 * @version 1.0.0
 * @since 1.0.0
 */
class MetamodelCompileTask extends JavaCompile {

	private static final Logger LOGGER = LoggerFactory.getLogger(MetamodelCompileTask)

	@PackageScope
	static final String NAME = 'metamodelCompile'

	@PackageScope
	static final String DESCRIPTION = 'Generates static metamodel classes'

	MetamodelCompileTask() {
		group = MetamodelPlugin.TASK_GROUP_NAME
		description = DESCRIPTION

		project.afterEvaluate {
			def extension = project.extensions.getByType(MetamodelExtension)
			setDestinationDir(project.file(extension.srcDir))
			configureSourceSet()

			options.compilerArgs += ['-proc:only', '-processor', extension.processor]
		}
	}

	protected def configureSourceSet() {
		def mainSourceSet = project.sourceSets[SourceSet.MAIN_SOURCE_SET_NAME] as SourceSet

		def configuration = project.configurations[MetamodelPlugin.CONFIGURATION_NAME]
		source = mainSourceSet.java
		classpath = mainSourceSet.compileClasspath + configuration
		LOGGER.debug("Configured $MetamodelPlugin.SOURCE_SET_NAME source set for $NAME task")
	}

}
