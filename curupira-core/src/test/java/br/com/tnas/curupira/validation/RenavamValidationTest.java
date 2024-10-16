package br.com.tnas.curupira.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.com.tnas.curupira.message.ValidationMessage;
import br.com.tnas.curupira.validator.RenavamValidator;
import br.com.tnas.curupira.validator.error.InvalidStateException;

public class RenavamValidationTest {

    private final String validUnformattedRenavam1 = "00639884962";
    private final String validUnformattedRenavam2 = "00811443620";
    private final String validFormattedRenavam1 = "0073.640767-7";
    private final String validFormattedRenavam2 = "0096.525195-0";
    private final String renavamUnformattedWithInvalidCheckDigit = "00639884969";
    private final String renavamFormattedWithInvalidCheckDigit = "0096.525195-2";
    private final String renavamWithLessThenElevenDigits = "9999999999";
    private final String renavamWithMoreThenElevenDigits = "999999999999";
    private final String renavamWithNineDigits = "639884962";

    @Test
    public void shouldValidateValidUnformatedRenavam() {
        RenavamValidator validator = new RenavamValidator();
        validator.assertValid(validUnformattedRenavam1);
        validator.assertValid(validUnformattedRenavam2);

        List<ValidationMessage> errorMessages = validator.invalidMessagesFor(validUnformattedRenavam1);
        assertTrue(errorMessages.isEmpty());
    }

    @Test
    public void shouldValidateFormattedValidRenavam() {
        RenavamValidator validator = new RenavamValidator(true);
        validator.assertValid(validFormattedRenavam1);
        validator.assertValid(validFormattedRenavam2);

        List<ValidationMessage> errorMessages = validator.invalidMessagesFor(validFormattedRenavam1);
        assertTrue(errorMessages.isEmpty());
    }

    @Test
    public void shouldConsiderAValidFormattedRenavamAsEligible() {
        RenavamValidator validator = new RenavamValidator(true);
        assertTrue(validator.isEligible(validFormattedRenavam1));
        assertTrue(validator.isEligible(validFormattedRenavam2));
        assertTrue(validator.isEligible(renavamFormattedWithInvalidCheckDigit));
    }

    @Test
    public void shouldConsiderAValidUnformattedRenavamAsEligible() {
        RenavamValidator validator = new RenavamValidator();
        assertTrue(validator.isEligible(validUnformattedRenavam1), "Renamvam " + validUnformattedRenavam1 + " must be eligible.");
        assertTrue(validator.isEligible(validUnformattedRenavam2));
        assertTrue(validator.isEligible(renavamUnformattedWithInvalidCheckDigit));
    }

    @Test
    public void shouldNotValidadeUnformattedRenavamWithInvalidCheckDigit() {
        RenavamValidator validator = new RenavamValidator();
        Assertions.assertThrows(InvalidStateException.class, () -> validator.assertValid(renavamUnformattedWithInvalidCheckDigit));
    }

    @Test
    public void shouldNotValidadeFormattedRenavamWithInvalidCheckDigit() {
        RenavamValidator validator = new RenavamValidator(true);
        Assertions.assertThrows(InvalidStateException.class, () -> validator.assertValid(renavamUnformattedWithInvalidCheckDigit));
    }

    @Test
    public void onlyRenavamWithNineOrElevenDigitsAreEligible() {
        RenavamValidator validator = new RenavamValidator();
        assertTrue(validator.isEligible(renavamWithNineDigits));
        assertTrue(validator.isEligible(renavamWithLessThenElevenDigits));
        assertFalse(validator.isEligible(renavamWithMoreThenElevenDigits));
    }

    @Test
    public void shouldNotValidateARenavamWithLessThenElevenDigits() {
        RenavamValidator validator = new RenavamValidator();
        Assertions.assertThrows(InvalidStateException.class, () -> validator.assertValid(renavamWithLessThenElevenDigits));
    }

    @Test
    public void shouldNotValidateARenavamWithMoreThenElevenDigits() {
        RenavamValidator validator = new RenavamValidator();
        Assertions.assertThrows(InvalidStateException.class, () -> validator.assertValid(renavamWithMoreThenElevenDigits));
    }

    @Test
    public void renavamMustHaveOnlyNumbers() {
        RenavamValidator validator = new RenavamValidator();
        assertFalse(validator.isEligible("99999999x"));
        assertFalse(validator.isEligible("9999999 9"));
    }

    @Test
    public void shouldGenerateExplanatoryErrorMessagesForUnformattedRenavam() {
        RenavamValidator validator = new RenavamValidator();
        List<ValidationMessage> invalidMessagesFor = null;

        invalidMessagesFor = validator.invalidMessagesFor("999");
        assertTrue(invalidMessagesFor.size() == 1);
        assertEquals("RenavamError : INVALID DIGITS", invalidMessagesFor.get(0).getMessage());

        invalidMessagesFor = validator.invalidMessagesFor(renavamUnformattedWithInvalidCheckDigit);
        assertTrue(invalidMessagesFor.size() == 1);
        assertEquals("RenavamError : INVALID CHECK DIGITS", invalidMessagesFor.get(0).getMessage());
    }

    @Test
    public void shouldGenerateExplanatoryErrorMessagesForFormattedRenavam() {
        RenavamValidator validator = new RenavamValidator(true);
        List<ValidationMessage> invalidMessagesFor = null;

        invalidMessagesFor = validator.invalidMessagesFor("999");
        assertTrue(invalidMessagesFor.size() == 1);
        assertEquals("RenavamError : INVALID FORMAT", invalidMessagesFor.get(0).getMessage());

        invalidMessagesFor = validator.invalidMessagesFor(renavamFormattedWithInvalidCheckDigit);
        assertTrue(invalidMessagesFor.size() == 1);
        assertEquals("RenavamError : INVALID CHECK DIGITS", invalidMessagesFor.get(0).getMessage());
    }

    @Test
    public void shouldUseAnSimpleMessageProducerAsDefault() {
        RenavamValidator validator = new RenavamValidator();
        try {
            validator.assertValid(renavamFormattedWithInvalidCheckDigit);
            fail();
        } catch (InvalidStateException e) {
            List<ValidationMessage> errors = e.getInvalidMessages();
            assertTrue(errors.size() == 1);
            assertEquals("RenavamError : INVALID CHECK DIGITS", errors.get(0).getMessage());
        }
    }

    @Test
    public void shouldValidateValidRenavamWithNineDigits() {
        RenavamValidator validator = new RenavamValidator();
        validator.assertValid(renavamWithNineDigits);

        List<ValidationMessage> errorMessages = validator.invalidMessagesFor(renavamWithNineDigits);
        assertTrue(errorMessages.isEmpty());
    }

    @Test
    public void shouldGenerateValidFormattedRenavam() {
        final RenavamValidator renavamValidator = new RenavamValidator(true);
        final String generated = renavamValidator.generateRandomValid();
        renavamValidator.assertValid(generated);
    }

    @Test
    public void shouldGenerateValidUnformattedRenavam() {
        final RenavamValidator renavamValidator = new RenavamValidator();
        final String generated = renavamValidator.generateRandomValid();
        renavamValidator.assertValid(generated);
    }
}
