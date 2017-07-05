package edu.csu.utils;

import java.util.UUID;

public  class GUID {

     public static String getGUID()
     {

         return UUID.randomUUID().toString().replace("-", "");
     }
}
