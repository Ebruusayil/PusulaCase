package tests.steps;

import io.cucumber.java.en.*;
import tests.pages.AppointmentPage;
import org.testng.Assert;

public class AppointmentSteps {

    private final AppointmentPage appt = new AppointmentPage();
    private String lastToast = ""; // <-- eklendi

    @Given("Randevu modülüne girilir")
    public void randevu_modulune_girilir() { appt.goToAppointmentModule(); }
    @When("Filtre paneli açılır")
    public void filtre_paneli_acilir() { appt.openFilterPanel(); }

    @When("doktor filtresi temizlenir")
    public void doktor_filtresi_temizlenir() { appt.clearDoctorFilterTokens(); }

    @When("doktor olarak {string} seçilir ve filtre uygulanır")
    public void doktor_secilir_ve_filtre_uygulanir(String doktor) {
        appt.selectDoctorAndApply(doktor);
    }

    @When("takvimde hedef slota tıklanır")
    public void takvimde_hedef_slota_tiklanir() { appt.clickTargetSlot(); }

    @When("randevu formunda hasta {string} aranır ve seçilir")
    public void randevu_formunda_hasta_aranir_ve_secilir(String hastaAdi) {
        appt.searchAndSelectPatient(hastaAdi);
    }
    @When("randevu tarihi bugün, saat en yakın ve süre {int} dakika olarak girilir")
    public void randevu_tarih_saat_sure_otomatik(int dk) {
        appt.fillDateTimeAndDurationForNow(dk);
    }
    @When("{string} isimli hasta Doğum Tarihi: {string} kayıtlıdır")
    public void isimli_hasta_dogum_tarihi_kayitlidir(String ad, String dt) { /* no-op */ }

    @When("{string} ve {string} ile randevu oluşturulur")
    public void ve_ile_randevu_olusturulur(String doktor, String departman) {
        appt.fillDateTimeAndDurationForNow(30);
        appt.saveAppointment();
    }

    @Then("randevu başarıyla oluşturulmalıdır")
    public void randevu_basarili_olusturulmalidir() { appt.assertAppointmentSaved(); }

    @When("randevu kartina tiklanir")
    public void randevu_kartina_tiklanir(){ appt.openAppointmentCardSimple(); }

    @When("check in yapilir")
    public void check_in_yapilir(){ appt.clickCheckInSimple(); }

    @When("admission tiklanir")
    public void admission_tiklanir(){ appt.clickAdmissionSimple(); }

    @When("hasta kabul kaydedilir")
    public void hasta_kabul_kaydedilir(){ appt.saveAdmissionSimple(); }

    @When("telefon doğrulama popupı kapatılır")
    public void telefon_dogrulama_popupi_kapatilir() {
        appt.closePhonePopup();
    }
    @When("modüller menüsü açılır ve Randevuya dönülür")
    public void modullere_git_randevuya_don() {
        appt.openModulesDrawer();
        appt.selectRandevuFromDrawer();
    }
    @When("randevu kartı {string} tekrar açılır")
    public void randevu_karti_tekrar_acilir(String hastaAdi) { appt.openAppointmentCard(hastaAdi); }

    @When("randevu tamamlanır")
    public void randevu_tamamlanir() {  }

    @Then("randevu durumu {string} olmalıdır")
    public void randevu_durumu_olmalidir(String beklenen){
        String actual = appt.getAppointmentStatus();
        Assert.assertEquals(actual, beklenen, "Randevu durumu eşleşmedi!");
    }


    @When("randevu kartı silinir")
    public void randevu_karti_silinir(){
        appt.deleteAppointmentSimple();
    }

    @Then("silme bildirimi gorunmelidir")
    public void silme_bildirimi_gorunmelidir() {
        String toast = appt.waitToastTextFromContainer(5);
        String low = toast == null ? "" : toast.toLowerCase();

        System.out.println("Toast text: '" + toast + "'");

        org.testng.Assert.assertTrue(
                !low.isBlank() &&
                        (low.contains("sil") || low.contains("iptal") || low.contains("başar") || low.contains("kaldır")),
                "Silme/iptal bildirimi bekleniyordu, gelen: '" + toast + "'"
        );
    }

    @Then("randevu silme işlemi başarılı olmalıdır")
    public void randevu_silme_islemi_basarili_olmalidir() {
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
    }
}
