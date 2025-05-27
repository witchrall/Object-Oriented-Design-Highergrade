package se.kth.iv1350.amazingpos.view;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TotalRevenueViewTest {
    private TotalRevenueView totalRevenueView = new TotalRevenueView();
    PrintStream originalOut;
    ByteArrayOutputStream outContent;
    private double revenue = 450;
   
    @BeforeEach
    void setUp(){
        outContent = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outContent));        
    }

    @AfterEach
    void tearDownClass(){
        System.setOut(originalOut);
    }

    @Test
    void testDoShowTotalIncome() {
        try{
            totalRevenueView.doShowTotalIncome(revenue);
        }catch(Exception e){

        }

        String output = outContent.toString();

        assertTrue(output.contains("#### This is not part of the receipt ####"), "First line incorrect");
        assertTrue(output.contains("#### Will be viewed on it's own GUI ####"), "Second line incorrect");
        assertTrue(output.contains("####Total Revenue from todays sales####"), "Third line incorrect");
        assertTrue(output.contains("Total revenue would be: 450.0"), "Fourth line incorrect");
        assertTrue(output.contains("###################################"), "Last line incorrect");
    }
}
