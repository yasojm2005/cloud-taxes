package uy.com.geocom.bdd;
import io.cucumber.spring.CucumberContextConfiguration;
import uy.com.geocom.TaxServiceApplication;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@CucumberContextConfiguration
@SpringBootTest(classes = TaxServiceApplication.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class CucumberSpringConfiguration { }
