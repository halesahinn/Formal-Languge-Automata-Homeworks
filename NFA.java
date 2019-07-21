
//Hale ÞAHÝN


import java.util.Set;
import java.util.EnumSet;

public class NFA {
   
   public enum State {a, b, c, d} //enumerated class, added one more state for our NFA

   final static EnumSet<State> F = EnumSet.of(State.d); //changed final state as d
   
   //non-deterministic finite automaton for ((0+1)1(0+1)*0)
   public Set<State> delta(State q, char k) {
     if (q==State.a && k=='0') return EnumSet.of(State.b); //all symbols at a, will lead to b
     if (q==State.a && k=='1') return EnumSet.of(State.b); 
     if (q==State.b && k=='1') return EnumSet.of(State.c); //only '1' will lead to c
     if (q==State.c && k=='0') return EnumSet.of(State.c, State.d); //if the last character '0', it will lead to d
     if (q==State.c && k=='1') return EnumSet.of(State.c); //if not last character, all symbols will lead to c itself
     
    
     return EnumSet.noneOf(State.class);  //default is empty set
   } 
   public boolean accept(String w) {
      Set<State> q = EnumSet.of(State.a);  //initial State
      for (int j=0; j<w.length(); j++) {
         char k = w.charAt(j);
         Set<State> t = EnumSet.noneOf(State.class);  //empty set
         for (State s: q) //all possible states in q
            t.addAll(delta(s, k)); //set union
         System.out.println(j+": ("+q+", "+k+") -> "+t);
         if (t.isEmpty()) return false;
         q = t;
      }
      q.retainAll(F); //set intersection
      return (!q.isEmpty());  //acceptable?
   } 
   public void test(String w) {
      System.out.println(w);
      System.out.println(accept(w) ? "Accept" : "Reject");
      System.out.println();
   }

   public static void main(String[] args) {
      NFA a = new NFA();
      //tests
      a.test("010");
      a.test("0110101111");
      a.test("011010000111");
      a.test("000010");
      a.test("01001010");
      a.test("010010010010");
      a.test("010010101");
   }
}