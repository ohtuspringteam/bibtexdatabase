package fluentlenium;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author santeri
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class NewArticleTest extends FluentTest {
    @Value("${local.server.port}")
    private int serverPort;
    public WebDriver webDriver = new HtmlUnitDriver();
    
    public WebDriver getDefaultDriver(){
        return webDriver;
    }
    
    @Test
    public void submitArticle(){
        goTo("http://localhost:" +serverPort+"/articles/new");
        fill("#author").with("Santeri");
        fill("#title").with("Eeppinen kandi");
        fill("#citation").with("artsu");
        fill("#journal").with("test");
        fill("#year").with("1999");
        fill("#volume").with("1");
        submit("button[type=submit]");
        assertTrue(pageSource().contains("New article created"));
    }
}
