

/*
 *  Date created: 11/06/2019
 *  Last updated: 11/06/2019
 *  Name project: 
 *  Description:
 *  Auth: batrong2709@gmail.com
 */

public class patternRegex {

    public static final String REGEX_EMAIL_ADDRESS ="^([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";

    //Use 8 or more characters with a mix of letters (cannot have special characters), at least 1 number and space is not allowed.
    public static final String REGEX_PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{8,}$";

    //Does not contain special characters
    public static final String REGEX_COUPON = "^\\w+$";

    public static String REGEX_URLS = "^(http:\\/\\/|https:\\/\\/)?(www.)?([a-zA-Z0-9]+).[a-zA-Z0-9]*.[a-z]{3}.?([a-z]+)?$";

}
