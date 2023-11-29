package com.serenitydojo;

public enum enumPrepositions {
   past("past"),
    to("to"),
    almost("almost"),
    just_after("just after"),
    empty("");

   private String string;
    enumPrepositions(String string){
        this.string = string;
   }

   public String getString(){
        if(this.string.isEmpty()){
            return this.string;
        } else {
            return this.string + " " ;
        }
   }
}
