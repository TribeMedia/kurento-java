/*
 * (C) Copyright 2013 Kurento (http://kurento.org/)
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-2.1.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 */
package com.kurento.kmf.media;

import com.kurento.kmf.media.events.HasWindowInListener;
import com.kurento.kmf.media.events.HasWindowOutListener;

/**
 * 
 * @author Ivan Gracia (igracia@gsyc.es)
 * 
 * @version 1.0.0
 * 
 */
public interface PointerDetectorFilter extends Filter, HasWindowOutListener,
		HasWindowInListener {

	void addWindow(int upperRightX, int upperRightY, int width, int height,
			String id);

	void removeWindow(String windowId);

	void clearWindows();

	void addWindow(int upperRightX, int upperRightY, int width, int height,
			String id, Continuation<Void> cont);

	void removeWindow(String windowId, Continuation<Void> cont);

	void clearWindows(Continuation<Void> cont);

	public interface PointerDetectorFilterBuilder
			extends
			MediaObjectBuilder<PointerDetectorFilterBuilder, PointerDetectorFilter> {

	}

}