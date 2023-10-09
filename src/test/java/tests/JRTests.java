package tests;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pages.JRMainPage;
import pages.components.VerifyComponent;

import java.util.stream.Stream;

@Tags({@Tag("UI"), @Tag("MainPage")})
public class JRTests extends TestBase {
    JRMainPage javaRushMainPage = new JRMainPage();
    VerifyComponent verifyComponent = new VerifyComponent();

    @CsvSource(value = {
            "NEWS, https://javarush.com/news",
            "FORUM, https://javarush.com/forum",
            "INTERNSHIP, https://javarush.com/internship"
    })
    @ParameterizedTest(name = "Открытие страницы {1} при клике на {0}")
    @Tag("Link")
    void openStudyGroupLinkTest(String target, String actualUrl) {
        javaRushMainPage.openPage();
        javaRushMainPage.clickNavbarLinkByDataTarget(target);
        verifyComponent.verifyUrl(actualUrl);
    }

    @CsvFileSource(resources = "/test_data.csv")
    @ParameterizedTest(name = "При нажатии на пункт меню боковой панели <{0}> открывается страница <{1}>")
    @Tag("Link")
    void openCommunityGroupLinkTest(String value, String url) {
        javaRushMainPage.openPage();
        javaRushMainPage.clickNavbarLinkBySearchingText(value);
        verifyComponent.verifySiteTitle(url);
    }

    static Stream<Arguments> selenideButtonsTest() {
        return Stream.of(
                Arguments.of("Отзывы", "Отзывы"),
                Arguments.of("О нас", "О JavaRush")
        );
    }

    @MethodSource("selenideButtonsTest")
    @ParameterizedTest(name = "При нажатии на кнопку навигации <{0}> открывается страница с заголовком <{1}>")
    @Tag("Link")
    void openHeaderNavLinkTest(String navLink, String article) {
        javaRushMainPage.openPage();
        javaRushMainPage.clickHeaderLink(navLink);
        verifyComponent.verifyPageArticleText(article);
    }
}
