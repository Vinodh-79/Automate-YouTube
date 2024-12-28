package demo.wrappers;

import org.checkerframework.checker.units.qual.m;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class Wrappers {
    /*
     * Write your selenium wrappers here
     */
    public static boolean goToUrlAndWait(WebDriver driver, String url) {

        driver.get(url);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(driver1 -> ((JavascriptExecutor) driver1).executeScript("return document.readyState")
                .equals("complete"));

        return true;
    }

    public static void clickTillUnclickable(WebDriver driver, By locator, Integer maxIterations)
            throws InterruptedException {
        Integer numInteger = 0;

        while (numInteger < maxIterations) {
            try {
                findElementAndClick(driver, locator);
            } catch (TimeoutException e) {
                // TODO: handle exception
                break;
                //System.out.println("Stoppping - " + e.getMessage());
            }
        }
    }

    public static void findElementAndClick(WebDriver driver, By locator) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait for the Element to be clickable
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));

        // Wait untill the element is visble after scrolling
        wait.until(ExpectedConditions.visibilityOf(element));

        element.click();
        Thread.sleep(1000);
    }

    public static void findElementAndClickWE(WebDriver driver, WebElement we, By locator) {

        WebElement element = we.findElement(locator);

        // Scroll to the element
        ((JavascriptExecutor) driver).executeAsyncScript("arguments[0].scrollIntoView(true);", element);

        element.click();
    }

    public static String findElementAndPrint(WebDriver driver, By locator, int elementNo) {

        WebElement we = driver.findElements(locator).get(elementNo);

        // Return the Result
        String txt = we.getText();
        return txt;
    }

    public static String findElementAndPrintWE(WebDriver driver, By locator, WebElement we, int elementNo) {

        WebElement element = we.findElements(locator).get(elementNo);

        // Return the Result
        String txt = element.getText();
        return txt;
    }

    public static long convertToNumericValue(String value) {
        // Trim the String to remove any leading or trailing spaces
        value = value.trim().toUpperCase();

        // Check if the last character is non-numeric and determine the multiplier

        char lastChar = value.charAt(value.length() - 1);
        int multiplier = 1;
        switch (lastChar) {
            case 'K':
                multiplier = 1000;
                break;
            case 'M':
                multiplier = 1000000;
                break;
            case 'B':
                multiplier = 1000000000;
                break;
            default:
                // If the last character is Numeric then parse the entire string
                if (Character.isDigit(lastChar)) {
                    return Long.parseLong(value);
                }
                throw new IllegalArgumentException("Invalid Format " + value);
        }

        // Extract the numeric part before the last character
        String numericPart = value.substring(0, value.length() - 1);
        double number = Double.parseDouble(numericPart);

        // Calculate the final Value

        return (long) (number * multiplier);

    }
}
