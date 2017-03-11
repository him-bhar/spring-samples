package com.himanshu.projects.springsecurity.minified.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserNameValidator {
  /**
   ^               # Start of the line
   [a-z0-9_-]     # Match characters and symbols in the list, a-z, 0-9, underscore, hyphen
   {3,15}         # Length at least 3 characters and maximum length of 15 
   $              # End of the line
  */
  private static Pattern usrNamePtrn = Pattern.compile("^[a-z0-9_-]{3,15}$");
  
  public static boolean validateUserName(String userName) {
    Matcher mtch = usrNamePtrn.matcher(userName);
    if (mtch.matches()) {
      return true;
    }
    return false;
  }
}