package com.generic.utils;

import lombok.extern.log4j.Log4j2;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static java.time.ZoneId.systemDefault;
import static java.time.format.DateTimeFormatter.ofPattern;

/**
 * The type Date util.
 */
@Log4j2
public class DateUtil {

    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    /**
     * Convert date to local date time local date time.
     *
     * @param date the date
     * @return the local date time
     */
    public static LocalDateTime convertDateToLocalDateTime( Date date ) {
        return date.toInstant( ).atZone( systemDefault( ) ).toLocalDateTime( );
    }

    /**
     * Convert date to local date local date.
     *
     * @param date the date
     * @return the local date
     */
    public static LocalDate convertDateToLocalDate( Date date ) {
        return date.toInstant( ).atZone( systemDefault( ) ).toLocalDate( );
    }

    /**
     * Parse date date.
     *
     * @param dateString  the date string
     * @param datePattern the date pattern
     * @return the date
     */
    public static Date parseDate( String dateString, String datePattern ) {
        final DateFormat dateFormat = new SimpleDateFormat( datePattern );
        try {
            return dateFormat.parse( dateString );
        } catch ( ParseException e ) {
            log.debug( "Error in parsing Date '" + dateString + "' in pattern '" + datePattern + "' " );
            log.error( "e: ", e );
        }
        return null;
    }

    /**
     * Parse local date time local date time.
     *
     * @param dateString  the date string
     * @param datePattern the date pattern
     * @return the local date time
     */
    public static LocalDateTime parseLocalDateTime( String dateString, String datePattern ) {
        return LocalDateTime.parse( dateString, ofPattern( datePattern ) );
    }

    /**
     * Parse local date local date.
     *
     * @param dateString  the date string
     * @param datePattern the date pattern
     * @return the local date
     */
    public static LocalDate parseLocalDate( String dateString, String datePattern ) {
        return LocalDate.parse( dateString, ofPattern( datePattern ) );
    }

    /**
     * Format date string.
     *
     * @param date    the date
     * @param pattern the pattern
     * @return the string
     */
    public static String formatDate( Date date, String pattern ) {
        final DateFormat dateFormat = new SimpleDateFormat( pattern );
        return dateFormat.format( date );
    }

    /**
     * Format date string.
     *
     * @param date    the date
     * @param pattern the pattern
     * @return the string
     */
    public static String formatDate( String date, String pattern ) {
        final DateFormat dateFormat = new SimpleDateFormat( pattern );
        return dateFormat.format( parseDate( date, pattern ) );
    }

    /**
     * Formate date string.
     *
     * @param localDateTime the local date time
     * @param pattern       the pattern
     * @return the string
     */
    public static String formatDate( LocalDateTime localDateTime, String pattern ) {
        return localDateTime.format( ofPattern( pattern ) );
    }

    /**
     * Format date string.
     *
     * @param localDate the local date
     * @param pattern   the pattern
     * @return the string
     */
    public static String formatDate( LocalDate localDate, String pattern ) {
        return localDate.format( ofPattern( pattern ) );
    }

    /**
     * Format date to default date time string.
     *
     * @param date     the date
     * @param withTime the with time
     * @return the string
     */
    public static String formatDateToDefaultDateTime( Date date, boolean withTime ) {
        final DateFormat dateFormat = withTime ? new SimpleDateFormat( DATE_TIME_PATTERN ) :
                new SimpleDateFormat( DATE_PATTERN );
        return dateFormat.format( date );
    }

    /**
     * Format string pattern format string.
     *
     * @param date     the date
     * @param pattern  the pattern
     * @param withTime the with time
     * @return the string
     */
    public static String formatStringPatternFormat( String date, String pattern, boolean withTime ) {
        return formatDateToDefaultDateTime( parseDate( date, pattern ), withTime );
    }

    /**
     * Gets first date.
     *
     * @param date the date
     * @return the first date
     */
    public static Date getFirstDate( Date date ) {
        Calendar cal = GregorianCalendar.getInstance( );
        cal.setTime( date );
        Integer minDate = cal.getActualMinimum( Calendar.DAY_OF_MONTH );
        cal.set( Calendar.DATE, minDate );
        return cal.getTime( );
    }

    /**
     * Gets last date.
     *
     * @param date the date
     * @return the last date
     */
    public static Date getLastDate( Date date ) {
        Calendar cal = GregorianCalendar.getInstance( );
        cal.setTime( date );
        Integer maxDate = cal.getActualMaximum( Calendar.DAY_OF_MONTH );
        cal.set( Calendar.DATE, maxDate );
        return cal.getTime( );
    }

    /**
     * Gets month first date.
     *
     * @param localDate the local date
     * @return the month first date
     */
    public static LocalDate getMonthFirstDate( LocalDate localDate ) {
        return localDate.withDayOfMonth( 1 );
    }

    /**
     * Gets month last date.
     *
     * @param localDate the local date
     * @return the month last date
     */
    public static LocalDate getMonthLastDate( LocalDate localDate ) {
        return localDate.withDayOfMonth( localDate.lengthOfMonth( ) );
    }

    /**
     * Gets curr mon first date.
     *
     * @return the curr mon first date
     */
    public static LocalDate getCurrMonFirstDate( ) {
        return getMonthFirstDate( LocalDate.now( ) );
    }

    /**
     * Gets curr mon last date.
     *
     * @return the curr mon last date
     */
    public static LocalDate getCurrMonLastDate( ) {
        return getMonthLastDate( LocalDate.now( ) );
    }

    /**
     * Gets cur mon year mmmmyyyy.
     *
     * @return the cur mon year mmmmyyyy
     */
    public static String getCurMonYearMMMMYYYY( ) {
        return getCurrDatePattern( "MMMM yyyy" );
    }

    /**
     * Gets curr date pattern.
     *
     * @param pattern the pattern
     * @return the curr date pattern
     */
    public static String getCurrDatePattern( String pattern ) {
        return LocalDate.now( ).format( ofPattern( pattern ) );
    }

    /**
     * Gets prev curr local date pattern.
     *
     * @param monthsToSubstract the months to substract
     * @param pattern           the pattern
     * @return the prev curr local date pattern
     */
    public static String getPrevCurrLocalDatePattern( Integer monthsToSubstract, String pattern ) {
        return formatDate( getPrevLocalDate( LocalDate.now( ), monthsToSubstract ), pattern );
    }

    /**
     * Gets prev local date.
     *
     * @param localDate        the local date
     * @param monthsToSubtract the months to subtract
     * @return the prev local date
     */
    public static LocalDate getPrevLocalDate( LocalDate localDate, Integer monthsToSubtract ) {
        return localDate.minusMonths( monthsToSubtract );
    }

}
