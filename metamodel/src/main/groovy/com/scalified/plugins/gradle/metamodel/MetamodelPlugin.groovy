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
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.tasks.SourceSet
import org.gradle.plugins.ide.idea.IdeaPlugin
import org.gradle.plugins.ide.idea.model.IdeaModule
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * @author shell
 * @version 1.0.0
 * @since 1.0.0
 */
class MetamodelPlugin implements Plugin<Project> {

	private static final Logger LOGGER = LoggerFactory.getLogger(MetamodelPlugin)

	@PackageScope
	static final String TASK_GROUP_NAME = 'metamodel'

	@PackageScope
	static final String CONFIGURATION_NAME = 'metamodel'

	@PackageScope
	static final String SOURCE_SET_NAME = 'metamodel'

	private Project project

	@Override
	void apply(Project project) {
		this.project = project

		if (!project.plugins.hasPlugin(JavaPlugin)) {
			project.plugins.apply(JavaPlugin)
			LOGGER.debug("Applied Java Plugin")
		}

		project.extensions.create(MetamodelExtension.NAME, MetamodelExtension)
		LOGGER.debug("Created $MetamodelExtension.NAME extension")

		project.configurations.create(CONFIGURATION_NAME)
		LOGGER.debug("Created $CONFIGURATION_NAME configuration")

		project.sourceSets.create(SOURCE_SET_NAME)
		LOGGER.debug("Created $SOURCE_SET_NAME source set")

		project.tasks.clean.dependsOn(project.tasks.create(MetamodelCleanTask.NAME, MetamodelCleanTask))
		project.tasks.compileJava.dependsOn(project.tasks.create(MetamodelCompileTask.NAME, MetamodelCompileTask))
		LOGGER.debug("Created and configured ${[MetamodelCleanTask.NAME, MetamodelCompileTask.NAME]} tasks")

		project.afterEvaluate {
			def extension = project.extensions.getByType(MetamodelExtension)
			createDirectories(extension)
			configureSourceSet(extension)
			configureDependencies(extension)
		}

	}

	private def createDirectories(MetamodelExtension extension) {
		def directory = project.file(extension.srcDir)
		if (!directory.exists()) {
			directory.mkdirs()
			LOGGER.debug("Created $directory.absolutePath directory")
		}

		if (!project.plugins.hasPlugin(IdeaPlugin)) {
			project.plugins.apply(IdeaPlugin)
			LOGGER.debug("Applied Idea Plugin")
		}
		def ideaModule = project.idea.module as IdeaModule
		ideaModule.generatedSourceDirs += directory
		LOGGER.debug("Marked $directory.absolutePath directory as Idea generated sources directory")
	}

	private def configureSourceSet(MetamodelExtension extension) {
		def sourceSet = project.sourceSets[SOURCE_SET_NAME] as SourceSet
		sourceSet.java.srcDirs = project.files(extension.srcDir)
		LOGGER.debug("Configured $SOURCE_SET_NAME source set")

		def mainSourceSet = project.sourceSets[SourceSet.MAIN_SOURCE_SET_NAME] as SourceSet
		mainSourceSet.java.srcDirs += sourceSet.java.srcDirs
		LOGGER.trace("Configured $SourceSet.MAIN_SOURCE_SET_NAME source set")
	}

	private def configureDependencies(MetamodelExtension extension) {
		project.dependencies.add(CONFIGURATION_NAME, extension.dependency)
		LOGGER.debug("Added $extension.dependency dependency")
	}

}
