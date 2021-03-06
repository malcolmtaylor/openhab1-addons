/**
 * Copyright (c) 2010-2019 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.maxcul.internal.messages;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Thermostate State message
 *
 * @author Paul Hampson (cyclingengineer)
 * @since 1.6.0
 */
public class ThermostatStateMsg extends BaseMsg {

    final static private int THERMOSTAT_STATE_TIME_PAYLOAD_LEN = 6; /* in bytes */
    final static private int THERMOSTAT_STATE_MEAS_PAYLOAD_LEN = 5; /* in bytes */
    final static private int THERMOSTAT_STATE_SHORT_PAYLOAD_LEN = 3; /* in bytes */

    private static final Logger logger = LoggerFactory.getLogger(ThermostatStateMsg.class);

    private double desiredTemperature;
    private int valvePos;
    private boolean dstActive;
    private boolean lanGateway; // unknown what this is for
    private boolean lockedForManualSetPoint;
    private boolean rfError;
    private boolean batteryLow;
    private Double measuredTemperature = null;
    private GregorianCalendar untilDateTime = null;
    private ThermostatControlMode ctrlMode;

    public ThermostatStateMsg(String rawMsg) {
        super(rawMsg);
        logger.debug(this.msgType + " Payload Len -> " + this.payload.length);

        if (this.payload.length == THERMOSTAT_STATE_SHORT_PAYLOAD_LEN
                || this.payload.length == THERMOSTAT_STATE_MEAS_PAYLOAD_LEN
                || this.payload.length == THERMOSTAT_STATE_TIME_PAYLOAD_LEN) {
            /* extract control mode */
            ctrlMode = ThermostatControlMode.values()[(this.payload[0] & 0x3)];
            /* extract DST status */
            dstActive = extractBitFromByte(this.payload[0], 3);
            ;
            /* extract lanGateway */
            lanGateway = extractBitFromByte(this.payload[0], 4);
            /* extract locked status */
            lockedForManualSetPoint = extractBitFromByte(this.payload[0], 5);
            /* extract rferror status */
            rfError = extractBitFromByte(this.payload[0], 6);
            /* extract battery status */
            batteryLow = extractBitFromByte(this.payload[0], 7);
            /* extract valve position */
            valvePos = this.payload[1];
            /* extract desired temperature information */
            desiredTemperature = (this.payload[2] & 0x7f) / 2.0;

            /* handle longer packet */
            if (this.payload.length == THERMOSTAT_STATE_MEAS_PAYLOAD_LEN) {
                /* extract measured temperature */
                if (ctrlMode != ThermostatControlMode.TEMPORARY) {
                    int mTemp = (this.payload[3] & 0x1);
                    mTemp <<= 8;
                    mTemp |= ((this.payload[4]) & 0xff);
                    measuredTemperature = mTemp / 10.0; // temperature over 25.5
                                                        // uses extra bit in
                                                        // desiredTemperature
                                                        // byte
                    if (measuredTemperature < 4.5) {
                        measuredTemperature = null;
                    }
                }
            } else if (this.payload.length == THERMOSTAT_STATE_TIME_PAYLOAD_LEN) {
                // TODO extract Date/Time if payload is
                // THERMOSTAT_STATE_TIME_PAYLOAD_LEN
            }
        } else {
            logger.error("Got " + this.msgType + " message with incorrect length!");
        }
    }

    /**
     * Print output as decoded fields
     */
    @Override
    protected void printFormattedPayload() {
        logger.debug("\tDesired Temperature => " + desiredTemperature);
        logger.debug("\tMeasured Temperature=> " + measuredTemperature);
        logger.debug("\tValve Position      => " + valvePos);
        logger.debug("\tControl Mode        => " + ctrlMode);
        logger.debug("\tDST Active          => " + dstActive);
        logger.debug("\tLAN Gateway         => " + lanGateway);
        logger.debug("\tPanel locked        => " + lockedForManualSetPoint);
        logger.debug("\tRF Error            => " + rfError);
        logger.debug("\tBattery Low         => " + batteryLow);
        if (untilDateTime != null) {
            logger.debug("\tUntil DateTime      => " + untilDateTime.get(Calendar.YEAR) + "-"
                    + (untilDateTime.get(Calendar.MONTH) + 1) + "-" + untilDateTime.get(Calendar.DAY_OF_MONTH) + " "
                    + untilDateTime.get(Calendar.HOUR_OF_DAY) + ":" + untilDateTime.get(Calendar.MINUTE) + ":"
                    + untilDateTime.get(Calendar.SECOND));
        }
    }

    public Double getDesiredTemperature() {
        return desiredTemperature;
    }

    public Double getMeasuredTemperature() {
        return measuredTemperature;
    }

    public int getValvePos() {
        return (valvePos & 0xff);
    }

    public boolean getBatteryLow() {
        return batteryLow;
    }

    public ThermostatControlMode getControlMode() {
        return ctrlMode;
    }

}
