/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmit.se2.pkg2012a.skoorti.model.util;

import java.util.Comparator;

/**
 *
 * @author duynguyen
 */
public class DescendingArrayComparator implements Comparator {

  @Override
  public int compare(Object o1, Object o2) {
    int[] a1 = (int[])o1;
    int[] a2 = (int[])o2;
    int max = Math.max(a1.length, a2.length);
    for(int i=0; i<max; i++) {
      int cmp = Integer.valueOf(a2[i]).compareTo(Integer.valueOf(a1[i]));
      if(cmp!=0)
        return cmp;
    }
    return Integer.valueOf(max-1).compareTo(Integer.valueOf(max-1));
  }
  
}
