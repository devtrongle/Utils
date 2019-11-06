

/*
 *  Date created: 11/06/2019
 *  Last updated: 11/06/2019
 *  Name project: 
 *  Description:
 *  Auth: batrong2709@gmail.com
 */

public class patternRegex {

    public static final String REGEX_EMAIL_ADDRESS ="^(.+)@(.+)$";

    //Use 8 or more characters with a mix of letters (cannot have special characters), at least 1 number and space is not allowed.
    public static final String REGEX_PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{8,}$";

}
