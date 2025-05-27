package se.kth.iv1350.amazingpos.view;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.kth.iv1350.amazingpos.controller.Controller;
import se.kth.iv1350.amazingpos.integration.Printer;
import se.kth.iv1350.amazingpos.integration.RegistryCreator;
import se.kth.iv1350.amazingpos.model.PaymentStrategy;

public class ViewTest {
    private PaymentStrategy paymentType;
    private Controller contr;
    static  RegistryCreator externalSystems;
    static  Printer printer;
    private View view;

    @BeforeEach
    void setUpClass(){
        printer = new Printer();
        externalSystems = new RegistryCreator();

        this.contr = new Controller(externalSystems, printer);
        this.view = new View(contr);
    }
    @AfterEach
    void tearDownClass(){
        printer = null;
        externalSystems = null;
        contr = null;
        view = null;
    }

    @Test
    void testRunFakeExecution() {
        view.runFakeExecution();
    }
}
