package fluentlenium;


import org.fluentlenium.adapter.FluentTest;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import wad.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class NewTechreportTest extends FluentTest {
    
    @Value("${local.server.port}")
    private int serverPort;
    public WebDriver webDriver = new HtmlUnitDriver();
    
    public WebDriver getDefaultDriver(){
        return webDriver;
    }
    
    @Test
    public void submitBook(){
        goTo("http://localhost:" +serverPort+"/techreports/new");
        fill("#citation").with("raportti");
        fill("#author").with("Santeri");
        fill("#institution").with("Kustantamo");
        fill("#year").with("1234");
        fill("#title").with("Eeppinen seksiopas");
        submit("button[type=submit]");
        assertTrue(pageSource().contains("New techreport created"));
    }
    
}
