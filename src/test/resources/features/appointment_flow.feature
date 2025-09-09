@appointment
Feature: Randevu akışı

  Background:
    Given uygulama açılır
    And müşteri "Nişantaşı Klinik" seçilir
    And kullanıcı "Test" ve "Test123." ile giriş yapar

  Scenario: Olustur - Tamamla - Sil
    Given Randevu modülüne girilir
    When Filtre paneli açılır
    And doktor filtresi temizlenir
    And doktor olarak "Doç. Dr.Ziya Bey" seçilir ve filtre uygulanır
    When takvimde hedef slota tıklanır
    And randevu formunda hasta "ebru ebru" aranır ve seçilir
    And "ebru ebru" isimli hasta Doğum Tarihi: "01.01.2001" kayıtlıdır
    And randevu tarihi bugün, saat en yakın ve süre 30 dakika olarak girilir
    And "Doç. Dr.Ziya Bey" ve "Çocuk Doktoru" ile randevu oluşturulur
    Then randevu başarıyla oluşturulmalıdır
    When randevu kartina tiklanir
    And check in yapilir
    And admission tiklanir
    And hasta kabul kaydedilir
    And telefon doğrulama popupı kapatılır
    When modüller menüsü açılır ve Randevuya dönülür
    And randevu kartı "EBRU EBRU" tekrar açılır
    When randevu tamamlanır
    Then randevu durumu "Tamamlandı" olmalıdır
    When randevu silinir
    When randevu kartı silinir
    When silme bildirimi gorunmelidir
    Then randevu silme işlemi başarılı olmalıdır
