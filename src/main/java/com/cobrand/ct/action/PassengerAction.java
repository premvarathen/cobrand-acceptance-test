package com.cobrand.ct.action;

import static aa.ct.fly.bdd.pnr.pnrcreation.PNRToolVersion.V1;
import static com.cobrand.citi.constant.CobrandConstants.DELETE_PNR_UPON_TEST_COMPLETION;
import static com.cobrand.citi.constant.CobrandConstants.SESSION_KEY_RETRY_ELIG_COUNTER;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.cobrand.ct.controller.ReservationWrapper;
import com.cobrand.ct.domain.Reservation;
import com.cobrand.ct.mapper.ReservationMapper;
import com.cobrand.ct.sabre.CobrandSabrePnrCreationProvider;
import com.cobrand.ct.utils.ActionUtils;

import aa.ct.fly.bdd.pnr.pnrcreation.PNRToolVersion;
import io.restassured.response.ValidatableResponse;
import net.thucydides.core.annotations.Step;

@Component
public class PassengerAction {
    private final PassengerEligibilityAction passengerEligibilityAction = new PassengerEligibilityAction();
    private final ActionUtils actionUtils = new ActionUtils();
    private CobrandSabrePnrCreationProvider sabrePnrCreationProvider = new CobrandSabrePnrCreationProvider();
    private ReservationMapper reservationMapper = new ReservationMapper();

    public String passengerRequestsPNR(List<Map<String, String>> reservationRawData) {
        Reservation reservation = reservationMapper.map(reservationRawData);
        return sabrePnrCreationProvider.createWithCommands(reservation, PNRToolVersion.V1);
    }
    
    public String createPNR(ReservationWrapper reservation) {
        return sabrePnrCreationProvider.createWithCommandsForWrapper(reservation, PNRToolVersion.V1);
    }

    @Step
    public ValidatableResponse passengerChecksBagsEligibility(String recordLocator) {
        return actionUtils.invokeWithRetry(() -> passengerEligibilityAction.passengerChecksBagsEligibility(recordLocator), SESSION_KEY_RETRY_ELIG_COUNTER);
    }

    public void deletePnr(String recordLocator) {
        sabrePnrCreationProvider.deletePnr(recordLocator, DELETE_PNR_UPON_TEST_COMPLETION, V1);
    }

    public void validatesAndContinueOnlyIfBagsProductEligible(ValidatableResponse response) {
        passengerEligibilityAction.validatesBagsProductEligible(response);
    }
}
