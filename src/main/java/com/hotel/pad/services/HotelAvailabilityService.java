package com.hotel.pad.services;


import com.hotel.pad.client.HotelAvailabilityClient;
import com.hotel.pad.mappers.AvailRequestSegmentsMapper;
import com.hotel.pad.mappers.PosMapper;
import lombok.extern.slf4j.Slf4j;
import org.opentravel.ota._2003._05.OTAHotelAvailRQ;
import org.opentravel.ota._2003._05.OTAHotelAvailRS;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class HotelAvailabilityService {

    private final PosMapper posMapper;
    private final AvailRequestSegmentsMapper availRequestSegmentsMapper;
    private final HotelAvailabilityClient hotelAvailabilityClient;


    public HotelAvailabilityService(PosMapper posMapper, AvailRequestSegmentsMapper availRequestSegmentsMapper, HotelAvailabilityClient hotelAvailabilityClient) {
        this.posMapper = posMapper;
        this.availRequestSegmentsMapper = availRequestSegmentsMapper;
        this.hotelAvailabilityClient = hotelAvailabilityClient;
    }


    public void getAvailableHotelItemsFromSupplier(){
        OTAHotelAvailRQ hotelAvailRQ = new OTAHotelAvailRQ();
        hotelAvailRQ.setPOS(posMapper.mapPOS());
        hotelAvailRQ.setAvailRequestSegments(availRequestSegmentsMapper.mapAvailRequestSegments());
        try {
            OTAHotelAvailRS hotelAvailRS = hotelAvailabilityClient.restClient(hotelAvailRQ);
            if(hotelAvailRS.getErrors().getError() !=null){
                log.info("Successful OTA hotel Avail Response",hotelAvailRS);
            }
        } catch (Exception e) {
            log.error("Response is not received" + e);
        }

    }
}