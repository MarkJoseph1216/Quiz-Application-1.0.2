package com.example.quizapplication.ConvertGenerator;


/**  Author: Engr.Arvin Lemuel Cabunoc, CPE
 *  Date: July 15,2019
 *  Time: 12:25am
 * **/

public class ConvertGeneratorDate {

    public static String yearFrom = "";
    public static String monthFrom = "";
    public static String dayFrom = "";

    public static String yearEnd = "";
    public static String monthEnd = "";
    public static String dayEnd = "";

    String globalYearFrom = "";
    String globalMonthFrom ="";
    String globalDayFrom = "";

    String globalYearEnd = "";
    String globalMonthEnd = "";
    String globalDayEnd = "";

    //Convert Generator year from
    public void convertGeneratorYearFrom(String yearFromm)
    {
        switch (yearFromm)
        {
            case "mZlY":
                globalYearFrom = "2016";
                break;
            case "mZpP":
                globalYearFrom = "2017";
                break;
            case "mZkX":
                globalYearFrom = "2018";
                break;
            case "mZjW":
                globalYearFrom = "2019";
                break;
            case "mZiV":
                globalYearFrom = "2020";
                break;
            case "mZhU":
                globalYearFrom = "2021";
                break;
            case "mZgT":
                globalYearFrom = "2022";
                break;
            case "mZfS":
                globalYearFrom = "2023";
                break;
            case "mZeR":
                globalYearFrom = "2024";
                break;
            case "mZdQ":
                globalYearFrom = "2025";
                break;
            case "mZcP":
                globalYearFrom = "2026";
                break;
            case "mZbO":
                globalYearFrom = "2027";
                break;
            case "mZmA":
                globalYearFrom = "2028";
                break;
            case "zMaN":
                globalYearFrom = "2029";
                break;
            case "zMoB":
                globalYearFrom = "2030";
                break;
        }
        yearFrom = globalYearFrom;
    }

    //convert Generator Month From
    public void convertGeneratorMonthFrom(String monthFromm)
    {
        switch (monthFromm) {
            case "aNbO":
                globalMonthFrom = "01";
                break;
            case "bOcP":
                globalMonthFrom = "02";
                break;
            case "cPdQ":
                globalMonthFrom = "03";
                break;
            case "dQeR":
                globalMonthFrom = "04";
                break;
            case "eRfS":
                globalMonthFrom = "05";
                break;
            case "fSgT":
                globalMonthFrom = "06";
                break;
            case "gThU":
                globalMonthFrom = "07";
                break;
            case "hUiV":
                globalMonthFrom = "08";
                break;
            case "iVjW":
                globalMonthFrom = "09";
                break;
            case "jWkX":
                globalMonthFrom = "10";
                break;
            case "kXlY":
                globalMonthFrom = "11";
                break;
            case "lYaN":
                globalMonthFrom = "12";
                break;

        }
        monthFrom = globalMonthFrom;
    }

    //Generator Day From
    public void convertGeneratorDayFrom(String dayFromm)
    {
        switch (dayFromm) {
            case "aN":
                globalDayFrom = "01";
                break;
            case "bO":
                globalDayFrom = "02";
                break;
            case "cP":
                globalDayFrom = "03";
                break;
            case "dQ":
                globalDayFrom = "04";
                break;
            case "eR":
                globalDayFrom = "05";
                break;
            case "fS":
                globalDayFrom = "06";
                break;
            case "gT":
                globalDayFrom = "07";
                break;
            case "hU":
                globalDayFrom = "08";
                break;
            case "iV":
                globalDayFrom = "09";
                break;
            case "jW":
                globalDayFrom = "10";
                break;
            case "kX":
                globalDayFrom = "11";
                break;
            case "lY":
                globalDayFrom = "12";
                break;
            case "mZ":
                globalDayFrom = "13";
                break;
            case "zM":
                globalDayFrom = "14";
                break;
            case "yL":
                globalDayFrom = "15";
                break;
            case "xK":
                globalDayFrom = "16";
                break;
            case "wJ":
                globalDayFrom = "17";
                break;
            case "vI":
                globalDayFrom = "18";
                break;
            case "uH":
                globalDayFrom = "19";
                break;
            case "tG":
                globalDayFrom = "20";
                break;
            case "sF":
                globalDayFrom = "21";
                break;
            case "rE":
                globalDayFrom = "22";
                break;
            case "qD":
                globalDayFrom = "23";
                break;
            case "pC":
                globalDayFrom = "24";
                break;
            case "oB":
                globalDayFrom = "25";
                break;
            case "nA":
                globalDayFrom = "26";
                break;
            case "aC":
                globalDayFrom = "27";
                break;
            case "bD":
                globalDayFrom = "28";
                break;
            case "cE":
                globalDayFrom = "29";
                break;
            case "dF":
                globalDayFrom = "30";
                break;
            case "eG":
                globalDayFrom = "31";
                break;
        }
        dayFrom = globalDayFrom;
    }


