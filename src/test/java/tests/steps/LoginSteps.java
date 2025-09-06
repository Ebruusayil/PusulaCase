package tests.steps;

import io.cucumber.java.en.Given;
import tests.pages.LoginPage;


public class LoginSteps {
    private final LoginPage login = new LoginPage();

    @Given("uygulama açılır")
    public void open_app(){ login.open(); }

    @Given("müşteri {string} seçilir")
    public void musteri_seciliir(String musteriAdi){ login.ensureCustomer(musteriAdi); }

    @Given("kullanıcı {string} ve {string} ile giriş yapar")
    public void login_with(String u, String p){
        login.login(u, p);
    }

}
