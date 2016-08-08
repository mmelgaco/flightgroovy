package com.mttnow.rest

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController;;
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.RESTClient
import static groovyx.net.http.Method.GET
import static groovyx.net.http.ContentType.XML
import com.mttnow.domain.Availability


@RestController
class FlightController {

    @RequestMapping(value="/{origin}/{destination}/{departureDate}/{returnDate}/{pax}")
    def list(@PathVariable String origin, 
        @PathVariable String destination, @PathVariable String departureDate,
        @PathVariable String returnDate, @PathVariable String pax) {
        
        def zorillo = new RESTClient("http://private-72732-mockairline.apiary-mock.com/")
            
        StringBuilder url = new StringBuilder("flights/");
        url.append()
            .append(origin)
            .append("/").append(destination)
            .append("/").append(departureDate)
            .append("/").append(returnDate)
            .append("/").append(pax);
            
        def res = zorillo.get(path:url.toString())
        
        def flights = []
        for(flight in res.data.Flight){
            
            Availability aval = new Availability(
                flight.CarrierCode.text(),
                flight.FlightDesignator.text(),
                flight.OriginAirport.text(),
                flight.DestinationAirport.text(),
                flight.DepartureDate.text(),
                flight.ArrivalDate.text()
                );
            
            flights.add(aval);
                        
            def FIF = flight.Fares.Fare.find { it.'@class' == 'FIF' };
            def CIF = flight.Fares.Fare.find { it.'@class' == 'CIF' };
            def YIF = flight.Fares.Fare.find { it.'@class' == 'YIF' };
            doFare(aval, FIF, CIF, YIF);

        }

                
        [availability: flights]
        
    }
    
    def doFare(aval, FIF, CIF, YIF){
        def BasePrice = FIF.BasePrice.text()
        def Fees = FIF.Fees.text()
        def Tax = FIF.Tax.text()
        
        def BasePriceCif = CIF.BasePrice.text()
        def FeesCif = CIF.Fees.text()
        def TaxCif = CIF.Tax.text()
        
        def BasePriceYif = YIF.BasePrice.text()
        def FeesYif = YIF.Fees.text()
        def TaxYif = YIF.Tax.text()
        
        aval.flight.farePrices = new Availability.Flight.FarePrices()
        aval.flight.farePrices.first = new Availability.Flight.FarePrices.Price()
        aval.flight.farePrices.first.ticket = new Availability.Flight.FarePrices.Price.Currency();
        aval.flight.farePrices.first.ticket.setValues(BasePrice);
        aval.flight.farePrices.first.bookingFee = new Availability.Flight.FarePrices.Price.Currency();
        aval.flight.farePrices.first.bookingFee.setValues(Fees);
        aval.flight.farePrices.first.tax = new Availability.Flight.FarePrices.Price.Currency();
        aval.flight.farePrices.first.tax.setValues(Tax);
        
        aval.flight.farePrices.business = new Availability.Flight.FarePrices.Price()
        aval.flight.farePrices.business.ticket = new Availability.Flight.FarePrices.Price.Currency();
        aval.flight.farePrices.business.ticket.setValues(BasePriceCif);
        aval.flight.farePrices.business.bookingFee = new Availability.Flight.FarePrices.Price.Currency();
        aval.flight.farePrices.business.bookingFee.setValues(FeesCif);
        aval.flight.farePrices.business.tax = new Availability.Flight.FarePrices.Price.Currency();
        aval.flight.farePrices.business.tax.setValues(TaxCif);
        
        aval.flight.farePrices.economy = new Availability.Flight.FarePrices.Price()
        aval.flight.farePrices.economy.ticket = new Availability.Flight.FarePrices.Price.Currency();
        aval.flight.farePrices.economy.ticket.setValues(BasePriceYif);
        aval.flight.farePrices.economy.bookingFee = new Availability.Flight.FarePrices.Price.Currency();
        aval.flight.farePrices.economy.bookingFee.setValues(FeesYif);
        aval.flight.farePrices.economy.tax = new Availability.Flight.FarePrices.Price.Currency();
        aval.flight.farePrices.economy.tax.setValues(TaxYif);
    }

}
