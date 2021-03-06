/**
 * Copyright (c) 2010-2019 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.lightwaverf.internal.command;

import org.openhab.binding.lightwaverf.internal.LightwaveRfType;
import org.openhab.binding.lightwaverf.internal.message.LightwaveRfMessageId;
import org.openhab.core.types.State;

/**
 * @author Neil Renaud
 * @since 1.7.0
 */
public interface LightwaveRFCommand {

    LightwaveRFCommand STOP_MESSAGE = new LightwaveRFStopPublisherCommand();

    public String getLightwaveRfCommandString();

    public State getState(LightwaveRfType type);

    public LightwaveRfMessageId getMessageId();

    public LightwaveRfMessageType getMessageType();

}
