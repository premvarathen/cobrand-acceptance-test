package com.cobrand.ct.sabre;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.cobrand.ct.controller.ReservationWrapper;
import com.cobrand.ct.domain.Reservation;

public class VelocityTemplateDeterminer {

    public static Map<String, String> velocityTemplateMap = new HashMap<>();

    static {
        velocityTemplateMap.put("NON_STOP", "pnr_non_stop.vm");
        velocityTemplateMap.put("WITH_STOP", "templates/pnr_withstops.vm");
        velocityTemplateMap.put("SAMPLE_NON_STOP", "sample_pnr_non_stop.vm");
    }

    public String determine(Reservation reservation) {
        return velocityTemplateMap.get(buildKey(reservation));
    }
    public String determine(ReservationWrapper reservation) {
        return velocityTemplateMap.get("SAMPLE_NON_STOP");
    }

    private String buildKey(Reservation reservation) {
        return StringUtils.isNotBlank(reservation.getFlights().get(0).getConnectCity()) ? "WITH_STOP" : "NON_STOP";
    }
}
