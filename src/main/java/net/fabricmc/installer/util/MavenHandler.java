/*
 * Copyright (c) 2016, 2017, 2018 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.fabricmc.installer.util;

import org.apache.commons.io.IOUtils;
import org.apache.maven.artifact.repository.metadata.Metadata;
import org.apache.maven.artifact.repository.metadata.io.xpp3.MetadataXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.Collections;
import java.util.List;

public class MavenHandler {

	public static String latestVersion = "";
	public static List<String> versions;

	public static String getPath(String mavenServerURL, String packageName, String jarName, String version) {
		if (mavenServerURL.endsWith("/")) {
			mavenServerURL = mavenServerURL.substring(0, mavenServerURL.length() - 1);
		}

		return mavenServerURL + "/" + packageName.replace('.', '/') + "/" + jarName + "/" + version + "/" + jarName + "-" + version + ".jar";
	}

	public static void load(String mavenServerURL, String packageName, String jarName) throws IOException, ParserConfigurationException, SAXException, XmlPullParserException {
		String baseMavenMeta = IOUtils.toString(new URL(mavenServerURL + "/" + packageName.replace('.', '/') + "/" + jarName + "/maven-metadata.xml"), "UTF-8");
		Metadata metadata = new MetadataXpp3Reader().read(new StringReader(baseMavenMeta));
		latestVersion = metadata.getVersioning().getRelease();
		versions = metadata.getVersioning().getVersions();
		Collections.reverse(versions);
	}

}