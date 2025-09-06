package tests.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import tests.pages.AppointmentPage;
import tests.pages.VisitCancelPage;

public class VisitCancelSteps {

    private final VisitCancelPage cancel = new VisitCancelPage();
    private final AppointmentPage appt   = new AppointmentPage();


    private final String patientName = "EBRU EBRU";

    @When("randevu silinir")
    public void randevu_silinir() {
        cancel.openFinanceCard();
        cancel.openMoreMenu();
        cancel.chooseCancelVisit();
        cancel.confirmCancel();
        appt.openModulesDrawer();
        appt.selectRandevuFromDrawer();
        appt.openAppointmentCard(patientName);
    }


}
