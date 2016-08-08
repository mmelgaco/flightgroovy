package com.mttnow.domain

import groovy.transform.CompileStatic

import java.math.RoundingMode
import java.text.SimpleDateFormat

import org.apache.commons.lang.StringUtils
import org.joda.time.DateTime
import org.joda.time.Duration

@CompileStatic
class Availability {

    Availability(String operator, 
        String flightNumber,
        String departsFrom,
        String arrivesAt,
        String departsOn,
        String arrivesOn){
        
        flight = new Flight();
        flight.operator = operator;
        flight.flightNumber = flightNumber;
        flight.departsFrom = departsFrom;
        flight.arrivesAt = arrivesAt;
        flight.setDepartsOn(departsOn, arrivesOn);
        
    }
    
    Flight flight

    class Flight {

        String operator;
        String flightNumber;
        String departsFrom;
        String arrivesAt;
        FlightDateTime departsOn;
        FlightDateTime arrivesOn;
        String flightTime;
        FarePrices farePrices;
        
        def setDepartsOn(String departsOnStr, String arrivesOnStr){
            DateTime departsDt = new DateTime(departsOnStr) ;
            this.departsOn = new FlightDateTime();
            this.departsOn.date = new SimpleDateFormat("dd-MM-yyyy").format(departsDt.toDate());
            this.departsOn.time = new SimpleDateFormat("hh:mma").format(departsDt.toDate());
            DateTime arrivesDt = new DateTime(arrivesOnStr) ;
            this.arrivesOn = new FlightDateTime();
            this.arrivesOn.date = new SimpleDateFormat("dd-MM-yyyy").format(arrivesDt.toDate());
            this.arrivesOn.time = new SimpleDateFormat("hh:mma").format(arrivesDt.toDate());
            
            Duration duration = new Duration(departsDt, arrivesDt);
            this.flightTime = StringUtils.leftPad(String.valueOf(duration.getStandardHours()), 2, '0') + ":" + StringUtils.leftPad(String.valueOf(Math.round(duration.getStandardMinutes() % 60)), 2, '0');
        }

        class FlightDateTime {
            
            String date;
            String time;
    
        }

        class FarePrices {

            Price first;
            Price business;
            Price economy;
            
            class Price {
                
                Currency ticket;
                Currency bookingFee;
                Currency tax;
                
                class Currency {
                        
                    String currency;
                    BigDecimal amount;
                        
                    def setValues(String value){
                        String[] splitted = value.split(" ");
                        this.currency = splitted[0];
                        this.amount = new BigDecimal(splitted[1]).setScale(2, RoundingMode.UNNECESSARY);
                    }
                }
            }
        }

    }
    
    

}
