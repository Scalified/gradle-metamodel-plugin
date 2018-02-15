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
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * @author shell
 * @version 1.0.0
 * @since 1.0.0
 */
class MetamodelCleanTask extends DefaultTask {

	private static final Logger LOGGER = LoggerFactory.getLogger(MetamodelCleanTask)

	@PackageScope
	static final String NAME = 'metamodelClean'

	@PackageScope
	static final String DESCRIPTION = 'Cleans metamodel source directory'

	MetamodelCleanTask() {
		group = MetamodelPlugin.TASK_GROUP_NAME
		description = DESCRIPTION
	}

	@TaskAction
	def clean() {
		def extension = project.extensions.getByType(MetamodelExtension)
		def directory = project.file(extension.srcDir)
		directory.listFiles().each {
			it.directory ? it.deleteDir() : it.delete()
			LOGGER.debug("Removed $it.absolutePath ${it.directory ? 'directory' : 'file'}")
		}
	}

}