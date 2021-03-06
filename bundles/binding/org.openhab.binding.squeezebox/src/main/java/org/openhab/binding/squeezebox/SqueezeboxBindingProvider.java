/**
 * Copyright (c) 2010-2019 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.squeezebox;

import org.openhab.core.binding.BindingProvider;

/**
 * This interface is implemented by classes that can provide mapping information
 * between openHAB items and Squeezebox items.
 *
 * Implementing classes should register themselves as a service in order to be
 * taken into account.
 *
 * @author Markus Wolters
 * @author Ben Jones
 * @since 1.3.0
 */
public interface SqueezeboxBindingProvider extends BindingProvider {
    SqueezeboxBindingConfig getSqueezeboxBindingConfig(String itemName);
}