    //Generator year end
    public void convertGeneratorYearEnd(String yearEnnd)
    {
        switch (yearEnnd)
        {
            case "mZlY":
                globalYearEnd = "2016";
                break;
            case "mZpP":
                globalYearEnd = "2017";
                break;
            case "mZkX":
                globalYearEnd = "2018";
                break;
            case "mZjW":
                globalYearEnd = "2019";
                break;
            case "mZiV":
                globalYearEnd = "2020";
                break;
            case "mZhU":
                globalYearEnd = "2021";
                break;
            case "mZgT":
                globalYearEnd = "2022";
                break;
            case "mZfS":
                globalYearEnd = "2023";
                break;
            case "mZeR":
                globalYearEnd = "2024";
                break;
            case "mZdQ":
                globalYearEnd = "2025";
                break;
            case "mZcP":
                globalYearEnd = "2026";
                break;
            case "mZbO":
                globalYearEnd = "2027";
                break;
            case "mZmA":
                globalYearEnd = "2028";
                break;
            case "zMaN":
                globalYearEnd = "2029";
                break;
            case "zMoB":
                globalYearEnd = "2030";
                break;
        }
        yearEnd = globalYearEnd;
    }

    //convert Generator Month End
    public void convertGeneratorMonthEnd(String monthEndd)
    {
        switch (monthEndd) {
            case "aNbO":
                globalMonthEnd = "01";
                break;
            case "bOcP":
                globalMonthEnd = "02";
                break;
            case "cPdQ":
                globalMonthEnd = "03";
                break;
            case "dQeR":
                globalMonthEnd = "04";
                break;
            case "eRfS":
                globalMonthEnd = "05";
                break;
            case "fSgT":
                globalMonthEnd = "06";
                break;
            case "gThU":
                globalMonthEnd = "07";
                break;
            case "hUiV":
                globalMonthEnd = "08";
                break;
            case "iVjW":
                globalMonthEnd = "09";
                break;
            case "jWkX":
                globalMonthEnd = "10";
                break;
            case "kXlY":
                globalMonthEnd = "11";
                break;
            case "lYaN":
                globalMonthEnd = "12";
                break;

        }
        monthEnd = globalMonthEnd;
    }

    //Generator Day End
    public void convertGeneratorDayEnd(String dayEndd)
    {
        switch (dayEndd) {
            case "aN":
                globalDayEnd = "01";
                break;
            case "bO":
                globalDayEnd = "02";
                break;
            case "cP":
                globalDayEnd = "03";
                break;
            case "dQ":
                globalDayEnd = "04";
                break;
            case "eR":
                globalDayEnd = "05";
                break;
            case "fS":
                globalDayEnd = "06";
                break;
            case "gT":
                globalDayEnd = "07";
                break;
            case "hU":
                globalDayEnd = "08";
                break;
            case "iV":
                globalDayEnd = "09";
                break;
            case "jW":
                globalDayEnd = "10";
                break;
            case "kX":
                globalDayEnd = "11";
                break;
            case "lY":
                globalDayEnd = "12";
                break;
            case "mZ":
                globalDayEnd = "13";
                break;
            case "zM":
                globalDayEnd = "14";
                break;
            case "yL":
                globalDayEnd = "15";
                break;
            case "xK":
                globalDayEnd = "16";
                break;
            case "wJ":
                globalDayEnd = "17";
                break;
            case "vI":
                globalDayEnd = "18";
                break;
            case "uH":
                globalDayEnd = "19";
                break;
            case "tG":
                globalDayEnd = "20";
                break;
            case "sF":
                globalDayEnd = "21";
                break;
            case "rE":
                globalDayEnd = "22";
                break;
            case "qD":
                globalDayEnd = "23";
                break;
            case "pC":
                globalDayEnd = "24";
                break;
            case "oB":
                globalDayEnd = "25";
                break;
            case "nA":
                globalDayEnd = "26";
                break;
            case "aC":
                globalDayEnd = "27";
                break;
            case "bD":
                globalDayEnd = "28";
                break;
            case "cE":
                globalDayEnd = "29";
                break;
            case "dF":
                globalDayEnd = "30";
                break;
            case "eG":
                globalDayEnd = "31";
                break;
        }
        dayEnd = globalDayEnd;
    }

}
