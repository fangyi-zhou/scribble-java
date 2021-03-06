/**
 * Copyright 2008 The Scribble Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.scribble.cli;

import java.io.File;
import java.io.FileFilter;

import org.apache.commons.io.filefilter.AbstractFileFilter;

public class IgnoredDirectoryWhenFilter extends AbstractFileFilter {
	private final FileFilter filter;

	public IgnoredDirectoryWhenFilter(FileFilter filter) {
		this.filter = filter;
	}

	@Override
	public boolean accept(File file) {
		if (!file.isDirectory()) {
			return false;
		}
		return file.listFiles(filter).length == 0;
	}
}
