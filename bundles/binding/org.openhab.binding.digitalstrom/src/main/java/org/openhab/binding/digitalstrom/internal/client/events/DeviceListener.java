/**
 * Copyright (c) 2010-2019 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.digitalstrom.internal.client.events;

import java.util.EventListener;

/**
 * @author Alexander Betker
 * @since 1.3.0
 */
public interface DeviceListener extends EventListener {

    public void deviceUpdated(String dsid);

}
