/**
 * Copyright (c) 2010-2019 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.denon.internal;

import org.openhab.core.types.State;

/**
 * Callback interface to signal that a property was updated.
 *
 * @author Jeroen Idserda
 * @since 1.7.0
 */
public interface DenonPropertyUpdatedCallback {

    /**
     * Property was updated.
     * 
     * @param instance Name of the Denon receiver instance
     * @param property The property that was updated
     * @param state Its current state
     */
    public void updated(String instance, String property, State state);

}
