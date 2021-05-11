package test;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pages.DebetCard;
import pages.HomePage;
import pages.YouthCard;
import test.base.BaseTest;

@DisplayName("Simple Test to get Youth Card")
public class SimpleTest extends BaseTest {
    
    /**
     * Простой линейный тест для проверки заполнения полей при оформлении
     * молодежной карты
     * @author vadim
     */
    @Test
    public void SimpleGetYouthCardTest() {
        HomePage homePage = new HomePage(driver);
        DebetCard debetCard = new DebetCard(driver);
        YouthCard youthCard = new YouthCard(driver);

        homePage.clickButtonMenu();
        homePage.clickButtonPodMenu();
        debetCard.checkLabelPage();
        debetCard.checkAndClickCards();
        youthCard.checkLabelPage();
        // Из-за скрола, который возникает после перехода нажатие этой кнопки
        // отключено
        //youthCard.clickButtonIssueOnline();
        youthCard.fillInputs();
        youthCard.clickButtonNext();
        youthCard.isErrorCheckInput();
    }
    
    /**
     * Тест, аналогичный первому, только запуском с тремя наборами параметров
     */
    @ParameterizedTest
    @CsvSource({
        "Сергеев,Сергей,Сергеевич,SERGEY SERGEEV,"
                + "12.05.2003,serser@mail.ru,9851274967",
        "Евгеньев,Евгений,Евгеньевич,EVGENY EVGENEV,"
                + "25.10.2004,evgevg@mail.ru,9749369784",
        "Петров,Петр,Петрович,PETR PETROV,"
                + "12.07.2002,petpet@mail.ru,9749689142"
    })
    public void ParameterizedGetYouthCardTest(String lastName, String firstName,
            String middleName, String cardName, String birthDate,
            String email, String phone) {
        HomePage homePage = new HomePage(driver);
        DebetCard debetCard = new DebetCard(driver);
        YouthCard youthCard = new YouthCard(driver);

        homePage.clickButtonMenu();
        homePage.clickButtonPodMenu();
        debetCard.checkLabelPage();
        debetCard.checkAndClickCards();
        youthCard.checkLabelPage();
        youthCard.fillInputs(lastName, firstName, middleName, cardName,
                birthDate, email, phone);
        youthCard.clickButtonNext();
        youthCard.isErrorCheckInput();
    }
}